/*
 * Copyright (c) 2021 Thermo Fisher Scientific
 * All rights reserved.
 */


package com.example.demo.domain.service;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Random;

import com.example.demo.core.configuration.AppConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * TODO: Class description
 */
@Service
public class FileUploadService implements UploadService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);
    private final AppConfiguration configuration;

    @Autowired
    public FileUploadService(AppConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * TODO: Method description
     *
     * @param file
     *
     * @return
     *
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException
    {
        LOGGER.info("Uploading file " + file.getOriginalFilename());
        String originalNames = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = originalNames.substring(originalNames.lastIndexOf('.') + 1, originalNames.length());
        String fileName = String.format("%d_%s.%s", System.currentTimeMillis(), randomString(3), extension);
        String subPath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        Path directoryPath = Paths.get(configuration.getUploadDir(), subPath);
        Path filePath = Paths.get(directoryPath.toString(), fileName);
        if (!Files.exists(directoryPath))
        {
            Files.createDirectories(directoryPath);
        }
        Files.copy(file.getInputStream(), filePath);
        return subPath + File.separatorChar + fileName;
    }

    private String randomString(int length)
    {
        Random r = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++)
        {
            stringBuilder.append((char)(r.nextInt(26) + 'a'));
        }

        return stringBuilder.toString();
    }
}

