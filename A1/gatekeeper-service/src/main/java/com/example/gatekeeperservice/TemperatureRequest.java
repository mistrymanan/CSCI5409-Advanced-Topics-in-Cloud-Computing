package com.example.gatekeeperservice;

import com.example.gatekeeperservice.dto.UserInfoRequest;
import org.springframework.http.ResponseEntity;

public class TemperatureRequest implements RequestType {
    @Override
    public ResponseEntity<?> retrieve(UserInfoRequest userInfoRequest) {
        return null;
    }
}
