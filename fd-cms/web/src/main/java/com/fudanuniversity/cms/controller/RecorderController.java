package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsRecorderService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.recorder.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.commons.util.UrlUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.fudanuniversity.cms.commons.enums.UserRoleEnum.Seminar;

@Controller
@RequestMapping(path = "/recorder")
public class RecorderController extends BaseController {

    @Resource
    private CmsRecorderService cmsRecorderService;

    @Resource
    private CmsUserService cmsUserService;

    @PostMapping(path = "/add")
    @ResponseBody
    public JsonResult<?> addRecorder(@Valid @RequestBody CmsRecorderAddVo addVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Seminar);
        cmsRecorderService.saveCmsRecorder(addVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    @ResponseBody
    public JsonResult<?> updateRecorder(@Valid @RequestBody CmsRecorderUpdateVo updateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Seminar);
        cmsRecorderService.updateCmsRecorderById(updateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    @ResponseBody
    public JsonResult<?> deleteRecorder(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId(), Seminar);
        cmsRecorderService.deleteCmsRecorderById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 上传文稿
     */
    @PostMapping(path = "/upload")
    @ResponseBody
    public JsonResult<?> uploadRecorderFile(@Valid CmsRecorderUploadVo uploadVo) {
        LoginUser loginUser = getLoginUser();
        Long userId = loginUser.getUserId();
        cmsRecorderService.uploadRecorderFile(userId, uploadVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/paging")
    @ResponseBody
    public JsonResult<?> queryPagingResult(@Valid CmsRecorderQueryVo queryVo, Paging paging) {
        PagingResult<CmsRecorderVo> pagingResult = cmsRecorderService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    /**
     * 下载演讲文件
     */
    @GetMapping(path = "/download")
    public ResponseEntity<?> downloadRecorderFile(@Valid CmsRecorderDownloadVo downloadVo) {
        CmsRecorderDownloadResultVo downloadResultVo = cmsRecorderService.downloadRecorderFile(downloadVo);
        //给前端文件名转码防止乱码问题
        String encodedFileName = UrlUtils.encode(downloadResultVo.getFileName());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, downloadResultVo.getFileType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .body(downloadResultVo.getFileContent());
    }
}
