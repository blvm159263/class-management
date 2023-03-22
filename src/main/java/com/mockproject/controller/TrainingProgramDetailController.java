package com.mockproject.controller;

import com.mockproject.dto.*;
import com.mockproject.entity.*;
import com.mockproject.service.*;
import com.mockproject.service.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/training-program-detail")
public class TrainingProgramDetailController {
    private final ITrainingProgramService trainingProgramService;
    private final ITrainingProgramSyllabusService trainingProgramSyllabusService;
    private final ISyllabusService syllabusService;
    private final ISessionService sessionService;
    private final IUnitService unitService;
    private final IUnitDetailService unitDetailService;
    private final ITrainingMaterialService trainingMaterialService;

    @GetMapping("/")
    @Operation(summary = "Get training program by ID")
    public ResponseEntity getTrainingProgramById(@RequestParam Long id) {
        return ResponseEntity.ok(trainingProgramService.getTrainingProgramById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity getTrainingProgramSyllabusListById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(trainingProgramSyllabusService.getTrainingProgramSyllabusListById(id));
    }

    @GetMapping("/syllabus/{id}")
    public ResponseEntity getSyllabusById(@PathVariable("id") Long idTrainingProgram) {
        List<TrainingProgramSyllabusDTO> listTrainingProgramSyllabus = trainingProgramSyllabusService.getTrainingProgramSyllabusListById(idTrainingProgram);
        List<SyllabusDTO> list = new ArrayList<>();
        for (int i = 0; i < listTrainingProgramSyllabus.size(); i++) {
            SyllabusDTO s = syllabusService.getSyllabusById(listTrainingProgramSyllabus.get(i).getSyllabusId());
            list.add(s);
        }
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any thing in list Syllabus");
        }
    }

    @GetMapping("/syllabus/session/{idSyllabus}")
    @Operation(summary = "Get session list by syllabus ID")
    public ResponseEntity getSessionListBySyllabusId(@PathVariable("idSyllabus") Long idSyllabus) {
        return ResponseEntity.ok(sessionService.getSessionListBySyllabusId(idSyllabus));
    }

    @GetMapping("/syllabus/unit/{idSession}")
    public ResponseEntity getUnitListByIdSession(@PathVariable("idSession") Long idSyllabus) {
        List<SessionDTO> listSession = sessionService.getSessionListBySyllabusId(idSyllabus);
        List<UnitDTO> listUnit = unitService.getListUnit(listSession);
        if (!listUnit.isEmpty()) {
            return ResponseEntity.ok(listUnit);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any thing in list Unit");
        }
    }

    @GetMapping("/syllabus/unit-detail/{idSyllabus}")
    public ResponseEntity getUnitDetailListByUnitId(@PathVariable("idSyllabus") Long idSyllabus) {
        List<SessionDTO> listSession = sessionService.getSessionListBySyllabusId(idSyllabus);
        List<UnitDTO> listUnit = unitService.getListUnit(listSession);
        List<UnitDetailDTO> listUnitDetail = unitDetailService.getListUnitDetail(listUnit);
        if (!listUnitDetail.isEmpty()) {
            return ResponseEntity.ok(listUnitDetail);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any thing in list UnitDetail");
        }
    }

    @GetMapping("/syllabus/4/{idSyllabus}")
    public ResponseEntity getListTrainingMaterialByUnitDetailId(@PathVariable("idSyllabus") Long idSyllabus) {
        List<SessionDTO> listSession = sessionService.getSessionListBySyllabusId(idSyllabus);
        List<UnitDTO> listUnit = unitService.getListUnit(listSession);
        List<UnitDetailDTO> listUnitDetail = unitDetailService.getListUnitDetail(listUnit);
        List<TrainingMaterialDTO> listTrainingMaterial = trainingMaterialService.getListTrainingMaterial(listUnitDetail);
        if (!listTrainingMaterial.isEmpty()) {
            return ResponseEntity.ok(listTrainingMaterial);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any thing in list Training Material");
        }
    }
}
