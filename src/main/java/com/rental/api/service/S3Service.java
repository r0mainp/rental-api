package com.rental.api.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.IOException;

/**
 * Service class for interacting with Amazon S3.
 */
@Service
public class S3Service {
    private final AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    /**
     * Constructs an S3Service instance with the provided Amazon S3 client.
     *
     * @param s3client The Amazon S3 client used to communicate with S3.
     */
    public S3Service(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    /**
     * Uploads a file to the configured S3 bucket.
     *
     * @param keyName The key name (object key) under which to store the file in S3.
     * @param file The MultipartFile representing the file to upload.
     * @return The key name under which the file was uploaded.
     * @throws RuntimeException if there's an error reading the file input stream or uploading to S3.
     */
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

    /**
     * Retrieves the URL of a file stored in the configured S3 bucket.
     *
     * @param keyName The key name (object key) of the file in S3.
     * @return The URL of the file in S3.
     */
    public URL getFileUrl(String keyName) {
        return s3client.getUrl(bucketName, keyName);
    }

    /**
     * Uploads a file to the configured S3 bucket and retrieves its URL.
     *
     * @param keyName The key name (object key) under which to store the file in S3.
     * @param file The MultipartFile representing the file to upload.
     * @return The URL of the uploaded file in S3.
     */
    public URL uploadAndGetFileUrl(String keyName, MultipartFile file) {
        uploadFile(keyName, file);
        return getFileUrl(keyName);
    }
}
