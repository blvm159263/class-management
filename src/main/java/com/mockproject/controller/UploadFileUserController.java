package com.mockproject.controller;

import com.mockproject.service.UserService;
import com.mockproject.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/import")
@SecurityRequirement(name = "Authorization")
public class UploadFileUserController {
    public static final String VIEW = "ROLE_View_User";
    public static final String MODIFY = "ROLE_Modify_User";
    public static final String CREATE = "ROLE_Create_User";
    public static final String FULL_ACCESS = "ROLE_Full access_User";
    private final IUserService userService;

    @GetMapping("/template-csv")
    @Secured({CREATE, MODIFY, FULL_ACCESS})
    public ResponseEntity downloadTemplate(){
        File file = new File("src/main/resources/CSVFile/Template .xlsx");
        return ResponseEntity.ok(file);
    }

    @PostMapping("/user")
    @Secured({CREATE, MODIFY, FULL_ACCESS})
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
            ResponseEntity.badRequest().body("Can't read file!!!");
        }
        status = userService.readCSVFile(file);
        return ResponseEntity.ok(status);
    }

}