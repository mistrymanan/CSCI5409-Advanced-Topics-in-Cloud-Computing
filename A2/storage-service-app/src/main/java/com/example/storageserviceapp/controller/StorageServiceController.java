package com.example.storageserviceapp.controller;

import com.example.storageserviceapp.dto.DeleteStoreDataFileRequest;
import com.example.storageserviceapp.dto.SearchDataRequest;
import com.example.storageserviceapp.dto.StoreDataRequest;
import com.example.storageserviceapp.service.StoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
public class StorageServiceController {

    @Autowired
    StoreDataService storeDataService;

    @PostMapping("store-data")
    public ResponseEntity<?> storeData(@RequestBody StoreDataRequest request){
        return storeDataService.storeData(request);
    }
    @PostMapping("append-data")
    public ResponseEntity<?> appendData(@RequestBody StoreDataRequest request){
        return storeDataService.appendData(request);
    }
    @PostMapping("search-data")
    public ResponseEntity<?> searchData(@RequestBody SearchDataRequest request){
        return storeDataService.searchData(request);
    }
    @PostMapping("delete-file")
    public ResponseEntity<?> deleteFile(@RequestBody DeleteStoreDataFileRequest request){
        return storeDataService.deleteDataFile(request);
    }
}
