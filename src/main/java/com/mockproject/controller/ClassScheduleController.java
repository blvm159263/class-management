package com.mockproject.controller;

import com.mockproject.dto.SearchByDTO;
import com.mockproject.dto.TrainingClassFilterRequestDTO;
import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.service.interfaces.IClassScheduleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classschedule")
@SecurityRequirement(name = "Authorization")
@Slf4j
public class ClassScheduleController {
    public static final String VIEW = "ROLE_View_Class";
    public static final String MODIFY = "ROLE_Modify_Class";
    public static final String CREATE = "ROLE_Create_Class";
    public static final String FULL_ACCESS = "ROLE_Full access_Class";
    IClassScheduleService classScheduleService;

    @PostMapping("/day")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public List<TrainingClassFilterResponseDTO> getTrainingClassByDay(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {
        return classScheduleService.getTrainingClassByDay(filterRequestDTO);
    }

    @PostMapping("/week")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public List<TrainingClassFilterResponseDTO> getTrainingClassByWeek(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {
        log.info(filterRequestDTO.toString());
        return classScheduleService.getTrainingClassByWeek(filterRequestDTO);
    }

    @PostMapping("/search/day")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public List<TrainingClassFilterResponseDTO> searchTrainingClassInDay(@RequestBody SearchByDTO searchByDTO) {
        return classScheduleService.searchTrainingClassInDate(searchByDTO.getSearchText(), searchByDTO.getNowDate());
    }

    @PostMapping("/search/week")
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public List<TrainingClassFilterResponseDTO> searchTrainingClassInWeek(@RequestBody SearchByDTO searchByDTO) {
        return classScheduleService.searchTrainingClassInWeek(searchByDTO.getSearchText(), searchByDTO.getStartDate(), searchByDTO.getEndDate());
    }

}
