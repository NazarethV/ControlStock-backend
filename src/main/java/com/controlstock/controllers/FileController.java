package com.controlstock.controllers;


import com.controlstock.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file/")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Value("${project.imageProduct}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException{
        String uploadedFileName = fileService.uploadFile(path, file);
        return ResponseEntity.ok("File uploaded: " + uploadedFileName);
    }



}
