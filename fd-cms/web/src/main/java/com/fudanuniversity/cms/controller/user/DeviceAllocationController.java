package com.fudanuniversity.cms.controller.user;

import com.fudanuniversity.cms.business.service.CmsDeviceAllocationService;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationReturnVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * CmsDeviceAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@RestController
@RequestMapping("/u/device/allocation")
public class DeviceAllocationController extends BaseController {

    @Resource
    private CmsDeviceAllocationService cmsDeviceAllocationService;

    /**
     * 返还设备
     */
    @PostMapping("/return")
    public JsonResult<?> returnDeviceAllocation(@Valid @RequestBody CmsDeviceAllocationReturnVo allocationReturnVo) {
        LoginUser loginUser = getLoginUser();
        cmsDeviceAllocationService.returnDeviceAllocation(loginUser.getUserId(), allocationReturnVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 查询用户当前申请的设备列表
     */
    @GetMapping("/paging")
    public JsonResult<?> queryPagingResult(Paging paging) {
        LoginUser loginUser = getLoginUser();
        PagingResult<CmsDeviceAllocationVo> pagingResult
                = cmsDeviceAllocationService.queryPagingResult(loginUser.getUserId(), paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}