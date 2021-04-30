package com.fudanuniversity.MySpringBoot.Repository;
import com.fudanuniversity.MySpringBoot.Entity.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
    List<Device> findAllByType(Integer type);
}
