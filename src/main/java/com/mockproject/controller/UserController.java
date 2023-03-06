package com.mockproject.controller;

import com.mockproject.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final IUserService service;

    @GetMapping("/class-admin")
    public ResponseEntity<?> listClassAdmin(){
        return ResponseEntity.ok(service.listClassAdminTrue());
    }

}
