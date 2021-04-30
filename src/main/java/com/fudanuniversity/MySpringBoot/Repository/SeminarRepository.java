package com.fudanuniversity.MySpringBoot.Repository;

import com.fudanuniversity.MySpringBoot.Entity.Seminar;
import org.springframework.data.repository.CrudRepository;


public interface SeminarRepository extends CrudRepository<Seminar, Integer> {
}