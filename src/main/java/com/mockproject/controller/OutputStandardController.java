package com.mockproject.controller;

import com.mockproject.dto.OutputStandardDTO;
import com.mockproject.service.OutputStandardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/outputStandard")
@SecurityRequirement(name = "Authorization")
public class OutputStandardController {

    @Autowired
    OutputStandardService outputStandardService;

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
