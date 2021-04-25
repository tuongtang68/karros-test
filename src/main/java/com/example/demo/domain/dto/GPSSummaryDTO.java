package com.example.demo.domain.dto;

import com.example.demo.core.model.GPS;
import org.apache.commons.lang3.StringUtils;

public class GPSSummaryDTO extends BaseDTO<GPS> {
    private String name;
    private String description;
    private String thumbnailUrl;

    public GPSSummaryDTO() {

    }

    public GPSSummaryDTO(int id, String name, String description, String thumbnailUrl) {
        this.setName(name);
        this.setId(id);
        this.setDescription(description);
        this.setThumbnailUrl(thumbnailUrl);
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String url) {
        this.thumbnailUrl = url;
    }

    @Override
    public GPS toModel() {
        return null;
    }

    @Override
    public boolean equals(Object another) {
        if (! (another instanceof GPSSummaryDTO)) {
            return false;
        }

        GPSSummaryDTO dto = (GPSSummaryDTO) another;

        return StringUtils.equals(this.getDescription(), dto.getDescription())
            && StringUtils.equals(this.getName(), dto.getName())
            && StringUtils.equals(this.getThumbnailUrl(), dto.getThumbnailUrl());
    }
}
