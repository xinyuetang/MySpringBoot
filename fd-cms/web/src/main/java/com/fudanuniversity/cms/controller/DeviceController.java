package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsDeviceService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.device.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Administrator;

@RestController
@RequestMapping(path = "/device")
public class DeviceController extends BaseController {

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

    @GetMapping(path = "delete")
    public JsonResult<?> deleteDevice(@NotNull Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        return JsonResult.buildSuccess();
    }

    /**
     * 查看设备分页列表
     */
    @GetMapping(path = "paging")
    public JsonResult<?> queryPagingResult(CmsDeviceQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        PagingResult<CmsDeviceVo> pagingResult = cmsDeviceService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    /**
     * 查看某一设备使用情况分页列表
     */
    @GetMapping(path = "/usage/paging")
    public JsonResult<?> queryUsagePagingResult(Long deviceId, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.confirmUserPrivilege(loginUser.getStuId(), Administrator);
        PagingResult<CmsDeviceUsageVo> pagingResult = cmsDeviceService.queryUsagePagingResult(deviceId, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}
