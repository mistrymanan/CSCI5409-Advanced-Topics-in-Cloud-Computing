package com.example.gatekeeperservice;

import com.example.gatekeeperservice.dto.ErrorResponse;
import com.example.gatekeeperservice.dto.SuccessResponse;
import com.example.gatekeeperservice.dto.UserInfoRequest;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StoreRequest implements RequestType {
    @Override
    public ResponseEntity<?> retrieve(UserInfoRequest userInfoRequest) {

        try {
            File file = new File("/manan_PV_dir/" + userInfoRequest.getFile());
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(userInfoRequest.getData());
            fileWriter.close();
        } catch (IOException e) {
            return ResponseEntity.ok().body(new ErrorResponse(userInfoRequest.getFile(),"Error while storing the file to the storage."));
        }
    return ResponseEntity.ok().body(new SuccessResponse(userInfoRequest.getFile(), "Success."));
    }
}
