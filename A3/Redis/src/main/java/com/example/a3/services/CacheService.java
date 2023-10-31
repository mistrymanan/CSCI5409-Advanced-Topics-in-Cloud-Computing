package com.example.a3.services;

import com.example.a3.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CacheService {
    @Autowired
    private RedisTemplate<String, List<Product>> redisTemplate;

    public boolean isKeyInCache(String key){
        return redisTemplate.hasKey(key);
    }
    public void storeInRedisApplication(List<com.example.a3.entity.Product> productList){
        List<Product> productList1 = new LinkedList<>();
         productList
                .forEach(
                        product -> {
                            Product product1 =
                                    new Product(product.getName(), product.getPrice(), product.isAvailability());
                            productList1.add(product1);
                        });
    }
    public void storeInRedis(List<Product> products){
        ValueOperations<String, List<Product>> valueOps = redisTemplate.opsForValue();
        valueOps.set("products", products);
    }
    public List<Product> getFromRedis(){
        ValueOperations<String,List<Product>> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get("products");
    }
}
