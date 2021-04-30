package com.fudanuniversity.MySpringBoot.Repository;
import com.fudanuniversity.MySpringBoot.Entity.Class;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClassRepository extends CrudRepository<Class, Integer> {
    List<Class> findAllByTag(Integer tag);
}
