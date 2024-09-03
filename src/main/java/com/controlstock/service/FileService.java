package com.controlstock.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    //Cargar Archivo
    //            Dirección Archivo - Tipo de archivo
    String uploadFile(String path, MultipartFile file) throws IOException;

    //Encuentra el archivo y lo convierte en un flujo de datos para leerlo poco a poco, en lugar de cargarlo todo en la memoria
    InputStream getResourceFile(String path, String filename) throws FileNotFoundException;
}
