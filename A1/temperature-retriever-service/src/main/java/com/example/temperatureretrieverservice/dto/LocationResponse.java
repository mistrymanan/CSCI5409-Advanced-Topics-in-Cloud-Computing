package com.example.temperatureretrieverservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    String file;
    Float latitude;
    Float longitude;
}
