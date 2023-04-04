package com.nelf.api_images.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.iterable.S3Objects;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StorageService {
    private final String BUCKET_NAME = "bucket12345";

    public String getUrl(String fileName) {
        AwsClientBuilder.EndpointConfiguration ep = new AwsClientBuilder
                .EndpointConfiguration("storage.yandexcloud.net", "ru-central1");
        BasicAWSCredentials bAWSc = new BasicAWSCredentials("YCAJEzKNNIR29-k_z1xiJLMp9", "YCPxfKopP7vJCLUumKdPZbkSAlgaHzgeis_GSzaO");
        final AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(bAWSc))
                .withEndpointConfiguration(ep).build();
        Date expiration = new Date();
        StringBuilder url = new StringBuilder();
        S3Objects.inBucket(s3client, BUCKET_NAME).forEach(e -> {
            if (e.getKey().equals(fileName)) {
                expiration.setTime(expiration.getTime() + (1000 * 60 * 60 * 24 * 5));
                url.append(s3client.generatePresignedUrl(BUCKET_NAME, fileName, expiration, HttpMethod.GET));
            }

        });
        if (url.length() == 0)
            return "error";
        else
            return url.toString();
    }
}
