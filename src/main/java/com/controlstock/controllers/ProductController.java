package com.controlstock.controllers;

import com.controlstock.dto.ProductDto;
import com.controlstock.dto.ProductPageResponse;
import com.controlstock.entities.Product;
import com.controlstock.exceptions.EmptyFileException;
import com.controlstock.repositories.ProductRepository;
import com.controlstock.service.FileService;
import com.controlstock.service.ProductService;
import com.controlstock.utils.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final FileService fileService; // Inyección de dependencias de FileService

    public ProductController(ProductService productService, ProductRepository productRepository, FileService fileService) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.fileService = fileService;
    }

    // Inyectamos 'path' desde el archivo de configuración
    @Value("${project.imageProduct}")
    private String path;

@PreAuthorize("hasAuthority('ADMIN')")   //Sólo el usuario ADMIN puede agregar los productos
@PostMapping("/add-product")
public ResponseEntity<ProductDto> addProductHandler(@RequestParam MultipartFile file,
                                                    @RequestPart String productDto) throws IOException, EmptyFileException {
    //Las excepciones (en caso de que el archivo esté vacio)
    if (file.isEmpty()) {
        throw new EmptyFileException("File is empty! Please send another file");
    }

    //Se Interactua con el Service
    ProductDto dto = convertToProductDto(productDto); //Se convierte cadena a un JSON
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
                                                       @RequestPart(required = false) MultipartFile file,
                                                       @RequestPart String productDtoObj) throws IOException {
   if (file != null && file.isEmpty()) {
       file = null;
   }

   ProductDto productDto = convertToProductDto(productDtoObj);
   return ResponseEntity.ok(productService.updateProduct(productId, productDto, file));
}

//@PreAuthorize("hasAuthority('ADMIN')")
@DeleteMapping("/delete/{productId}")
public ResponseEntity<String> deleteProductHandler(@PathVariable Integer productId) throws IOException {
        return ResponseEntity.ok(productService.deleteProduct(productId));
}

@GetMapping("/allProductsPage")
public ResponseEntity<ProductPageResponse> getProductsWithPagination(
        @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
){
        return ResponseEntity.ok(productService.getAllProductsWithPagination(pageNumber, pageSize));
}

@GetMapping("/allProductsPageSort")
public ResponseEntity<ProductPageResponse> getProductsWithPaginationAndSorting(
        @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
        @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir
) {
       return ResponseEntity.ok(productService.getAllProductsWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir));
}

//Método Genérico para la CONVERSIÓN del Obj Product
private ProductDto convertToProductDto(String productDtoObj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(productDtoObj, ProductDto.class);
}

}
