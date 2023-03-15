package com.mockproject.controller;

import com.mockproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/template-csv")
    public ResponseEntity downloadTemplate(){
        File file = new File("src/main/resources/CSVFile/Template .xlsx");
        return ResponseEntity.ok(file);
    }

    @PostMapping("/user")
    public ResponseEntity readFileCSV(@RequestParam("file") MultipartFile mFile) {
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss.SSS");
        String path = "src/main/resources/CSVFile/" + String.valueOf(current.format(formatter)) + "-" + mFile.getOriginalFilename();
        File file = new File(path);
        String status=null;

        try {
            if (!file.getName().toLowerCase().endsWith("csv")) {
                return ResponseEntity.badRequest().body("Not CSV file");}
            else{
                mFile.transferTo(Path.of(path));

            }
        } catch (IOException e) {

        }
        status = userService.readCSVFile(file);
        return ResponseEntity.ok(status);
    }

}