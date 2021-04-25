package com.example.demo.core.model;

import com.example.demo.domain.dto.DeviceDTO;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Device extends BaseModel<DeviceDTO> {
    @Column(nullable = false, unique = true)
    public String name;

    @Column(nullable = false, unique = true)
    public String link;

    public Device(int id, String name, String link) {
        this.setId(id);
        this.setName(name);
        this.setLink(link);
    }

    public Device() {

    }

    @Override
    public DeviceDTO toDTO() {
        DeviceDTO dto = new DeviceDTO();
        dto.setId(getId());
        dto.setLink(getLink());
        dto.setName(getName());
        return dto;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof Device)) {
            return false;
        }

        Device anotherDevice = (Device) another;
        return this.getName().equals(anotherDevice.getName()) && this.getLink().equals(anotherDevice.getLink());
    }
}
