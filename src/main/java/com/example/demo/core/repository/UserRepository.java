package com.example.demo.core.repository;

import com.example.demo.core.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User getFirstByName(String name);
}
