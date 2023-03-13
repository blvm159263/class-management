package com.mockproject.controller;

import com.mockproject.service.interfaces.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/file-csv")
public class FileController {

    private final IFileService service;

    @PostMapping("")
    public ResponseEntity<?> readFile(@RequestParam("file") MultipartFile file) throws IOException {
        service.readFileCsv(file);
        return ResponseEntity.ok().build();
    }
}
