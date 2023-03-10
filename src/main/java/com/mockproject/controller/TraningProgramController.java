package com.mockproject.controller;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.service.interfaces.ITrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/trainingPropram")
public class TraningProgramController {
    private final ITrainingProgramService trainingProgramService;

    @GetMapping("trainingClass/{classCode}")
    public TrainingProgramDTO getTraningProgramByClassCode(@PathVariable("classCode") String code, boolean status) {
        return trainingProgramService.getTraningProgramByClassCode(code, status);
    }
}
