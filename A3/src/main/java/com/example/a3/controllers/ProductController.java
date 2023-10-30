package com.example.a3.controllers;

import com.example.a3.dto.*;
import com.example.a3.services.CacheService;
import com.example.a3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CacheService cacheService;

    @Value("${app.Type}")
    private String appType;

    @PostMapping("/store-products")
    ResponseEntity<Message> storeProducts(@RequestBody ProductsRequest productList){
        if(appType.equals("RDS")){
            System.out.println("RDS");
            productService.save(productList);
        }else if(appType.equals("REDIS")){
            System.out.println("REDIS");
            cacheService.storeInRedisApplication(productList.getProducts());
        }
        return ResponseEntity.ok(new Message("Success."));
    }
    @GetMapping("/list-products")
    ResponseEntity<ListProductResponseWithCache> getProductList(){
        ListProductResponseWithCache response = new ListProductResponseWithCache();
        if(appType.equals("RDS")){
            System.out.println("RDS");
            if(cacheService.isKeyInCache("products")){
                List<Product> productResponse = cacheService.getFromRedis();
                response.setProducts(productResponse);
                response.setCache(true);
            }else{
                List<com.example.a3.dto.Product> productList = productService.getProducts();
                response.setProducts(productList);
                response.setCache(false);
                cacheService.storeInRedis(productList);
            }
        }else if(appType.equals("REDIS")){
            System.out.println("REDIS");
            List<Product> productResponse = cacheService.getFromRedis();
            response.setProducts(productResponse);
            response.setCache(true);
        }
        return ResponseEntity.ok(response);
    }
}
