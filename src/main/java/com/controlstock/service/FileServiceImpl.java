package com.controlstock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{


    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        //String fileName = file.getOriginalFilename();
        //String filePath = path + File.separator + fileName;
        //File f = new File(path);
        //if(!f.exists()){
            //f.mkdir(); // mkdir: sirve para crear un nuevo directorio en el sistema de archivos. Si el directorio ya existe, no hace nada
        //}

        // Generar un nombre único usando UUID
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

        // Crear el directorio si no existe
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Construir la ruta completa del archivo
        String filePath = path + File.separator + uniqueFileName;

        ///////////////////////7
        // Validar extensión permitida
        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        if (!allowedExtensions.contains(fileExtension)) {
            throw new IllegalArgumentException("Invalid file type. Only JPG, JPEG, PNG, and GIF are allowed.");
        }
        //////////////////////

        // Guardar el archivo en la ubicación especificada
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //return fileName;
        return uniqueFileName;
    }


    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        String filePath = path + File.separator + filename;
        return new FileInputStream(filePath);
    }
}
