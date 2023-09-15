package com.example.gatekeeperservice.service;

import com.example.gatekeeperservice.RequestType;
import com.example.gatekeeperservice.RequestTypeFactory;
import com.example.gatekeeperservice.dto.ErrorResponse;
import com.example.gatekeeperservice.dto.LocationResponse;
import com.example.gatekeeperservice.dto.UserInfoRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class GatekeeperService {

//    Logger logger = LoggerFactory.getLogger(GatekeeperService.class);
    public ResponseEntity<?> userInfo(UserInfoRequest userInfoRequest){
        if(userInfoRequest.getFile() == null){
            return ResponseEntity.ok(new ErrorResponse(null,"Invalid JSON input."));
        } else if(!checkFileExist(userInfoRequest.getFile())){
            return ResponseEntity.ok(new ErrorResponse(userInfoRequest.getFile(),"File not found."));
        }
//        else if(userInfoRequest.getKey()){
//          case when key doestn't exist
//        }
        else {
            RequestType requestType = RequestTypeFactory.getInstance(userInfoRequest.getKey());
            return requestType.retrive(userInfoRequest);
        }
    }
    private boolean checkFileExist(String name){
        File file = new File(name);
        return file.isFile() && file.exists();
    }
}
