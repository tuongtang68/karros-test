package com.example.demo.domain.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
import java.util.List;

public class TrackWrapperDTO {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TrackSegDTO> trkseg;

    public TrackWrapperDTO() {
        this.trkseg = new ArrayList<>();
    }

    public List<TrackSegDTO> getTrkseg() {
        return this.trkseg;
    }
    public void setTrkseg(List<TrackSegDTO> trkseg) {
        this.trkseg = trkseg;
    }

    @Override
    public boolean equals(Object another) {
        if (! (another instanceof TrackWrapperDTO)) {
            return false;
        }

        return this.getTrkseg().equals(((TrackWrapperDTO) another).getTrkseg());
    }
}