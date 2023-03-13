package com.mockproject.controller;

import com.mockproject.service.interfaces.IFileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/file-csv")
public class FileController {

    private final IFileService service;

    @PostMapping("")
    public ResponseEntity<?> readFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(service.readFileCsv(file));
    }

    @Operation(summary = "A API like a link when access will down load Create class Format File")
    @GetMapping("/download/class-format")
    public ResponseEntity<?> downClassFormatFile() throws IOException {
        Path path = Paths.get("file-format", "create-class-format.csv");
        byte[] buffer = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(buffer);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=create-class-format.csv");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(buffer.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
