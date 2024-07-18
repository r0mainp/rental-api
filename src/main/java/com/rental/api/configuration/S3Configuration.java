package com.rental.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Configuration {
    @Value("${aws.access.key}")
    private String awsAccessKey;

    @Value("${aws.secret.key}")
    private String awsSecretKey;

    /**
     * Creates and configures an Amazon S3 client using the AWS SDK.
     * 
     * @return Configured AmazonS3 client instance.
     * @throws IllegalArgumentException If the AWS access or secret key is invalid.
     */
    @Bean
    public AmazonS3 s3client() {

        // Creaates credentials
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

        // Creates client
        var awsS3Config = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.EU_CENTRAL_1) // This field if not exist throws an exception
                .build();

        return awsS3Config;
    }
}
