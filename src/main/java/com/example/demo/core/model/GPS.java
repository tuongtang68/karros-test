package com.example.demo.core.model;


import com.example.demo.domain.dto.GPSDTO;
import com.example.demo.domain.dto.MetaDataDTO;
import com.example.demo.domain.dto.TrackSegDTO;
import com.example.demo.domain.dto.TrackWrapperDTO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class GPS extends BaseModel<GPSDTO> {
    @Column(nullable = false, length = 255)
    private String filePath;

    @Column(length = 255)
    private String name;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date uploadedDate;

    @Column
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "gps", cascade = CascadeType.ALL)
    private Set<WayPoint> wayPoints;

    @OneToMany(mappedBy = "gps", cascade = CascadeType.ALL)
    private Set<Track> tracks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private Device device;

    public GPS() {
        this.wayPoints = new HashSet<>();
        this.tracks = new HashSet<>();
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getUploadedDate() {
        return this.uploadedDate;
    }

    public void setUploadedDate(Date createdAt) {
        this.uploadedDate = createdAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WayPoint> getWayPoints() {
        return this.wayPoints;
    }

    public void setWayPoints(Set<WayPoint> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public Set<Track> getTracks() {
        return this.tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    public Device getDevice() {
        return this.device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public GPSDTO toDTO() {
        GPSDTO dto = new GPSDTO();
        dto.setId(this.getId());
        dto.setFile("content/" + this.getFilePath());
        dto.setMetaData(MetaDataDTO.newBuilder()
                                   .withName(this.getName())
                                   .withDescription(this.getDescription())
                                   .withUploadedDate(this.getUploadedDate())
                                   .withDevice(this.getDevice() != null ? this.getDevice().toDTO() : null)
                                   .withAuthor(this.getUser() != null ? this.getUser().getName() : null)
                                   .build());
        dto.setWpt(new ArrayList<>(this.getWayPoints().stream().map(WayPoint::toDTO).collect(Collectors.toSet())));
        TrackWrapperDTO trackWrapperDTO = new TrackWrapperDTO();
        TrackSegDTO trackSegDTO = new TrackSegDTO();
        trackWrapperDTO.getTrkseg().add(trackSegDTO);
        trackSegDTO.setTrkpt(new ArrayList<>(this.getTracks().stream().map(Track::toDTO).collect(Collectors.toSet())));
        dto.setTrackWrapper(trackWrapperDTO);

        return dto;
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof GPS)) {
            return false;
        }

        GPS anotherGPS = (GPS) another;

        return this.getName().equals(anotherGPS.getName())
            && this.getDescription().equals(anotherGPS.getDescription())
            && this.getDevice().equals(anotherGPS.getDevice())
            && this.getTracks().containsAll(anotherGPS.getTracks())
            && this.getTracks().size() == anotherGPS.getTracks().size()
            && this.getUser().equals(anotherGPS.getUser())
            && this.getWayPoints().containsAll(anotherGPS.getWayPoints())
            && this.getWayPoints().size() == anotherGPS.getWayPoints().size();
    }
}
