package com.example.gatekeeperservice;

import com.example.gatekeeperservice.dto.UserInfoRequest;
import org.springframework.http.ResponseEntity;

public class TemperatureRequest implements RequestType {
    @Override
    public ResponseEntity<?> retrive(UserInfoRequest userInfoRequest) {
        return null;
    }
}
