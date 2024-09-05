package com.controlstock.service;


import com.controlstock.dto.ProductDto;
import com.controlstock.dto.ProductPageResponse;
import com.controlstock.entities.Product;
import com.controlstock.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private String path; //Saqué la url de las imágenes/archivos de los productos ("imageProduct/")

    @Value("${base.url}")
    private String baseUrl; //Saqué le url base ("http://localhost:8080")

    @Override
    public ProductDto addProduct(ProductDto productDto, MultipartFile file) throws IOException {
        //1- Cargar archivo (verifico si el nombre del archivo ya existe o no)
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))){
            throw new RuntimeException("File already exists: Please enter another file name!");
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
        //1-Comprobar existencia del producto en la DB (Y si existe devuelve el Obj Product con ese ID)
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id =" + productId));

        //2- Genero la URL de la imagen del producto
        String imageUrl = baseUrl + "/file/" + product.getImage();

        //3- Genero la respuesta que va a devolver este método (Devuelvo Obj DTO del producto)
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getSupplier(),
                product.getImage(),
                imageUrl
        );
    }



    @Override
    public List<ProductDto> getAllProducts() {
        //1- Recupero todos los productos de la DB y lo guardo en 'products'
        List<Product> products = productRepository.findAll();
        //Creo una lista vacía para guardar la lista de Obj products
        List<ProductDto> productDtos = new ArrayList<>();

        //2- Transformo la lista de Obj products en una lista de Obj DTO (y genero la URL de la image/archivo)
        for (Product product : products) { //Cada product de la lista de products
            String imageUrl = baseUrl + "/file/" + product.getImage();
            ProductDto response = new ProductDto(
                    product.getProductId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    product.getCategory(),
                    product.getSupplier(),
                    product.getImage(),
                    imageUrl
            );

            productDtos.add(response);
        }

        //Devuelvo la Lista DTO de productos
        return productDtos;
    }


    @Override
    public ProductDto updateProduct(Integer productId, ProductDto productDto, MultipartFile file) throws IOException {
        //1- Compruebo que el Product exista en la DB
        Product prod = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id = " + productId));

        //2-Verifico si hay un nuevo archivo/imagen para reemplazar al antiguo (Si hay, borro el viejo y guardo el nuevo, si no hay no hago nada)
       String fileName = prod.getImage();
       if (file != null) {
           Files.deleteIfExists(Paths.get(path + File.separator + fileName));
           fileName = fileService.uploadFile(path, file);
       }

       //3- Defino el nombre del archivo/imagen de ProductDto según el proceso anterior (fileName en el if)
        productDto.setImage(fileName);

       //4-Asigno el cambio/actualización al objeto Movie
        Product product = new Product(
                prod.getProductId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock(),
                productDto.getCategory(),
                productDto.getSupplier(),
                productDto.getImage()
        );

        //5- Guardo el Obj Product con los cambios actualizados
        Product updatedProduct = productRepository.save(product);

        //6-Genero la URL de la imagen/archivo del producto
        String imageUrl = baseUrl + "/file/" + fileName;

        //7- Retorno el DTO del producto como respuesta
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getSupplier(),
                product.getImage(),
                imageUrl
        );
    }


    @Override
    public String deleteProduct(Integer productId) throws IOException {
        //1- Verifico si el producto existe en la DB
        Product prod = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id = " + productId));
        //Si se encuentra el Product en la DB, se guarda el ID
        Integer id = prod.getProductId();

        //2- Se elimina primero el archivo asociado al Obj Product que se quiere eliminar
        Files.deleteIfExists(Paths.get(path + File.separator + prod.getImage()));

        //3- Elimino el Obj Prod del repositorio (es decir de la base de datos)
        productRepository.delete(prod);

        return "Product deleted with id = " + id;
    }


    @Override
    public ProductPageResponse getAllProductsWithPagination(Integer pageNumber, Integer pageSize) {
        //Pageable: Interfaz para la configuración del PAGINADO
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> productPages = productRepository.findAll(pageable);
        List<Product> products = productPages.getContent();

        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            String imageUrl = baseUrl + "/file/" + product.getImage();
            ProductDto productDto = new ProductDto(
                    product.getProductId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    product.getCategory(),
                    product.getSupplier(),
                    product.getImage(),
                    imageUrl
            );
            productDtos.add(productDto);
        }
        return new ProductPageResponse(productDtos, pageNumber, pageSize,
                productPages.getTotalElements(),
                productPages.getTotalPages(),
                productPages.isLast());
    }

    @Override
    public ProductPageResponse getAllProductsWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> productPages = productRepository.findAll(pageable);
        List<Product> products = productPages.getContent();

        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            String imageUrl = baseUrl + "/file/" + product.getImage();
            ProductDto productDto = new ProductDto(
                    product.getProductId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    product.getCategory(),
                    product.getSupplier(),
                    product.getImage(),
                    imageUrl
            );
            productDtos.add(productDto);
        }
        return new ProductPageResponse(productDtos, pageNumber, pageSize,
                productPages.getTotalElements(),
                productPages.getTotalPages(),
                productPages.isLast()
        );
    }


}















