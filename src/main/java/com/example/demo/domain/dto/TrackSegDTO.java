package com.example.demo.domain.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class TrackSegDTO {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TrackDTO> trkpt;

    public List<TrackDTO> getTrkpt() {
        return this.trkpt;
    }

    public void setTrkpt(List<TrackDTO> trkpt) {
        this.trkpt = trkpt;
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof TrackSegDTO)) {
            return false;
        }

        return this.trkpt.containsAll(((TrackSegDTO) another).getTrkpt())
            && this.trkpt.size() == ((TrackSegDTO) another).getTrkpt().size();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        return builder.append(getTrkpt()).build();
    }
}
