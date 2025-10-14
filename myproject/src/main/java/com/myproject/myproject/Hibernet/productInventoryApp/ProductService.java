package com.myproject.myproject.Hibernet.productInventoryApp;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

   private final ProductRepository productRepository;

   public ProductService(ProductRepository productRepository){
       this.productRepository = productRepository;
   }

   public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getProductByName(String name){
       return (productRepository.findByName(name));
    }

    public List<Product> getProductByLessThan(Double price){
       return productRepository.findByPriceLessThan(price);
    }

    public List<Product> getProductByPriceAndName(Double price, String name){
       return productRepository.findByPriceLessThanAndName(price,name);
    }

    public Product updateProduct(Long id, Product productDetails){
        Product product = productRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        return productRepository.save(product);
    }

    public Product deleteProductById(Long id){
       Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
       productRepository.delete(product);
       return product;
    }


}
