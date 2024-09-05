package com.controlstock.controllers;

import com.controlstock.dto.ProductDto;
import com.controlstock.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

@PostMapping("/add-product")   //En el BODY envío la info en la variable 'productDto' y 'file'(para la imagen)
public ResponseEntity<ProductDto> addProductHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String productDto) throws IOException, RuntimeException{
    //Agrego después las excepciones

    //Interactua con el Service
    ProductDto dto = convertToProductDto(productDto);
    return new ResponseEntity<>(productService.addProduct(dto, file), HttpStatus.CREATED);

}

@GetMapping("/{productId}")
public ResponseEntity<ProductDto> getProductHandler(@PathVariable Integer productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
}


@GetMapping("/all")
public ResponseEntity<List<ProductDto>> getAllProductsHandler() {
        return ResponseEntity.ok(productService.getAllProducts());
}

@PutMapping("/update/{productId}")   //En el BODY envío la info en la variable 'productDtoObj' y 'file'(para la imagen)
public ResponseEntity<ProductDto> updateProductHandler(@PathVariable Integer productId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String productDtoObj) throws IOException {
   if (file.isEmpty()) file = null;
   ProductDto productDto = convertToProductDto(productDtoObj);
   return ResponseEntity.ok(productService.updateProduct(productId, productDto, file));
}


@DeleteMapping("/delete/{productId}")
public ResponseEntity<String> deleteProductHandler(@PathVariable Integer productId) throws IOException {
        return ResponseEntity.ok(productService.deleteProduct(productId));
}


//Método Genérico para la CONVERSIÓN del Obj Product
private ProductDto convertToProductDto(String productDtoObj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(productDtoObj, ProductDto.class);
}

}
