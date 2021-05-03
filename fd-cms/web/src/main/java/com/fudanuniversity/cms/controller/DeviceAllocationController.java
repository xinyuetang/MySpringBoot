package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsDeviceAllocationService;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationApplyVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationReturnVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * CmsDeviceAllocationController
 * <p>
 * Created by tidu at 2021-05-03
 */
@RestController
@RequestMapping("/device/allocation")
public class DeviceAllocationController extends BaseController {

    @Resource
    private CmsDeviceAllocationService cmsDeviceAllocationService;

    /**
     * 申请设备
     */
    @PostMapping("/apply")
    public JsonResult<?> applyDeviceAllocation(@Valid CmsDeviceAllocationApplyVo allocationApplyVo) {
        cmsDeviceAllocationService.applyDeviceAllocation(allocationApplyVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 返还设备
     */
    @PostMapping("/return")
    public JsonResult<?> returnDeviceAllocation(@Valid CmsDeviceAllocationReturnVo allocationReturnVo) {
        cmsDeviceAllocationService.returnDeviceAllocation(allocationReturnVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据条件查询信息列表
     */
    @GetMapping("/paging")
    public JsonResult<?> queryPagingResult(@Valid Paging paging) {
        PagingResult<CmsDeviceAllocationVo> pagingResult = cmsDeviceAllocationService.queryPagingResult(paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}