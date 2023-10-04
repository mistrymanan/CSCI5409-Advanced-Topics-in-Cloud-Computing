package com.example.storageserviceapp.service;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AmazonS3ClientCreator {
    static AmazonS3 amazonS3;

    public static AmazonS3 getInstance(){
        if(amazonS3 != null){
            return amazonS3;
        }
        amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(Regions.US_EAST_1)
                .build();

        return amazonS3;
    }

}
