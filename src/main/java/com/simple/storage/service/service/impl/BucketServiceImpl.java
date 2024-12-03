package com.simple.storage.service.service.impl;

import com.simple.storage.service.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@Service
public class BucketServiceImpl implements BucketService {

    @Autowired
    private S3Client s3Client;

    @Override
    public String createOneBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();
        s3Client.createBucket(createBucketRequest);
        return "Bucket Created Successfully";
    }
}
