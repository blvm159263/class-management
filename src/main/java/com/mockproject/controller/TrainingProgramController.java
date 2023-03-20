package com.mockproject.controller;

import com.mockproject.dto.TrainingProgramDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/training-program")
public class TrainingProgramController {

    private final ITrainingProgramService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Program"),
            @ApiResponse(responseCode = "200", description = "When find training program and return list program",
                    content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
    })
    @Operation(summary = "Get all Training Program")
    @GetMapping("list")
    public ResponseEntity<?> getAllTrainingProgram(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   Model model)
    {
        long rows=service.countAll();
        long totalPage=rows/pageSize;
        if(totalPage==0||rows%pageSize!=0){
            totalPage+=1;
        }
        model.addAttribute("NUMBER_OF_PAGE",totalPage);

        Page<TrainingProgramDTO> list =service.getAll(pageNo, pageSize);
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
                                              @RequestParam(name="name",required = false) String name,
                                              @RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              HttpServletResponse response,
                                              HttpServletRequest request,
                                              Model model) throws IOException {
        long rows=service.countAll();
        long totalPage=rows/pageSize;
        if(totalPage==0||rows%pageSize!=0){
            totalPage+=1;
        }
        model.addAttribute("NUMBER_OF_PAGE",totalPage);

        HttpSession session=request.getSession();
        List<String> listName=(List<String>) session.getAttribute("LIST_NAME");
        if(listName==null && name==null) {
            response.sendRedirect("list");
        }
        if (listName==null){
            listName=new ArrayList<>();
        }
        listName.add(name);
        session.setAttribute("LIST_NAME",listName);
        List<TrainingProgramDTO> list = new ArrayList<>();
        for(String key:listName){
            for (TrainingProgramDTO dto:service.findByNameContaining(pageNo, pageSize, key, key)){
                list.add(dto);
            }
        }
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Training Program!");
        }
    }
    @GetMapping("delete-searchkey")
    public ResponseEntity<?> deleteSearchKey(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.removeAttribute("LIST_NAME");
        return ResponseEntity.ok().body("detele successfully");
    }
    @GetMapping("view-searchkey")
    public ResponseEntity<?> getSearchKey(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<String> listName=(List<String>) session.getAttribute("LIST_NAME");
        if (listName==null){
            listName=new ArrayList<>();
        }
        return ResponseEntity.ok(listName);
    }
}
