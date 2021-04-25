package com.example.demo.domain.dto;

import com.example.demo.domain.dto.deserialize.DateDeserializer;
import com.example.demo.core.model.Track;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

public class TrackDTO extends BaseDTO<Track> {
    private String ele;

    @JacksonXmlProperty(isAttribute = true, localName = "lat")
    private float latitude;

    @JacksonXmlProperty(isAttribute = true, localName = "lon")
    private float longitude;

    @JacksonXmlProperty(localName = "time")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date time;

    public String getEle() {
        return this.ele;
    }

    public void setEle(String ele) {
        this.ele = ele;
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

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public Track toModel() {
        Track model = new Track();
        model.setId(getId());
        model.setEle(getEle());
        model.setLatitude(getLatitude());
        model.setLongitude(getLongitude());
        model.setTime(getTime());

        return model;
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof TrackDTO)) {
            return false;
        }

        TrackDTO anotherTrack = (TrackDTO) another;
        return this.getEle().equals(anotherTrack.getEle()) && this.getLatitude() == anotherTrack.getLatitude()
            && this.getLongitude() == anotherTrack.getLongitude() && this.getTime() == anotherTrack.getTime();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        return builder.append(this.getEle())
                      .append(this.getLatitude())
                      .append(this.getLongitude())
                      .append(this.getTime())
                      .build();
    }
}
