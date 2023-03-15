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
import com.mockproject.service.interfaces.IClassScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
    private final IClassScheduleService classScheduleService;

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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "When list of ClassShedule have been saved success!"),
            @ApiResponse(responseCode = "400", description = "When Saving fail!")
    })
    @Operation(summary = "Save list of ClassSchedule by given Training Class")
    @PostMapping("list/traing-class/{tcId}")
    public ResponseEntity<?> createListSchedule(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                content = @Content(examples = @ExampleObject(value = "[\"2023-03-14\",\"2023-03-15\"]")))
                                                @Valid @RequestBody List<LocalDate> listDate,
                                                @Parameter(description = "Training Class ID when call create Training Class API return")
                                                @PathVariable("tcId") Long tcId){
        if(classScheduleService.saveClassScheduleForTrainingClass(listDate, tcId)){
            return new ResponseEntity<>("List of Date have been saved!", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Saving Fail!", HttpStatus.BAD_REQUEST);
        }
    }
}
