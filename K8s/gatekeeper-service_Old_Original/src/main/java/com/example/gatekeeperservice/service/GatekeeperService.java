package com.example.gatekeeperservice.service;

import com.example.gatekeeperservice.RequestType;
import com.example.gatekeeperservice.RequestTypeFactory;
import com.example.gatekeeperservice.TemperatureRequest;
import com.example.gatekeeperservice.dto.ErrorResponse;
import com.example.gatekeeperservice.dto.UserInfoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class GatekeeperService {

//    Logger logger = LoggerFactory.getLogger(GatekeeperService.class);
    public ResponseEntity<?> getTemperature(UserInfoRequest userInfoRequest){
        if(userInfoRequest.getFile() == null){
            return ResponseEntity.ok(new ErrorResponse(null,"Invalid JSON input."));
        } else if(!checkFileExist(userInfoRequest.getFile())){
            return ResponseEntity.ok(new ErrorResponse(userInfoRequest.getFile(),"File not found."));
        }
        else {
            RequestType requestType = RequestTypeFactory.getInstance("temperature");
            return requestType.retrieve(userInfoRequest);
        }
    }
    public ResponseEntity<?> storeFile(UserInfoRequest userInfoRequest){
        if(userInfoRequest.getFile() == null){
            return ResponseEntity.ok(new ErrorResponse(null,"Invalid JSON input."));
        }else {
            RequestType requestType = RequestTypeFactory.getInstance("store");
            return requestType.retrieve(userInfoRequest);
        }
    }
    private boolean checkFileExist(String name){
        File file = new File("/manan_PV_dir/" +name);
        return file.isFile() && file.exists();
    }
}
