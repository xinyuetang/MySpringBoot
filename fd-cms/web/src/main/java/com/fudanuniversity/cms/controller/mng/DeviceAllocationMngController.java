package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsDeviceAllocationService;
import com.fudanuniversity.cms.business.service.CmsUserService;
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

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

/**
 * CmsDeviceAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@RestController
@RequestMapping("/mng/device/allocation")
public class DeviceAllocationMngController extends BaseController {

    @Resource
    private CmsDeviceAllocationService cmsDeviceAllocationService;

    @Resource
    private CmsUserService cmsUserService;

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
    public JsonResult<?> queryPagingResult(Long userId, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        PagingResult<CmsDeviceAllocationVo> pagingResult
                = cmsDeviceAllocationService.queryPagingResult(userId, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}