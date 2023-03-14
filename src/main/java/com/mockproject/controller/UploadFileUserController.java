package com.mockproject.controller;

import com.mockproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/import")

public class UploadFileUserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseEntity readFileCSV(@RequestParam("file") MultipartFile mFile)  {
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss.SSS");
        String path = "src/main/resources/CSVFile/" + String.valueOf(current.format(formatter))+"-"+mFile.getOriginalFilename();
        File file = new File(String.valueOf(path));
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            mFile.transferTo(file);
        } catch (IOException e) {

        }
        String status = userService.readCSVFile(file);

        return ResponseEntity.ok(status);
    }

}