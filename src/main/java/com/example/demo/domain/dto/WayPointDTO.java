package com.example.demo.domain.dto;

import com.example.demo.core.model.WayPoint;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class WayPointDTO extends BaseDTO<WayPoint> {
    private String name;

    @JacksonXmlProperty(isAttribute = true, localName = "lat")
    private float latitude;

    @JacksonXmlProperty(isAttribute = true, localName = "lon")
    private float longitude;

    private String sym;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getSym() {
        return this.sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    @Override
    public WayPoint toModel() {
        WayPoint model = new WayPoint();
        model.setId(getId());
        model.setLatitude(getLatitude());
        model.setLongitude(getLongitude());
        model.setName(getName());
        model.setSym(getSym());

        return model;
    }
}
