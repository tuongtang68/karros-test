package com.example.demo.domain.dto;

import com.example.demo.domain.dto.deserialize.DateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class MetaDataDTO {
    @JacksonXmlProperty(localName = "desc")
    private String description;

    private String name;

    private String author;

    @JacksonXmlProperty(localName = "link")
    private DeviceDTO device;

    @JacksonXmlProperty(localName = "time")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date uploadedDate;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public DeviceDTO getDevice() {
        return this.device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUploadedDate() {
        return this.uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public UserDTO extractUser() {
        if (this.getAuthor() == null || this.getAuthor().trim().length() == 0) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setName(this.getAuthor());

        return userDTO;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof MetaDataDTO)) {
            return false;
        }

        MetaDataDTO anotherMetadata = (MetaDataDTO) another;
        return StringUtils.equals(this.getName(), anotherMetadata.getName())
            && StringUtils.equals(this.getDescription(), anotherMetadata.getDescription())
            && StringUtils.equals(this.getAuthor(), anotherMetadata.getAuthor())
            && ((this.getDevice() != null
            && anotherMetadata.getDevice() != null
            && StringUtils.equals(this.getDevice().getName(), anotherMetadata.getDevice().getName())) || this.getDevice() == null && anotherMetadata.getDevice() == null);
    }

    public static class Builder {
        private MetaDataDTO holder = new MetaDataDTO();

        public Builder withName(String name) {
            holder.setName(name);
            return this;
        }

        public Builder withDescription(String description) {
            holder.setDescription(description);
            return this;
        }

        public Builder withUploadedDate(Date uploadedDate) {
            holder.setUploadedDate(uploadedDate);
            return this;
        }

        public Builder withAuthor(String author) {
            holder.setAuthor(author);
            return this;
        }

        public Builder withDevice(DeviceDTO device) {
            holder.setDevice(device);
            return this;
        }

        public MetaDataDTO build() {
            return holder;
        }
    }
}
