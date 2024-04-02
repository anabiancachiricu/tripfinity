package com.unibuc.tripfinity.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum BucketName {
    PROFILE_IMAGE("tripfinity-aws-bucket");
    private final String bucketName;
}