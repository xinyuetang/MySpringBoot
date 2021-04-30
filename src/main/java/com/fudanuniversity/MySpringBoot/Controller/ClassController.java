package com.fudanuniversity.MySpringBoot.Controller;
import com.fudanuniversity.MySpringBoot.Entity.Article;
import com.fudanuniversity.MySpringBoot.Entity.Class;
import com.fudanuniversity.MySpringBoot.Repository.ArticleRepository;
import com.fudanuniversity.MySpringBoot.Repository.ClassRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/class")
public class ClassController {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping(path="/add")
    public @ResponseBody
    String addClass (@RequestParam String className, @RequestParam Integer tag) {
        JSONObject json=new JSONObject();
        Class c= new Class();
        c.setClassName(className);
        c.setTag(tag);
        classRepository.save(c);
        json.put("result","success");
        return json.toString();
    }
    @GetMapping(path="/all")
    public @ResponseBody
    String allClass (@RequestParam Integer tag) {
        List<Class> classes = classRepository.findAllByTag(tag);
        JSONArray result = new JSONArray(classes);
        int index = 0;
        for(Class c : classes){
            List<Article> articles =  articleRepository.findAllByClassID(c.getId());
            JSONArray articleTitleList = new JSONArray();
            for(Article a: articles){
                JSONObject tmp = new JSONObject();
                tmp.put("id",a.getId());
                tmp.put("title",a.getTitle());
                articleTitleList.put(tmp);
            }
            JSONObject J = (JSONObject) result.get(index);
            J.put("articles",articleTitleList);

            index++;
        }
        return result.toString();
    }

    @GetMapping(path="/delete")
    public @ResponseBody
    String deleteClass (@RequestParam Integer id) {
        List<Article> articleList =  articleRepository.findAllByClassID(id);
        for (Article a: articleList){
            articleRepository.delete(a);
        }
        classRepository.deleteById(id);
        JSONObject json = new JSONObject();
        json.put("result","success");
        return json.toString();
    }




}
