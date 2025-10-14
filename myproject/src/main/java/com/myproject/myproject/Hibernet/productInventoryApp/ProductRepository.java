package com.myproject.myproject.Hibernet.productInventoryApp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);
    List<Product> findByPriceLessThan(Double price);

    List<Product> findByPriceLessThanAndName(Double price,String name);


}
