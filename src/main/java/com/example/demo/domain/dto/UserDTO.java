package com.example.demo.domain.dto;

import com.example.demo.core.model.User;

public class UserDTO extends BaseDTO<User> {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public User toModel() {
        User user = new User();
        user.setId(getId());
        user.setName(getName());

        return user;
    }
}
