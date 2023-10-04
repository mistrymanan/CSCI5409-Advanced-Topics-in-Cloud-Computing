package com.example.storageserviceapp;

import com.example.storageserviceapp.dto.StoreDataRequest;
import com.example.storageserviceapp.service.StoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StorageServiceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageServiceAppApplication.class, args);
    }

}
