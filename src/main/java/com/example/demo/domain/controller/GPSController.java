/*
 * Copyright (c) 2021 Thermo Fisher Scientific
 * All rights reserved.
 */


package com.example.demo.domain.controller;

import java.io.IOException;

import java.time.ZonedDateTime;

import com.example.demo.domain.dto.GPSDTO;
import com.example.demo.domain.dto.GPSSummaryDTO;
import com.example.demo.domain.dto.Paging;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.exception.ParseGPSFileException;
import com.example.demo.domain.service.GPSService;

import com.example.demo.domain.service.UploadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * TODO: Class description
 */
@RestController
@RequestMapping(path = "/gps")
public class GPSController {
    private final GPSService gpsService;
    private final UploadService uploadService;

    public GPSController(GPSService gpsService, UploadService uploadService) {
        this.gpsService = gpsService;
        this.uploadService = uploadService;
    }

    /**
     * TODO: Method description
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    @ApiOperation(value = "List out latest tracking paginated")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Paging<GPSSummaryDTO>> latestTrack(@ApiParam(value = "Page offset") @RequestParam(defaultValue = "0") int page,
                                                             @ApiParam(value = "Number of records returned") @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(gpsService.findAll(page, size));
    }

    /**
     * TODO: Method description
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Get detail of a tracking by ID")
    @GetMapping(path = "/{id}")
    public GPSDTO detail(@PathVariable int id) throws NotFoundException {
        GPSDTO dto = gpsService.get(id);
        if (dto == null) {
            throw new NotFoundException();
        }
        return dto;
    }

    /**
     * TODO: Method description
     *
     * @param file
     * @return
     */
    @PostMapping
    @ApiOperation(value = "Upload tracking")
    public GPSDTO create(@ApiParam(value = "Only supports file extension gpx") @RequestBody MultipartFile file) throws IOException, ParseGPSFileException {
        String filePath = uploadService.uploadFile(file);
        GPSDTO dto = new GPSDTO();
        dto.setFile(filePath);

        return gpsService.save(dto, true);
    }
}

