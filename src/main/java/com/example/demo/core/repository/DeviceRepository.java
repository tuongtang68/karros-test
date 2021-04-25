package com.example.demo.core.repository;

import com.example.demo.core.model.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
    Device getFirstByName(String name);
}
