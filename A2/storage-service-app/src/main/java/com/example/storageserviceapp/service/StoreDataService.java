package com.example.storageserviceapp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.example.storageserviceapp.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.regex.Pattern;

@Service
public class StoreDataService {
    final String BUCKET_NAME = "b00948831-1";
    final String CONTENT_TYPE = "text/plain";
    final String FILE_NAME = "data_file.txt";
    final String S3_DATA_URL = "https://"+BUCKET_NAME+".s3.us-east-1.amazonaws.com/"+FILE_NAME;
    Logger logger = LoggerFactory.getLogger(StoreDataService.class);

    public StoreDataService() {
    }

    public ResponseEntity<?> storeData(StoreDataRequest storeDataRequest){
        AmazonS3 amazonS3 =  AmazonS3ClientCreator.getInstance();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(CONTENT_TYPE);

        try(InputStream data = new ByteArrayInputStream(storeDataRequest.getData().getBytes())){
            PutObjectRequest objectRequest = new PutObjectRequest(BUCKET_NAME,FILE_NAME,data,metadata);
            objectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(objectRequest);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

//        URL url = amazonS3.generatePresignedUrl(new GeneratePresignedUrlRequest(BUCKET_NAME,FILE_NAME));
        return ResponseEntity.ok(new StoreDataResponse(S3_DATA_URL));
    }

    public ResponseEntity<?> appendData(StoreDataRequest data){
        AmazonS3 amazonS3 = AmazonS3ClientCreator.getInstance();
        GetObjectRequest request = new GetObjectRequest(BUCKET_NAME,FILE_NAME);
        S3Object s3Object = amazonS3.getObject(request);
        try {
            StringBuilder fileContent = new StringBuilder(IOUtils.toString(s3Object.getObjectContent()));
            fileContent.append(data.getData());
            data.setData(fileContent.toString());
            return storeData(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseEntity<?> searchData(SearchDataRequest data){
        AmazonS3 amazonS3 = AmazonS3ClientCreator.getInstance();
        GetObjectRequest request = new GetObjectRequest(BUCKET_NAME,FILE_NAME);
        S3Object s3Object = amazonS3.getObject(request);
        try {
            StringBuilder fileContent = new StringBuilder(IOUtils.toString(s3Object.getObjectContent()));
            String[] lines = fileContent.toString().split("\n");

            String regex = data.getRegex();
            regex = regex.replaceAll("\\\\b\\b", "\\\\\\\\b");

            Pattern pattern = Pattern.compile(regex);
            System.out.println(pattern.matcher(fileContent).find());
            for (String line:lines) {
                if(pattern.matcher(line).find()){
                    return  ResponseEntity.ok(new SearchDataResponse(true,line));
                }
            }
            return ResponseEntity.ok(new SearchDataResponse(false,""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseEntity<?> deleteDataFile(DeleteStoreDataFileRequest request){
        AmazonS3 amazonS3 = AmazonS3ClientCreator.getInstance();
        AmazonS3URI amazonS3URI = new AmazonS3URI(request.getS3uri());
        amazonS3.deleteObject(new DeleteObjectRequest(amazonS3URI.getBucket(),amazonS3URI.getKey()));
        return ResponseEntity.ok("File has been deleted!");
    }
}
