package com.example.demo.core.model;

import com.example.demo.domain.dto.UserDTO;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends BaseModel<UserDTO> {
    @Column(nullable = false)
    private String name;

    public User() {

    }

    public User(int id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDTO toDTO() {
        UserDTO dto = new UserDTO();
        dto.setName(this.getName());
        dto.setId(this.getId());

        return dto;
    }

    @Override
    public boolean equals(Object another) {
        if (! (another instanceof User)) {
            return false;
        }

        User anotherUser = (User) another;

        return this.getName().equals(anotherUser.getName());
    }
}
