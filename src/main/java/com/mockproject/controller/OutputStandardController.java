package com.mockproject.controller;

import com.mockproject.dto.OutputStandardDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.mockproject.service.interfaces.IOutputStandardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Output Standard API")
@RequestMapping("/api/osd")
public class OutputStandardController {

    private final IOutputStandardService outputStandardService;

    @GetMapping("/{syllabusId}")
    @Operation(
            summary = "Get output standard by syllabus ID"
    )
    public ResponseEntity<?> getOsdBySyllabusId(
            @PathVariable("syllabusId")
            @Parameter(
                    description = "<b>Insert syllabus ID to get output standard<b>",
                    example = "7"
            ) long id) {
        return ResponseEntity.ok(outputStandardService.getOsdBySyllabusId(true, id));
    }

    @GetMapping("/{outputStandardId}")
    public ResponseEntity<OutputStandardDTO> getOutputStandardById(@PathVariable("outputStandardId") long id){
        OutputStandardDTO outputStandardDTO = outputStandardService.getOutputStandardById(id, true);
        return ResponseEntity.ok(outputStandardDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<OutputStandardDTO>> getAll(){
        List<OutputStandardDTO> outputStandardDTOList = outputStandardService.getOutputStandard(true);
        return ResponseEntity.ok(outputStandardDTOList);
    }

}
