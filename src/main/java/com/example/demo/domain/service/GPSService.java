/*
 * Copyright (c) 2021 Thermo Fisher Scientific
 * All rights reserved.
 */


package com.example.demo.domain.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Collectors;

import com.example.demo.core.configuration.AppConfiguration;
import com.example.demo.domain.dto.GPSDTO;
import com.example.demo.domain.dto.GPSSummaryDTO;
import com.example.demo.domain.dto.Paging;
import com.example.demo.domain.exception.ParseGPSFileException;
import com.example.demo.core.model.Device;
import com.example.demo.core.model.GPS;
import com.example.demo.core.model.Track;
import com.example.demo.core.model.User;
import com.example.demo.core.model.WayPoint;
import com.example.demo.core.repository.DeviceRepository;
import com.example.demo.core.repository.GPSRepository;

import com.example.demo.core.repository.UserRepository;
import com.example.demo.core.repository.projection.GPSSummary;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


/**
 * TODO: Class description
 */
@Service
public class GPSService {
    private final GPSRepository gpsRepository;
    private final UserRepository userRepository;
    private final AppConfiguration appConfiguration;
    private final DeviceRepository deviceRepository;

    public final static String ANONYMOUS_USERNAME = "Anonymous";

    public GPSService(GPSRepository gpsRepository, UserRepository userRepository, DeviceRepository deviceRepository, AppConfiguration appConfiguration) {
        this.gpsRepository = gpsRepository;
        this.userRepository = userRepository;
        this.appConfiguration = appConfiguration;
        this.deviceRepository = deviceRepository;
    }

    /**
     * TODO: Method description
     *
     * @param dto
     * @return
     */
    public GPSDTO save(GPSDTO dto, boolean parseFromFile) throws ParseGPSFileException {
        GPS gps = null;
        if (parseFromFile) {
            File file = Paths.get(appConfiguration.getUploadDir(), dto.getFile()).toFile();
            XmlMapper mapper = new XmlMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                String filePath = dto.getFile();
                dto = mapper.readValue(file, GPSDTO.class);
                dto.setFile(filePath);
            }
            catch (IOException e) {
                throw new ParseGPSFileException(dto.getFile(), e);
            }
        }

        gps = dto.toModel();
        populateRequiredValue(gps);

        return gpsRepository.save(gps).toDTO();
    }

    /**
     * TODO: Method description
     *
     * @param id
     * @return
     */
    public GPSDTO get(int id) {
        GPS gps = gpsRepository.findById(id).orElse(null);
        if (gps == null) {
            return null;
        }

        return gps.toDTO();
    }

    /**
     * TODO: Method description
     *
     * @param page
     * @param size
     * @return
     */
    public Paging<GPSSummaryDTO> findAll(int page, int size) {
        Pageable pagingRequest = PageRequest.of(page, size, Sort.by("uploadedDate").descending());
        Page<GPSSummary> lstGps = gpsRepository.findAllByUserNotNull(pagingRequest);
        Paging<GPSSummaryDTO> paging = new Paging<>();
        paging.setCurrentPage(page);
        paging.setPageSize(size);
        paging.setTotalRecords(lstGps.getTotalElements());
        paging.setReturnedRecords(lstGps.getNumberOfElements());
        paging.setData(lstGps.stream().map(gpsSummary -> {
            GPSSummaryDTO summaryDTO = new GPSSummaryDTO();
            summaryDTO.setName(gpsSummary.getName());
            summaryDTO.setDescription(gpsSummary.getDescription());
            summaryDTO.setThumbnailUrl(null);
            summaryDTO.setId(gpsSummary.getId());

            return summaryDTO;
        }).collect(Collectors.toList()));

        return paging;
    }

    private void populateRequiredValue(GPS gps) {
        if (gps.getUser() == null) {
            gps.setUser(userRepository.getFirstByName(ANONYMOUS_USERNAME));
        }
        else {
            User foundUser = userRepository.getFirstByName(gps.getUser().getName());
            if (foundUser == null) {
                foundUser = userRepository.save(gps.getUser());
            }
            gps.setUser(foundUser);
        }

        if (gps.getDevice() != null) {
            Device foundDevice = deviceRepository.getFirstByName(gps.getDevice().getName());
            if (foundDevice == null) {
                foundDevice = deviceRepository.save(gps.getDevice());
            }
            gps.setDevice(foundDevice);
        }

        if (gps.getUploadedDate() == null) {
            gps.setUploadedDate(new Date());
        }

        for (Track track : gps.getTracks()) {
            track.setGPS(gps);
        }

        for (WayPoint wayPoint: gps.getWayPoints()) {
            wayPoint.setGPS(gps);
        }
    }
}

