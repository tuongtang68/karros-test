package com.example.demo.domain.dto;

import com.example.demo.core.model.Device;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DeviceDTO extends BaseDTO<Device> {
    @JacksonXmlProperty(isAttribute = true, localName = "href")
    private String link;

    @JacksonXmlProperty(localName = "text")
    private String name;

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Device toModel() {
        Device device = new Device();
        device.setId(getId());
        device.setName(getName());
        device.setLink(getLink());

        return device;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private DeviceDTO holder;

        public Builder() {
            holder = new DeviceDTO();
        }

        public Builder withName(String name) {
            this.holder.setName(name);
            return this;
        }

        public Builder withLink(String link) {
            this.holder.setLink(link);
            return this;
        }

        public Builder withId(int id) {
            this.holder.setId(id);
            return this;
        }

        public DeviceDTO build() {
            return this.holder;
        }
    }
}
