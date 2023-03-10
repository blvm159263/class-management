package com.mockproject.controller;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.service.interfaces.ITrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/trainingClass")
public class TrainingClassController {
    private final ITrainingClassService trainingClassService;

    @GetMapping("{classCode}")
    public TrainingClassDTO getTrainingClassByClassCode(@PathVariable("classCode") String code){
        return  trainingClassService.getTrainingClassByClassCode(code);
    }
}
