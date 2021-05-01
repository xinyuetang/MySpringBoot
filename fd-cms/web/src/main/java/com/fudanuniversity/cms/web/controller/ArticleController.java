package com.fudanuniversity.cms.web.controller;

import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.inner.entity.deprecated.Article;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/article")
public class ArticleController {
//    @Autowired
//    private ArticleRepository articleRepository;

    @PostMapping(path = "/add")
    public JsonResult<?> addClass(@RequestBody Article article) {
//        JSONObject json=new JSONObject();
//        articleRepository.save(article);
//        json.put("result","success");
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

    @GetMapping(path = "/get")
    public JsonResult<?> getArticle(@RequestParam Integer id) {
//        if(articleRepository.findById(id).isPresent()){
//        JSONObject json=new JSONObject(articleRepository.findById(id).get());
//        return json .toString();
//        }
//        JSONObject json = new JSONObject();
//        json.put("result","fail");
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
