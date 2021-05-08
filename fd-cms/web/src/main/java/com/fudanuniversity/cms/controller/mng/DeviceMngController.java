package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsDeviceService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.device.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

@RestController
@RequestMapping(path = "/mng/device")
public class DeviceMngController extends BaseController {

    @Resource
    private CmsDeviceService cmsDeviceService;

    @Resource
    private CmsUserService cmsUserService;

    @PostMapping(path = "/add")
    public JsonResult<?> addNewDevice(@RequestBody CmsDeviceAddVo addVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsDeviceService.saveCmsDevice(addVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateDevice(@RequestBody CmsDeviceUpdateVo updateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsDeviceService.updateCmsDeviceById(updateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> deleteDevice(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        cmsDeviceService.deleteCmsDeviceById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 查看设备分页列表
     */
    @GetMapping(path = "/paging")
    public JsonResult<?> queryPagingResult(@Valid CmsDeviceQueryVo queryVo, Paging paging) {
        PagingResult<CmsDeviceVo> pagingResult = cmsDeviceService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    /**
     * 查看某一设备使用情况分页列表
     */
    @GetMapping(path = "/usage/paging")
    public JsonResult<?> queryUsagePagingResult(Long deviceId, Paging paging) {
        PagingResult<CmsDeviceUsageVo> pagingResult = cmsDeviceService.queryUsagePagingResult(deviceId, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}
