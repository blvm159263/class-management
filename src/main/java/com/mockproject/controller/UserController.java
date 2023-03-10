package com.mockproject.controller;

import com.mockproject.dto.UserDTO;
import com.mockproject.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final IUserService userService;

    @GetMapping("trainingClass/trainer/{classCode}")
    public List<UserDTO> getTrainerByClassCode(@PathVariable("classCode") String code) {
        return userService.getTrainerByClassCode(code);
    }
}
