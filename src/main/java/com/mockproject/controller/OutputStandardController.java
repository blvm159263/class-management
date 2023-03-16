package com.mockproject.controller;

import com.mockproject.dto.OutputStandardDTO;
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
@RequestMapping(value = "/api/outputStandard")
public class OutputStandardController {

    private final IOutputStandardService outputStandardService;

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
