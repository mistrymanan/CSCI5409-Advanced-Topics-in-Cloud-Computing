package com.example.gatekeeperservice;

import com.example.gatekeeperservice.dto.ErrorResponse;
import com.example.gatekeeperservice.dto.LocationResponse;
import com.example.gatekeeperservice.dto.UserInfoRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;

import java.io.FileReader;
import java.io.Reader;

public class LocationRequest implements RequestType {
    @Override
    public ResponseEntity<?> retrieve(UserInfoRequest userInfoRequest) {
        try (Reader reader = new FileReader("./" + userInfoRequest.getFile())) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord record : csvParser) {
                if(record.getRecordNumber() != 1){
                    String name = record.get(0);
                    Float latitude = Float.parseFloat(record.get(1));
                    Float longitude = Float.parseFloat(record.get(2));
                    if( name.equals(userInfoRequest.getName())){
                        return ResponseEntity.ok(new LocationResponse(userInfoRequest.getFile(), latitude, longitude));
                    }
                }
            }
            return ResponseEntity.ok(new ErrorResponse(userInfoRequest.getFile(),"Input file not in CSV format."));
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrorResponse(userInfoRequest.getFile(),"Input file not in CSV format."));
        }
    }
}
