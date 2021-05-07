package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsDeviceAllocationService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationApplyVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationReturnVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

/**
 * CmsDeviceAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@RestController
@RequestMapping("/device/allocation")
public class DeviceAllocationController extends BaseController {

    @Resource
    private CmsDeviceAllocationService cmsDeviceAllocationService;

    @Resource
    private CmsUserService cmsUserService;

    /**
     * 申请设备
     */
    @PostMapping("/apply")
    public JsonResult<?> applyDeviceAllocation(@Valid @RequestBody CmsDeviceAllocationApplyVo allocationApplyVo) {
        LoginUser loginUser = getLoginUser();
        cmsDeviceAllocationService.applyDeviceAllocation(loginUser.getUserId(), allocationApplyVo);
        return JsonResult.buildSuccess();
    }

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
        userId = ValueUtils.defaultLong(userId, loginUser.getUserId());
        if (!Objects.equals(userId, loginUser.getUserId())) {//至允许userId对应的本人和管理员查看指定用户分配的培养计划
            cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        }
        PagingResult<CmsDeviceAllocationVo> pagingResult
                = cmsDeviceAllocationService.queryPagingResult(loginUser.getUserId(), paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}