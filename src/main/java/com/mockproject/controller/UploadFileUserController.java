package com.mockproject.controller;

import com.mockproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
    public ResponseEntity downloadTemplate() throws FileNotFoundException {
        String fileURL = "src/main/resources/CSVFile/Template.xlsx";
//        File file = new File("src/main/resources/CSVFile/Template .xlsx");
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        RestTemplate restTemplate = new RestTemplate();
        byte[] fileBytes = restTemplate.getForObject(fileURL, byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "Template.xlsx");

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity readFileCSV(@RequestParam("file") MultipartFile mFile, @RequestParam("scanning") String scanning,
                                      @RequestParam("duplicate")String duplicateHandle) {
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
        status = userService.readCSVFile(file,scanning,duplicateHandle);
        return ResponseEntity.ok(status);
    }

}