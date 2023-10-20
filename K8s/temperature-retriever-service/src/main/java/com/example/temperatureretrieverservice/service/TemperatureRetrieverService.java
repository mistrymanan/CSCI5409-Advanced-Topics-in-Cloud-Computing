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
        try (Reader reader = new FileReader( "/manan_PV_dir/" + userInfoRequest.getFile())) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            boolean isParsed = false;
            TemperatureResponse response = new TemperatureResponse();
            for (CSVRecord record : csvParser) {
                if(record.getRecordNumber() != 1){
                    String name = record.get(0);
                    Integer temperature = Integer.parseInt(record.get(3).trim());
                    if( name.equals(userInfoRequest.getName())){
                        response = new TemperatureResponse(userInfoRequest.getFile(), temperature);
                        isParsed = true;
                    }
                }
            }
            if(isParsed){
             return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(new ErrorResponse(userInfoRequest.getFile(),"Input file not in CSV format."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ErrorResponse(userInfoRequest.getFile(),"Input file not in CSV format."));
        }
    }
}
