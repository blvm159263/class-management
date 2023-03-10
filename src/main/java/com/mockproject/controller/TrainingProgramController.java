package com.mockproject.controller;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.service.TrainingProgramService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/view")
public class TrainingProgramController {
    private final TrainingProgramService trainingProgramService;

    @GetMapping("/trainingprogram")
    public List<TrainingProgram> getAllTrainingProgram(@RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                                          Model model)
    {
        long rows=trainingProgramService.countAll();
        long totalPage=rows/pageSize;
        if(totalPage==0||rows%pageSize!=0){
            totalPage+=1;
        }
        model.addAttribute("NUMBER_OF_PAGE",totalPage);
        return trainingProgramService.getAll(pageNo, pageSize);
    }
    @PostMapping("/searchtrainingprogram")
    public List<TrainingProgram> getByKeyword(@RequestParam(name="keyword",required = false) String keyword,
                                              HttpServletResponse response,
                                              Model model,
                                              HttpServletRequest request) throws IOException {
        HttpSession session=request.getSession();
        List<String> listKeyword=(List<String>) session.getAttribute("LIST_KEYWORD");
        if(listKeyword==null && keyword==null) {
            response.sendRedirect("/trainingprogram");
        }
        if (listKeyword==null){
            listKeyword=new ArrayList<>();
        }
        listKeyword.add(keyword);
        session.setAttribute("LIST_KEYWORD",listKeyword);
        for (String s:listKeyword){
            System.out.println(s);
        }
        List<TrainingProgram> resultList=new ArrayList<>();
        for (String key:listKeyword){
            for (TrainingProgram p:trainingProgramService.getByName(keyword)){
                resultList.add(p);
            }
            for (TrainingProgram p:trainingProgramService.getByCreatorFullname(keyword)){
                resultList.add(p);
            }
        }


        return resultList;

    }
}