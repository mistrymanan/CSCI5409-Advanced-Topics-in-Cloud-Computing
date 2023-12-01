package com.example.temperatureretrieverservice.service;

import com.example.temperatureretrieverservice.dto.ErrorResponse;
import com.example.temperatureretrieverservice.dto.TemperatureResponse;
import com.example.temperatureretrieverservice.dto.UserInfoRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;

@Service
public class TemperatureRetrieverService {
    public ResponseEntity<?> getTemperatureInfo(UserInfoRequest userInfoRequest){
        try (Reader reader = new FileReader( "./app-data/" + userInfoRequest.getFile())) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord record : csvParser) {
                if(record.getRecordNumber() != 1){
                    String name = record.get(0);
                    Integer temperature = Integer.parseInt(record.get(3));
                    if( name.equals(userInfoRequest.getName())){
                        return ResponseEntity.ok(new TemperatureResponse(userInfoRequest.getFile(), temperature));
                    }
                }
            }
            return ResponseEntity.ok(new ErrorResponse(userInfoRequest.getFile(),"Input file not in CSV format."));
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrorResponse(userInfoRequest.getFile(),"Input file not in CSV format."));
        }
    }
}
