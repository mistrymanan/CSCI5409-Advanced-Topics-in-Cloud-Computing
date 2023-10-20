package com.example.gatekeeperservice;

import com.example.gatekeeperservice.dto.UserInfoRequest;
import org.springframework.http.ResponseEntity;

public interface RequestType {
    ResponseEntity<?> retrieve(UserInfoRequest userInfoRequest);
}
