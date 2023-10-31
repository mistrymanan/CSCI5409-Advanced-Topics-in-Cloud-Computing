package com.example.a3.services;

import com.example.a3.dto.Product;
import com.example.a3.dto.ProductsRequest;
import com.example.a3.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {

    @Value("${app.Type}")
    private String appType;

//    @Autowired
//    ProductRepository productRepository;

//    public void save(ProductsRequest productList){
//        productRepository.saveAll(productList.getProducts());
//    }

//    public List<Product> getProducts() {
//        List<Product> productList = new LinkedList<>();
//    productRepository
//        .findAll()
//        .forEach(
//            product -> {
//              Product product1 =
//                  new Product(product.getName(), product.getPrice(), product.isAvailability());
//              productList.add(product1);
//            });
//    return productList;
//    }
}
