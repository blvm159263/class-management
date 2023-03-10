package com.mockproject.controller;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.dto.TrainingClassAdminDTO;
import com.mockproject.service.interfaces.ITrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/trainingClass")
public class TrainingClassController {
    private final ITrainingClassService trainingClassService;


}
