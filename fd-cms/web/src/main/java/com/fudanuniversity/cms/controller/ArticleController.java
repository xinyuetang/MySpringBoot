package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsArticleCategoryService;
import com.fudanuniversity.cms.business.service.CmsArticleService;
import com.fudanuniversity.cms.business.vo.article.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/article")
public class ArticleController extends BaseController {

    @Resource
    private CmsArticleService cmsArticleService;

    @Resource
    private CmsArticleCategoryService cmsArticleCategoryService;

    @PostMapping(path = "/category/add")
    public JsonResult<?> addArticleCategory(@Valid @RequestBody CmsArticleCategoryAddVo categoryAddVo) {
        cmsArticleCategoryService.addArticleCategory(categoryAddVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/category/delete")
    public JsonResult<?> deleteArticleCategoryById(@RequestParam Long id) {
        cmsArticleCategoryService.deleteArticleCategoryById(id);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/category/list")
    public JsonResult<?> listArticleCategories(@Valid @RequestBody CmsArticleCategoryQueryVo queryVo) {
        cmsArticleCategoryService.listArticleCategories(queryVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/{id}")
    public JsonResult<?> getArticle(@PathVariable @NotNull @Min(1L) Long id) {
        CmsArticleVo articleVo = cmsArticleService.getArticle(id);
        return JsonResult.buildSuccess(articleVo);
    }

    @GetMapping(path = "/paging")
    public JsonResult<?> getArticle(CmsArticleQueryVo queryVo, Paging paging) {
        PagingResult<CmsArticleVo> pagingResult = cmsArticleService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @PostMapping(path = "/add")
    public JsonResult<?> addArticle(@RequestBody @Valid CmsArticleAddVo articleAddVo) {
        cmsArticleService.saveCmsArticle(articleAddVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/edit")
    public JsonResult<?> editArticle(@RequestBody @Valid CmsArticleEditVo editVo) {
        cmsArticleService.editCmsArticleBy(editVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> deleteArticle(@RequestParam @NotNull @Min(1L) Long id) {
        cmsArticleService.deleteCmsArticleById(id);
        return JsonResult.buildSuccess();
    }

}
