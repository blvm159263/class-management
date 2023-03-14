package com.mockproject.controller;

import com.mockproject.entity.*;
import com.mockproject.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController

public class TrainingProgramDetailController {
    private final TrainingProgramService trainingProgramService;
    private final TrainingProgramSyllabusService trainingProgramSyllabusService;
    private final SyllabusService syllabusService;
    private final SessionService sessionService;
    private final UnitService unitService;
    private final UnitDetailService unitDetailService;
    private final TrainingMaterialService trainingMaterialService;
    public TrainingProgramDetailController(TrainingProgramService trainingProgramService, TrainingProgramSyllabusService trainingProgramSyllabusService, SyllabusService syllabusService, SessionService sessionService, UnitService unitService, UnitDetailService unitDetailService, TrainingMaterialService trainingMaterialService) {
        this.trainingProgramService = trainingProgramService;
        this.trainingProgramSyllabusService = trainingProgramSyllabusService;
        this.syllabusService = syllabusService;
        this.sessionService = sessionService;
        this.unitService = unitService;
        this.unitDetailService = unitDetailService;
        this.trainingMaterialService = trainingMaterialService;
    }
    @GetMapping("/trainingprogramdetail")
    public TrainingProgram getTrainingProgramById(@RequestParam long id){
        return trainingProgramService.getTrainingProgramById(id);
    }
    @GetMapping("/trainingprogramdetail/{id}")
    public List<TrainingProgramSyllabus> getTrainingProgramSyllabusListById(@PathVariable("id") long id){
        return trainingProgramSyllabusService.getTrainingProgramSyllabusListById(id);
    }

    @GetMapping("/trainingprogramdetail/syllabus/{id}")
    public List<Syllabus> getSyllabusById(@PathVariable("id") long idTrainingProgram){
        List<TrainingProgramSyllabus> listTrainingProgramSyllabus = getTrainingProgramSyllabusListById(idTrainingProgram);
        List<Syllabus> list = new ArrayList<>() ;
        for(int i = 0; i < listTrainingProgramSyllabus.size(); i++){
            Syllabus s = syllabusService.getSyllabusById(listTrainingProgramSyllabus.get(i).getSyllabus().getId());
            list.add(s);
        }
        return list;
    }

    @GetMapping("/trainingprogramdetail/syllabus/1/{idSyllabus}")
    public List<Session> getSessionListBySyllabusId(@PathVariable("idSyllabus") long idSyllabus){
        return sessionService.getSessionListBySyllabusId(idSyllabus);
    }

    @GetMapping("/trainingprogramdetail/syllabus/2/{idSyllabus}")
    public List<Unit> getUnitListByIdSession(@PathVariable("idSyllabus") long idSyllabus){
        List<Session> listSession = getSessionListBySyllabusId(idSyllabus);
        List<Unit> listUnit = new ArrayList<>() ;
        for(Session s : listSession){
            listUnit.addAll(unitService.getUnitBySessionId(s.getId()));
        }
        return listUnit;
    }

    @GetMapping("/trainingprogramdetail/syllabus/3/{idSyllabus}")
    public List<UnitDetail> getUnitDetailListByUnitId(@PathVariable("idSyllabus") long idSyllabus){
        List<Unit> listUnit = getUnitListByIdSession(idSyllabus);
        List<UnitDetail> listUnitDetail = new ArrayList<>();
        for(Unit u : listUnit){
            listUnitDetail.addAll(unitDetailService.getUnitDetailByUnitId(u.getId()));
        }
        return listUnitDetail;
    }
    @GetMapping("/trainingprogramdetail/syllabus/4/{idSyllabus}")
    public List<TrainingMaterial> getListTrainingMaterialByUnitDetailId(@PathVariable("idSyllabus") long idSyllabus){
        List<UnitDetail> listUnitDetail = getUnitDetailListByUnitId(idSyllabus);
        List<TrainingMaterial> listTrainingMaterial = new ArrayList<>();
        for(UnitDetail ud: listUnitDetail){
            listTrainingMaterial.addAll(trainingMaterialService.getListTrainingMaterialByUnitDetailId(ud.getId()));
        }
        return listTrainingMaterial;
    }
}
