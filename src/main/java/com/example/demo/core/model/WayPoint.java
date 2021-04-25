package com.example.demo.core.model;

import com.example.demo.domain.dto.WayPointDTO;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "waypoint")
public class WayPoint extends BaseModel<WayPointDTO> {
    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false)
    private float longitude;

    @Column(length = 255)
    private String name;

    @Column(length = 255)
    private String sym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gps_id", nullable = false)
    private GPS gps;

    @Override
    public WayPointDTO toDTO() {
        WayPointDTO dto = new WayPointDTO();
        dto.setId(getId());
        dto.setName(getName());
        dto.setSym(getSym());
        dto.setLatitude(getLatitude());
        dto.setLongitude(getLongitude());
        return dto;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GPS getGps() {
        return this.gps;
    }

    public void setGPS(GPS gps) {
        this.gps = gps;
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof WayPoint)) {
            return false;
        }

        WayPoint anotherWaypoint = (WayPoint) another;

        return this.getLatitude() == anotherWaypoint.getLatitude() && this.getLongitude() == anotherWaypoint.getLongitude()
            && this.getSym().equals(anotherWaypoint.getSym())
            && this.getName().equals(anotherWaypoint.getName());
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        return builder.append(this.getLatitude()).append(this.getLongitude()).append(this.getSym()).append(this.getName()).build();
    }
}

