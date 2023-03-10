package com.mockproject.controller;

import com.mockproject.dto.TrainingClassAdminDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.service.interfaces.ITrainingClassAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/trainingClassAdmin")
public class TrainingClassAdminController {

    private final ITrainingClassAdminService trainingClassAdminService;
    @GetMapping("admin/{code}")
    public List<UserDTO> getAdminByClassCode(@PathVariable("code") String code) {
        return trainingClassAdminService.getAdminByClassCode(code);
    }
}
