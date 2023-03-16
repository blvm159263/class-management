package com.mockproject.controller;

import com.mockproject.dto.FsuDTO;
import com.mockproject.service.interfaces.IFsuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Fsu API")
@RequestMapping("api/fsu")
public class FsuController {

    private final IFsuService fsuService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Fsu"),
            @ApiResponse(responseCode = "200", description = "When found Fsu",
                    content = @Content(schema = @Schema(implementation = FsuDTO.class)))
    })
    @Operation(summary = "Get all FSU have status = True")
    @GetMapping("")
    public ResponseEntity<?> listAll() {
        List<FsuDTO> list = fsuService.listAllTrue();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Fsu!");
        }
    }

    @GetMapping("/list")
    @Operation(
            summary = "Get fsu list"
    )
    public ResponseEntity<?> getAllFsu(){
        return ResponseEntity.ok(fsuService.getAllFsu(true));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get fsu by ID"
    )
    public ResponseEntity<?> getFsuById(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert ID to get fsu<b>",
                    example = "1"
            ) long id) {
        return ResponseEntity.ok(fsuService.getFsuById(true, id));
    }


    @Operation(summary = "Get class's Fsu by TrainingClass id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No Such Value", content = @Content(schema = @Schema(defaultValue = "Training class id[-] not found!!!"))),
            @ApiResponse(responseCode = "200", description = "Return Sample", content = @Content(schema = @Schema(implementation = FsuDTO.class)))
    })
    @GetMapping("/class-fsu")
    public ResponseEntity<?> getClassFsu(@Parameter(description = "TrainingClass id", example = "1") @Param("id") long id) {
        try{
            return ResponseEntity.ok(fsuService.getFsuByTrainingClassId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training class id[" + id + "] not found!!!");
        }
    }
}
