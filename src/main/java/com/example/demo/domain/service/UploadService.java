package com.example.demo.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    String uploadFile(MultipartFile file) throws IOException;
}
