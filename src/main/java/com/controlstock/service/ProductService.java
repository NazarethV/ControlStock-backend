package com.controlstock.service;

import com.controlstock.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto movieDto, MultipartFile file) throws IOException;

    ProductDto getProduct(Integer productId);

    List<ProductDto> getAllProducts();

    ProductDto updateProduct(Integer productId, ProductDto productDto, MultipartFile file) throws IOException;

    String deleteProduct(Integer productId) throws IOException;

    //ProductPageResponse getAllProductsWithPagination(Integer pageNumber, Integer pageSize);

    //ProductPageResponse getAllProductsWithPaginationAndSorting(Integer pageNumber, Integer pageSize,
                                                              // String sortBy, String dir);

}
