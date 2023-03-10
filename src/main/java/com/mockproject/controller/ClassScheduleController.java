package com.mockproject.controller;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/classSchedule")
public class ClassScheduleController {
    private final IClassScheduleService classScheduleService;

    @GetMapping("classSchedule/{code}")
    public List<ClassScheduleDTO> getScheduleByClassCode(@PathVariable("code") String code) {
        return classScheduleService.getScheduleByClassCode(code);
    }
}
