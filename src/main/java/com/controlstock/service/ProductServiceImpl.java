package com.controlstock.service;


import com.controlstock.dto.ProductDto;
import com.controlstock.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    //Para la Base de datos
    private final ProductRepository productRepository;

    //
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Value("${project.imageProduct}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public ProductDto addProduct(ProductDto movieDto, MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public ProductDto getProduct(Integer productId) {
        return null;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return List.of();
    }

    @Override
    public ProductDto updateProduct(Integer productId, ProductDto productDto, MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public String deleteProduct(Integer productId) throws IOException {
        return "";
    }
}
