package com.example.gatekeeperservice;

public class RequestTypeFactory {
    public static RequestType getInstance(String key){
        if(key.equals("location")){
            return new LocationRequest();
        }else{
            return new TemperatureRequest();
        }
    }
}
