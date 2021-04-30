package com.fudanuniversity.MySpringBoot.Repository;

import com.fudanuniversity.MySpringBoot.Entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    List<Article> findAllByClassID(Integer classID);
}
