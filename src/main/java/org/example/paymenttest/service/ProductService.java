package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.Product;
import org.example.paymenttest.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public List<Product> findAll(){
        return productRepository.findAll();
    }


    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
