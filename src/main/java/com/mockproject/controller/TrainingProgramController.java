package com.mockproject.controller;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.service.interfaces.ITrainingProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    public List<TrainingProgram> getAllTrainingProgram(@RequestParam(defaultValue = "0") Integer pageNo,
                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                       Model model) {
        Long rows = trainingProgramService.countAll();
        Long totalPage = rows / pageSize;
        if (totalPage == 0 || rows % pageSize != 0) {
            totalPage += 1;
        }
        model.addAttribute("NUMBER_OF_PAGE", totalPage);
        return trainingProgramService.getAll(pageNo, pageSize);
    }

    @PostMapping("/search")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    public List<TrainingProgram> getByKeyword(@RequestParam(name = "keyword", required = false) String keyword,
                                              HttpServletResponse response,
                                              Model model,
                                              HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        List<String> listKeyword = (List<String>) session.getAttribute("LIST_KEYWORD");
        if (listKeyword == null && keyword == null) {
            response.sendRedirect("/trainingprogram");
        }
        if (listKeyword == null) {
            listKeyword = new ArrayList<>();
        }
        listKeyword.add(keyword);
        session.setAttribute("LIST_KEYWORD", listKeyword);
        for (String s : listKeyword) {
            System.out.println(s);
        }
        List<TrainingProgram> resultList = new ArrayList<>();
        for (String key : listKeyword) {
            for (TrainingProgram p : trainingProgramService.getByName(key)) {
                resultList.add(p);
            }
            for (TrainingProgram p : trainingProgramService.getByCreatorFullname(key)) {
                resultList.add(p);
            }
        }
        return resultList;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Program"),
            @ApiResponse(responseCode = "200", description = "When find training program and return list program",
                    content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
    })
    @Operation(summary = "Get Training Program by searching name")
    @GetMapping("search-name")
    public ResponseEntity<?> searchByName(@Parameter(description = "Training Program Name want to search") @RequestParam(defaultValue = "") String name) {
        List<TrainingProgramDTO> list = trainingProgramService.searchByName(name);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Training Program!");
        }
    }
}
