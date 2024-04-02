package com.unibuc.tripfinity.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.unibuc.tripfinity.config.AmazonConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static com.unibuc.tripfinity.utils.Constants.BUCKET_NAME;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    public S3Service(AmazonConfig s3ClientConfig, AmazonS3 s3Client) {
        this.s3Client = s3ClientConfig.s3();
    }

    public void uploadFile(String keyName, File file) throws IOException {
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, keyName, file);
        s3Client.putObject(request);
    }
}
