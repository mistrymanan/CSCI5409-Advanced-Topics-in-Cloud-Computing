package com.example.gatekeeperservice.service;

import com.example.gatekeeperservice.RequestType;
import com.example.gatekeeperservice.RequestTypeFactory;
import com.example.gatekeeperservice.dto.ErrorResponse;
import com.example.gatekeeperservice.dto.UserInfoRequest;
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
            return requestType.retrieve(userInfoRequest);
        }
    }
    private boolean checkFileExist(String name){
        File file = new File(name);
        return file.isFile() && file.exists();
    }
}
