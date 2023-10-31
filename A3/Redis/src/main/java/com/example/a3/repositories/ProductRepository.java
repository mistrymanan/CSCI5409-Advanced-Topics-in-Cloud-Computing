package com.example.a3.repositories;

import com.example.a3.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    @Override
    <S extends Product> S save(S entity);
    @Override
    <S extends Product> Iterable<S> saveAll(Iterable<S> entities);
}
