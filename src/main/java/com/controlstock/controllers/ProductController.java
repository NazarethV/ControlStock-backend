package com.controlstock.controllers;

import com.controlstock.dto.ProductDto;
import com.controlstock.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

@PostMapping("/add-product")
public ResponseEntity<ProductDto> addProductHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String productDto) throws IOException, RuntimeException{
    //Agrego después las excepciones

    //Interactua con el Service
    ProductDto dto = convertToProductDto(productDto);
    return new ResponseEntity<>(productService.addProduct(dto, file), HttpStatus.CREATED);

}



//Método Genérico para la CONVERSIÓN del Obj Product
private ProductDto convertToProductDto(String productDtoObj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(productDtoObj, ProductDto.class);
}

}
