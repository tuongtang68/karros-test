package com.example.demo.core.model;

import com.example.demo.domain.dto.TrackDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Track extends BaseModel<TrackDTO> {
    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false)
    private float longitude;

    @Column
    private String ele;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gps_id", nullable = false)
    private GPS gps;

    @Override
    public TrackDTO toDTO() {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setLatitude(getLatitude());
        trackDTO.setLongitude(getLongitude());
        trackDTO.setEle(getEle());
        trackDTO.setId(getId());
        trackDTO.setTime(getTime());

        return trackDTO;
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

    public String getEle() {
        return this.ele;
    }

    public void setEle(String ele) {
        this.ele = ele;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public GPS getGps() {
        return this.gps;
    }

    public void setGPS(GPS gps) {
        this.gps = gps;
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof Track)) {
            return false;
        }

        Track anotherTrack = (Track) another;

        return this.getEle().equals(anotherTrack.getEle()) && this.getLatitude() == anotherTrack.getLatitude()
            && this.getLongitude() == anotherTrack.getLongitude() && this.getTime().equals(anotherTrack.getTime());
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        return builder.append(this.getEle()).append(getLatitude()).append(getLongitude()).append(getTime()).build();
    }
}
