package com.fudanuniversity.MySpringBoot.Repository;
import com.fudanuniversity.MySpringBoot.Entity.BulletinUser;
import org.springframework.data.repository.CrudRepository;

public interface BulletinUserRepository extends CrudRepository<BulletinUser, Integer> {
    BulletinUser findBulletinUserByBulletinIdAndUserId(Integer bulletinId, Integer userId );
}
