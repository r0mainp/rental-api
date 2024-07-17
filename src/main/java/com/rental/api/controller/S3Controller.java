package com.rental.api.controller;

import java.net.URL;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rental.api.service.S3Service;

@RestController
@RequestMapping("/api")
public class S3Controller {
    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<URL> uploadFile(@RequestParam("file") MultipartFile file) {
        String keyName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();; // Create a unique name
        URL fileUrl = s3Service.uploadAndGetFileUrl(keyName, file);
        return ResponseEntity.ok(fileUrl);
    }
}
