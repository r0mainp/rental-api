package com.rental.api.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.IOException;


@Service
public class S3Service {
    private final AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public String uploadFile(String keyName, MultipartFile file) {
        try {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            s3client.putObject(bucketName, keyName, file.getInputStream(), metadata);
            return keyName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file input stream", e);
        } catch (AmazonS3Exception e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    public URL getFileUrl(String keyName) {
        return s3client.getUrl(bucketName, keyName);
    }

    public URL uploadAndGetFileUrl(String keyName, MultipartFile file) {
        uploadFile(keyName, file);
        return getFileUrl(keyName);
    }
}
