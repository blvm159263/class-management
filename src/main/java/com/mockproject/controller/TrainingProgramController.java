package com.mockproject.controller;

import com.mockproject.entity.TrainingProgram;
import com.mockproject.service.interfaces.ITrainingProgramService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainingprogram")
@SecurityRequirement(name = "Authorization")
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
}