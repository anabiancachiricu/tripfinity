package com.unibuc.tripfinity.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String key = file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        Path tempFile = Files.createTempFile(key, null);
        file.transferTo(tempFile);

        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromFile(tempFile));

        Files.delete(tempFile);
        System.out.println("RESPONSE: "+ putObjectResponse);

        return putObjectResponse.eTag();
    }

    public File getObjectByETag(String bucketName, String targetETag) throws IOException {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listObjectsV2Response;

        do {
            listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);

            for (S3Object s3Object : listObjectsV2Response.contents()) {
                if (s3Object.eTag().replace("\"", "").equals(targetETag.replace("\"", ""))) {
                    String key = s3Object.key();

                    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build();

                    File file = new File("/path/to/save/" + key); // Change path as needed
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        s3Client.getObject(getObjectRequest, Paths.get(file.getPath()));
                    }
                    return file;
                }
            }

            listObjectsV2Request = listObjectsV2Request.toBuilder()
                    .continuationToken(listObjectsV2Response.nextContinuationToken())
                    .build();

        } while (listObjectsV2Response.isTruncated());

        return null;
    }
}
