package com.mockproject.controller;

import com.mockproject.dto.ReadFileDto;
import com.mockproject.dto.SearchByDTO;
import com.mockproject.dto.TrainingClassFilterRequestDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.exception.FileException;
import com.mockproject.service.interfaces.IClassScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/class-schedule")
@Slf4j
public class ClassScheduleController {

    private final IClassScheduleService classScheduleService;

    @PostMapping("/day")
    @Operation(summary = "Get training class for the typical day")
    public ResponseEntity getTrainingClassByDay(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {
        var trainingClass = classScheduleService.getTrainingClassByDay(filterRequestDTO);
        if (trainingClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't have any training class");
        } else {
            return ResponseEntity.ok(trainingClass);
        }
    }

    @PostMapping("/week")
    @Operation(summary = "Get a class schedule for a week")
    public ResponseEntity getTrainingClassByWeek(@RequestBody TrainingClassFilterRequestDTO filterRequestDTO) {

        var trainingClass = classScheduleService.getTrainingClassByWeek(filterRequestDTO);
        if (trainingClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't have any training class");
        } else {
            return ResponseEntity.ok(trainingClass);
        }
    }

    @PostMapping("/search/day")
    @Operation(summary = "Search training class in day by text")
    public ResponseEntity searchTrainingClassInDay(@RequestBody SearchByDTO searchByDTO) {
        var trainingClass = classScheduleService.searchTrainingClassInDate(searchByDTO.getSearchText(), searchByDTO.getNowDate());
        if (trainingClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't have any training class");
        } else {
            return ResponseEntity.ok(trainingClass);
        }
    }

    @PostMapping("/search/week")
    @Operation(summary = "Search training class in week by text")
    public ResponseEntity searchTrainingClassInWeek(@RequestBody SearchByDTO searchByDTO) {
        var trainingClass = classScheduleService.searchTrainingClassInWeek(searchByDTO.getSearchText(), searchByDTO.getStartDate(), searchByDTO.getEndDate());
        if (trainingClass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't have any training class");
        } else {
            return ResponseEntity.ok(trainingClass);
        }
    }
    @PostMapping("/uploadCsv")
    public ResponseEntity readFileCsv(@ModelAttribute ReadFileDto readFileDto){
        MultipartFile file = readFileDto.getFile();
        List<TrainingProgram> trainingProgramList= new ArrayList<>();
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("File is empty");
        }else{
            String fileName= file.getOriginalFilename();
            try{
                if(!fileName.split("\\.")[1].equals("csv")){
                    return ResponseEntity.badRequest().body("File is not csv");
                }else{
                    Long id,programId,creatorId,lastModifierId;
                    LocalDate dateCreated, lastDateModified;
                    String name;
                    BigDecimal hour;
                    int day;
                    boolean status;
                    List<Long> listTrainingClassesId,listTrainingProgramSyllabusesId;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

                    CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).build());
                    // skip header of csv file
                    parser.iterator().next();

                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    for (CSVRecord record : parser.getRecords()){
                        try{
                            dateCreated= LocalDate.parse(record.get(2),dateFormat);
                            lastDateModified = LocalDate.parse(record.get(3),dateFormat);
                        }catch (DateTimeException e){
                            throw new FileException("Date time is wrong", HttpStatus.BAD_REQUEST.value());
                        }
                        try{
                            programId = Long.parseLong(record.get(0));
                            name = record.get(1);
                            hour = new BigDecimal(record.get(4));
                            day = Integer.parseInt(record.get(5));
                            status = Boolean.parseBoolean(record.get(6));
                            creatorId = Long.parseLong(record.get(7));
                            lastModifierId = Long.parseLong(record.get(8));
                            listTrainingClassesId = Arrays.stream(record.get(9).split(","))
                                    .map(classId-> Long.parseLong(classId))
                                    .collect(Collectors.toList());
                            listTrainingProgramSyllabusesId = Arrays.stream(record.get(10).split(","))
                                    .map(classId-> Long.parseLong(classId))
                                    .collect(Collectors.toList());
                        }catch (Exception e){
                            throw new FileException ("Content is not valid",HttpStatus.BAD_REQUEST.value());
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok().body("Okeee");
    }
}
