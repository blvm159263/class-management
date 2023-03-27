package com.mockproject.controller;

import com.mockproject.dto.ReadFileDto;
import com.mockproject.dto.SearchTPDTO;
import com.mockproject.dto.TrainingProgramAddDto;
import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.service.interfaces.IFileService;
import com.mockproject.service.interfaces.ITrainingProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Training Program API")
@RequestMapping("api/training-program")
@SecurityRequirement(name = "Authorization")
@Slf4j
public class TrainingProgramController {

    public static final String VIEW = "ROLE_View_Training program";
    public static final String MODIFY = "ROLE_Modify_Training program";
    public static final String CREATE = "ROLE_Create_Training program";
    public static final String FULL_ACCESS = "ROLE_Full access_Training program";

    private final ITrainingProgramService trainingProgramService;
    private final IFileService fileService;
    private final ResourceLoader resourceLoader;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Program"),
            @ApiResponse(responseCode = "200", description = "When find training program and return list program",
                    content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
    })
    @Operation(summary = "Get all Training Program")
    @GetMapping("list")
    public ResponseEntity<?> getAllTrainingProgram(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<TrainingProgramDTO> list = trainingProgramService.getAll(pageNo, pageSize);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Training Program!");
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Program"),
            @ApiResponse(responseCode = "200", description = "When find training program and return list program",
                    content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
    })
    @Operation(summary = "Get Training Program by searching name or creator")
    @PostMapping("search-name")
    public ResponseEntity<?> searchByName(@Parameter(description = "Training Program Name want to search") @RequestParam(defaultValue = "") String name) {
        List<TrainingProgramDTO> list = trainingProgramService.searchByName(name);
//        List<TrainingProgramDTO> list = trainingProgramService.searchByNameOrCreator(search);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Training Program!");
        }
    }

    @PostMapping("/uploadCsv")
    public ResponseEntity readFileCsv(@Valid @ModelAttribute ReadFileDto readFileDto) {
        MultipartFile file = readFileDto.getFile();
        List<TrainingProgram> trainingProgramList = new ArrayList<>();
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        } else {
            String fileName = file.getOriginalFilename();
            if (!fileName.split("\\.")[1].equals("csv")) {
                return ResponseEntity.badRequest().body("File is not csv");
            } else {
                trainingProgramService.addFromFileCsv(file, readFileDto);
            }

        }
        return ResponseEntity.ok().body("uploadFile Success");

    }
    @GetMapping("/downloadFile/csv")
    public ResponseEntity<byte[]> downLoadFileExample(){
        try {
            Resource resource = resourceLoader.getResource("classpath:CSVFile/trainingProgram.csv");
            byte[] csvFileContent = fileService.getCsvFile(resource.getFile());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDispositionFormData("attachment", "file.csv");
            return new ResponseEntity<>(csvFileContent,headers,HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/saveTrainingProgram")
    public ResponseEntity<?> saveTrain(@Valid @RequestBody TrainingProgramAddDto trainingProgramDTO) {
        trainingProgramService.save(trainingProgramDTO, null, null);
        return ResponseEntity.ok("Add training program successfully");
    }


}
