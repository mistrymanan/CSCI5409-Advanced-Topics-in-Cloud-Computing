package com.example.temperatureretrieverservice.controller;

import com.example.temperatureretrieverservice.dto.UserInfoRequest;
import com.example.temperatureretrieverservice.service.TemperatureRetrieverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
public class TemperatureRetrieverController {
    @Autowired
    TemperatureRetrieverService temperatureRetrieverService;
    @PostMapping("user-info")
    public ResponseEntity<?> userInfo(@RequestBody UserInfoRequest userInfoRequest){
        return temperatureRetrieverService.getTemperatureInfo(userInfoRequest);
    }
}
