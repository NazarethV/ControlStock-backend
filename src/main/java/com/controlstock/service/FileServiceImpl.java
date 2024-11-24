package com.controlstock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{


    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload an empty file");
        }

        // Generar un nombre único usando UUID
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

        // Validar extensión permitida
        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        if (!allowedExtensions.contains(fileExtension)) {
            throw new IllegalArgumentException("Invalid file type. Only JPG, JPEG, PNG, and GIF are allowed.");
        }


        // Crear el directorio si no existe
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); //O mkdir
        }


        return uniqueFileName;
    }


    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        String filePath = path + File.separator + filename;
        return new FileInputStream(filePath);
    }
}
