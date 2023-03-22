package com.mockproject.controller;

import com.mockproject.dto.ReadFileDto;
import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.exception.FileException;
import com.mockproject.service.interfaces.IFileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import com.mockproject.service.interfaces.ITrainingProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/training-program")
public class TrainingProgramController {

    private final ITrainingProgramService service;
    private final IFileService fileService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Program"),
            @ApiResponse(responseCode = "200", description = "When find training program and return list program",
                    content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
    })
    @Operation(summary = "Get all Training Program")
    @GetMapping("list")
    public ResponseEntity<?> getAllTrainingProgram(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<TrainingProgramDTO> list = service.getAll(pageNo, pageSize);
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
    @Operation(summary = "Get Training Program by program name and creator name")
    @GetMapping("search-name")
    public ResponseEntity<?> searchByName(@Parameter(description = "Training Program Name want to search")
                                          @RequestParam(name = "name", required = false) String name,
                                          @RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          HttpServletRequest request) throws IOException {


        HttpSession session = request.getSession();

        //==================== get list search keyword from session
        List<String> listName = (List<String>) session.getAttribute("LIST_NAME");
        //========================================================================
        //
        //==================== no keyword and empty list, return all training program
        if (listName == null && name == null) {
            Page<TrainingProgramDTO> list = service.getAll(pageNo, pageSize);
            return ResponseEntity.ok(list);
        }
        if (listName == null) {
            listName = new ArrayList<>();
        }
        //===================== check duplicate search keyword in the list
        boolean check = false;
        for (String s : listName) {
            if (s.equals(name))
                check = true;
        }
        if (check == false)
            listName.add(name);
        //==================================================================
        //================== save list keyword to session
        session.setAttribute("LIST_NAME", listName);
        //==================================================================

        List<TrainingProgramDTO> list = new ArrayList<>();
        //================================= load search result from database
        for (String key : listName) {
            for (TrainingProgramDTO dto : service.findByNameContaining(pageNo, pageSize, key, key)) {
                list.add(dto);
            }
        }
        //==================================================================
        Page<TrainingProgramDTO> result = new PageImpl<>(list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Training Program!");
        }
    }

    @GetMapping("delete-searchkey")
    public ResponseEntity<?> deleteSearchKey(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestParam(name = "name", required = false) String name) {
        HttpSession session = request.getSession();
        List<String> listName = (List<String>) session.getAttribute("LIST_NAME");
        if (listName == null && name == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any keywords to delete!");
        }
        if (listName == null) {
            listName = new ArrayList<>();
        }
        listName.remove(name);
        if (listName.isEmpty()) {
            session.removeAttribute("LIST_NAME");
        }
        session.setAttribute("LIST_NAME", listName);
        return ResponseEntity.ok().body("Delete successfully");
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
                    //CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    Long creatorId,lastModifierId;
                    int programId;
                    LocalDate dateCreated, lastDateModified;
                    String name;
                    BigDecimal hour;
                    int day;
                    boolean status;
                    List<Long> listTrainingClassesId,listTrainingProgramSyllabusesId;
                   // BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

//                    CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).build());

                    CSVParser parser= fileService.readFile(file,readFileDto.getEncodingType());
                    // skip header of csv file
                    parser.iterator().next();
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    for (CSVRecord record : parser.getRecords()){
                        try{
                            dateCreated= LocalDate.parse(record.get(2),dateFormat);
                            lastDateModified = LocalDate.parse(record.get(3),dateFormat);
                        }catch (DateTimeException e){
                            throw new FileException("Date time is wrong( format: yyyy-MM-dd)", HttpStatus.BAD_REQUEST.value());
                        }
                        try{
                            programId = Integer.parseInt(record.get(0));
                            name = record.get(1);
                            hour = new BigDecimal(record.get(4));
                            day = Integer.parseInt(record.get(5));
                            status = Boolean.parseBoolean(record.get(6));
                            creatorId = Long.parseLong(record.get(7));
                            lastModifierId = Long.parseLong(record.get(8));
                            listTrainingClassesId = Arrays.stream(record.get(9).split("/"))
                                    .map(classId-> Long.parseLong(classId))
                                    .collect(Collectors.toList());
                            listTrainingProgramSyllabusesId = Arrays.stream(record.get(10).split("/"))
                                    .map(classId-> Long.parseLong(classId))
                                    .collect(Collectors.toList());
                        }catch (Exception e){
                            throw new FileException (e.getMessage(),HttpStatus.BAD_REQUEST.value());
                        }
                        var trainingProgram = new TrainingProgram(null,programId,name,dateCreated,lastDateModified,hour,day,status,null,null,null,null);
//                        System.out.println(trainingProgram.toString());
//                        System.out.println(creatorId+" "+lastModifierId+" " +listTrainingClassesId+" "+listTrainingProgramSyllabusesId);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok().body("Okeee");
    }
}
