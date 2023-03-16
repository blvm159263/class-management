package com.mockproject.controller;

import com.mockproject.dto.UnitDTO;
import com.mockproject.service.interfaces.IUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/unit")
public class UnitController {

    private final IUnitService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Unit"),
            @ApiResponse(responseCode = "200", description = "When found Unit",
            content = @Content(schema = @Schema(implementation = UnitDTO.class)))
    })
    @Operation(summary = "Get All Unit by given Session ID")
    @GetMapping("list-by-session/{sid}")
    public ResponseEntity<?> listBySessionId(@Parameter(description = "Session ID") @PathVariable("sid") Long sid) {
        List<UnitDTO> list = service.listBySessionId(sid);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Unit!");
        }
    }



    @Operation(
            summary = "Get all class Units for day-nth of total days of the class schedule",
            description = "Get list of Units in a date clicked in the class schedule table by the user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Day [-] of Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = UnitDTO.class)))
    })
    @GetMapping("/class-units-for-a-date")
    public ResponseEntity<?> getAllUnitsForADate(
            @Parameter(description = "TrainingClass id", example = "1") @Param("id") long id,
            @Parameter(description = "day-nth of total days of the class schedule", example = "1") @Param("dayNth") int dayNth
    ) {
        try{
            return ResponseEntity.ok(service.getAllUnitsForADateByTrainingClassId(id, dayNth));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Day [" + dayNth + "] of Training class id[" + id + "] not found!!!");
        }
    }
}
