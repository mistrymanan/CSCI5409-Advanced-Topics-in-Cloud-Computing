package com.example.a3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListProductResponseWithCache {
    List<Product> products;
    boolean cache;
}
