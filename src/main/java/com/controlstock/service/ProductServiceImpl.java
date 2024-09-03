package com.controlstock.service;


import com.controlstock.dto.ProductDto;
import com.controlstock.entities.Product;
import com.controlstock.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    //Para la Base de datos
    private final ProductRepository productRepository;
    private final FileService fileService;

    //Parámetros del constructor
    public ProductServiceImpl(ProductRepository productRepository, FileService fileService) {
        this.productRepository = productRepository;
        this.fileService = fileService;
    }

    @Value("${project.imageProduct}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public ProductDto addProduct(ProductDto productDto, MultipartFile file) throws IOException {
        //1- Cargar archivo (verifico si el nombre del archivo ya existe o no)
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))){
            throw new RuntimeException("File already exists: Please enter another file name!")
        }

        String uploadedFileName = fileService.uploadFile(path, file); //Para cargar el archivo hay que traer el método de servicio de archivos 'FileService'

        //2- Pongo cómo nombre de archivo el valor del campo 'imageProduct'
        productDto.setImage(uploadedFileName);//Pongo el nombre al archivo

        //3-Mapeo el producto DTO al objeto PRODUCT (ProductRepository guarda los datos en la DB y acepta objetos 'Product' por lo que es necesario mapearlo previamente)
        //Asigno el Obj DTO al Obj Product
        Product product = new Product(
                null, //Para no poder de manera directa el ID
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock(),
                productDto.getCategory(),
                productDto.getSupplier(),
                productDto.getImage()
        );

        //4- Guardo el nuevo Obj Product en la DB
        Product savedProduct = productRepository.save(product);

        //5- Genero la URL para la imagen/archivo del producto
        String imageUrl = baseUrl + "/file/" + uploadedFileName;

        //6- Genero la respuesta que va a devolver este método (Mapeo lo del Obj Product al Obj DTO)
        return new ProductDto(
                savedProduct.getProductId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStock(),
                savedProduct.getCategory(),
                savedProduct.getSupplier(),
                savedProduct.getImage(),
                imageUrl
        );
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
