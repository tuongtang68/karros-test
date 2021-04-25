/*
 * Copyright (c) 2021 Thermo Fisher Scientific
 * All rights reserved.
 */


package com.example.demo.domain.dto;

import com.example.demo.core.model.GPS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: Class description
 */
@JacksonXmlRootElement(localName = "gps")
public class GPSDTO extends BaseDTO<GPS> {
    private String file;

    @JacksonXmlProperty(localName = "metadata")
    private MetaDataDTO metaData;

    @JacksonXmlElementWrapper(useWrapping = false, localName = "wpt")
    private List<WayPointDTO> wpt;

    @JacksonXmlProperty(localName = "trk")
    private TrackWrapperDTO trackWrapper;

    /**
     * TODO: Method description
     *
     * @return
     */
    public String getFile() {
        return this.file;
    }

    /**
     * TODO: Method description
     *
     * @param file
     */
    public void setFile(String file) {
        this.file = file;
    }

    public List<WayPointDTO> getWpt() {
        return this.wpt;
    }

    public void setWpt(List<WayPointDTO> wpt) {
        this.wpt = wpt;
    }

    public TrackWrapperDTO getTrackWrapper() {
        return this.trackWrapper;
    }

    public void setTrackWrapper(TrackWrapperDTO trackWrapper) {
        this.trackWrapper = trackWrapper;
    }

    public MetaDataDTO getMetaData() {
        return this.metaData;
    }

    public void setMetaData(MetaDataDTO metaData) {
        this.metaData = metaData;
    }

    public GPS toModel() {
        GPS gps = new GPS();
        gps.setName(this.getMetaData().getName());
        gps.setDescription(this.getMetaData().getDescription());
        gps.setId(this.getId());
        gps.setFilePath(this.getFile());
        gps.setDevice(this.getMetaData().getDevice().toModel());
        gps.setWayPoints(this.getWpt().stream().map(WayPointDTO::toModel).collect(Collectors.toSet()));
        gps.setTracks(this.getTrackWrapper().getTrkseg()
                          .stream().map(TrackSegDTO::getTrkpt)
                          .flatMap(List::stream)
                          .map(TrackDTO::toModel)
                          .collect(Collectors.toSet()));

        UserDTO userDTO = this.getMetaData().extractUser();
        gps.setUser(userDTO == null ? null : userDTO.toModel());

        return gps;
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof GPSDTO)) {
            return false;
        }

        GPSDTO anotherGPS = (GPSDTO) another;

        return this.getMetaData().equals(anotherGPS.getMetaData())
            && this.getTrackWrapper().equals(anotherGPS.getTrackWrapper())
            && this.getWpt().equals(anotherGPS.getWpt());
    }
}

