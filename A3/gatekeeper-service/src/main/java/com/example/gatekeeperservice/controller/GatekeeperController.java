package com.example.gatekeeperservice.controller;


import com.example.gatekeeperservice.dto.UserInfoRequest;
import com.example.gatekeeperservice.service.GatekeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
public class GatekeeperController {

    @Autowired
    GatekeeperService gatekeeperService;
    @PostMapping("user-info")
    public ResponseEntity<?> userInfo(@RequestBody UserInfoRequest userInfoRequest){
        return gatekeeperService.userInfo(userInfoRequest);
    }
}
