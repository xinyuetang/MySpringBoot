package com.fudanuniversity.MySpringBoot.Controller;

import com.fudanuniversity.MySpringBoot.Entity.Article;
import com.fudanuniversity.MySpringBoot.Repository.ArticleRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller // This means that this class is a Controller
@RequestMapping(path="/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping(path="/add")
    public @ResponseBody
    String addClass (@RequestBody Article article) {
        JSONObject json=new JSONObject();
        articleRepository.save(article);
        json.put("result","success");
        return json.toString();
    }

    @PostMapping(path="/edit")
    public @ResponseBody
    String editArticle (@RequestBody String content, @RequestParam Integer id) {
        JSONObject json=new JSONObject();
        if(articleRepository.findById(id).isPresent()){
            Article article = articleRepository.findById(id).get();
            article.setContent(content);
            articleRepository.save(article);
            json.put("result","success");
        }
        else json.put("result","fail");
        return json.toString();
    }

    @GetMapping(path="/get")
    public @ResponseBody
    String getArticle (@RequestParam Integer id) {
        if(articleRepository.findById(id).isPresent()){
        JSONObject json=new JSONObject(articleRepository.findById(id).get());
        return json .toString();
        }
        JSONObject json = new JSONObject();
        json.put("result","fail");
        return json.toString();
    }

    @GetMapping(path="/delete")
    public @ResponseBody
    String deleteArticle (@RequestParam Integer id) {
        articleRepository.deleteById(id);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }




}
