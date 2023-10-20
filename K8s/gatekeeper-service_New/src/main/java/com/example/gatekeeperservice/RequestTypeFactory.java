package com.example.gatekeeperservice;

public class RequestTypeFactory {
    public static RequestType getInstance(String key){
        if(key.equals("location")){
            return new LocationRequest();
        }else if(key.equals("temperature")){
            return new TemperatureRequest();
        }else if(key.equals("store")){
            return new StoreRequest();
        }
        return null;
    }
}
