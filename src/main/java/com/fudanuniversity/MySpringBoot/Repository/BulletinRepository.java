package com.fudanuniversity.MySpringBoot.Repository;
import com.fudanuniversity.MySpringBoot.Entity.Bulletin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BulletinRepository extends CrudRepository<Bulletin, Integer> {
    List<Bulletin> findByOrderByDateDesc();
}
