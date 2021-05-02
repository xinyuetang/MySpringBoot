package com.fudanuniversity.cms.web.controller;

import com.fudanuniversity.cms.business.service.CmsArticleCategoryService;
import com.fudanuniversity.cms.business.service.CmsArticleService;
import com.fudanuniversity.cms.business.vo.article.CmsArticleCategoryAddVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleCategoryQueryVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/article")
public class ArticleController {

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
    public JsonResult<?> deleteArticleCategoryById(@RequestBody Long id) {
        cmsArticleCategoryService.deleteArticleCategoryById(id);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/category/list")
    public JsonResult<?> listArticleCategories(@Valid @RequestBody CmsArticleCategoryQueryVo queryVo) {
        cmsArticleCategoryService.listArticleCategories(queryVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/{id}")
    public JsonResult<?> getArticle(@PathVariable Integer id) {
        //cmsArticleService.queryPagingResultByParam()
//        if(articleRepository.findById(id).isPresent()){
//        JSONObject json=new JSONObject(articleRepository.findById(id).get());
//        return json .toString();
//        }
//        JSONObject json = new JSONObject();
//        json.put("result","fail");
//        return json.toString();
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/add")
    public JsonResult<?> addArticle(@RequestBody String content, @RequestParam Integer id) {
//        JSONObject json=new JSONObject();
//        if(articleRepository.findById(id).isPresent()){
//            Article article = articleRepository.findById(id).get();
//            article.setContent(content);
//            articleRepository.save(article);
//            json.put("result","success");
//        }
//        else json.put("result","fail");
//        return json.toString();
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/edit")
    public JsonResult<?> editArticle(@RequestBody String content, @RequestParam Integer id) {
//        JSONObject json=new JSONObject();
//        if(articleRepository.findById(id).isPresent()){
//            Article article = articleRepository.findById(id).get();
//            article.setContent(content);
//            articleRepository.save(article);
//            json.put("result","success");
//        }
//        else json.put("result","fail");
//        return json.toString();
        return JsonResult.buildSuccess();
    }


    @GetMapping(path = "/delete")
    public JsonResult<?> deleteArticle(@RequestParam Integer id) {
//        articleRepository.deleteById(id);
//        JSONObject json = new JSONObject();
//        json.put("result","success");
//        return json.toString();
        return JsonResult.buildSuccess();
    }

}
