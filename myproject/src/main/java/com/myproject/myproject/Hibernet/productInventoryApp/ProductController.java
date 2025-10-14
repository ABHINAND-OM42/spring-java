package com.myproject.myproject.Hibernet.productInventoryApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService service;


    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return service.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProduct(){
        return service.getAllProducts();
    }

    @GetMapping("/search/name")
    public List<Product> getByName(@RequestParam String name){
        return service.getProductByName(name);
    }

    @GetMapping("search/lessthan")
    public List<Product> getProductByLessThan(@RequestParam Double price){
        return service.getProductByLessThan(price);
    }

    @GetMapping("search/lessthan/name")
    public List<Product> getProductByPriceAndName(@RequestParam Double price){
        return service.getProductByLessThan(price);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product){
        return  service.updateProduct(id,product);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable Long id){
        return service.deleteProductById(id);
    }
}
