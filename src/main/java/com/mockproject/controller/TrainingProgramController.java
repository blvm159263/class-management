package com.mockproject.controller;

import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.TrainingProgramSyllabus;
import com.mockproject.service.SyllabusService;
import com.mockproject.service.TrainingProgramService;
import com.mockproject.service.TrainingProgramSyllabusService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TrainingProgramController {
    @Autowired
    private final TrainingProgramService trainingService;
    @Autowired
    private TrainingProgramSyllabusService syllabusService;
    @Autowired
    private SyllabusService service;

    @GetMapping("/training-program")
    public List<TrainingProgram> getAllTrain() {
        return trainingService.getAll();
    }
    @PostMapping("/training-program/addTrainingProgram")
    public String addTrain(@RequestParam("name") String trainingProgram, HttpSession session) {
            session.setAttribute("trainingName", trainingProgram);
            return "redirect:/saveTrainingProgram";
    }

    @PostMapping("/training-program/saveTrainingProgram")
    public String saveTrain(@RequestParam("content") Long sylId, HttpSession session) {
        String name =(String) session.getAttribute("trainingName");
        Syllabus syllabus = new Syllabus();
        System.out.println(sylId);
        syllabus = service.getSyllabusById(sylId);
        TrainingProgram trainingProgram = new TrainingProgram();
        trainingProgram.setName(name);
        trainingProgram.setDateCreated(LocalDate.now());
        trainingProgram.setLastDateModified(LocalDate.now());
        TrainingProgramSyllabus programSyllabus = new TrainingProgramSyllabus();
        programSyllabus.setTrainingProgram(trainingProgram);
        programSyllabus.setStatus(true);
        programSyllabus.setSyllabus(syllabus);
        System.out.println(trainingProgram);
        trainingService.save(trainingProgram);
        syllabusService.addSyllabus(programSyllabus);
        return "redirect:/addTrainingProgram";
    }
}

