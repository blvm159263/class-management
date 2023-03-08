package com.mockproject.Controller;

import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import com.mockproject.service.interfaces.ITrainingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/trainingclass")
public class ClassScheduleController {
    @Autowired
    IClassScheduleService classScheduleService;

    @GetMapping("/day/{day}")
    public List<TrainingClassFilterResponseDTO> getTrainingClassByDay(@PathVariable("day")LocalDate date){
        return classScheduleService.getTrainingClassByDay(date);
    }

    @GetMapping("/week/{startDate}/{endDate}")
    public List<TrainingClassFilterResponseDTO> getTrainingClassByWeek(@PathVariable("startDate")LocalDate start,@PathVariable("endDate")LocalDate end){
        return classScheduleService.getTrainingClassByWeek(start,end);
    }
}
