package com.mockproject.controller;

import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.TrainingProgramSyllabusMapper;
import com.mockproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainingprogramdetail")
public class TrainingProgramDetailController {
    private final TrainingProgramService trainingProgramService;
    private final TrainingProgramSyllabusService trainingProgramSyllabusService;
    private final SyllabusService syllabusService;
    private final SessionService sessionService;
    private final UnitService unitService;
    private final UnitDetailService unitDetailService;
    private final TrainingMaterialService trainingMaterialService;

    @GetMapping("/")
    public TrainingProgram getTrainingProgramById(@RequestParam long id) {
        return trainingProgramService.getTrainingProgramById(id);
    }

    public List<TrainingProgramSyllabusDTO> getTrainingProgramSyllabusListById(@PathVariable("id") long id) {
        return trainingProgramSyllabusService.getAllSyllabusByTrainingProgramId(id, true);
    }

    @GetMapping("/syllabus/{id}")
    public List<Syllabus> getSyllabusById(@PathVariable("id") long idTrainingProgram) {
        List<TrainingProgramSyllabus> listTrainingProgramSyllabus = new ArrayList<>();
        List<TrainingProgramSyllabusDTO> trainingProgramSyllabusDTOList = getTrainingProgramSyllabusListById(idTrainingProgram);
        for (TrainingProgramSyllabusDTO t: trainingProgramSyllabusDTOList){
            listTrainingProgramSyllabus.add(TrainingProgramSyllabusMapper.INSTANCE.toEntity(t));
        }
        List<Syllabus> list = new ArrayList<>();
        for (int i = 0; i < listTrainingProgramSyllabus.size(); i++) {
            Syllabus s = syllabusService.getSyllabusById(listTrainingProgramSyllabus.get(i).getSyllabus().getId());
            list.add(s);
        }
        return list;
    }

    @GetMapping("/syllabus/1/{idSyllabus}")
    public List<Session> getSessionListBySyllabusId(@PathVariable("idSyllabus") long idSyllabus) {
        return sessionService.getSessionListBySyllabusId(idSyllabus);
    }

    @GetMapping("/syllabus/2/{idSyllabus}")
    public List<Unit> getUnitListByIdSession(@PathVariable("idSyllabus") long idSyllabus) {
        List<Session> listSession = getSessionListBySyllabusId(idSyllabus);
        List<Unit> listUnit = new ArrayList<>();
        for (Session s : listSession) {
            listUnit.addAll(unitService.getUnitBySessionId(s.getId()));
        }
        return listUnit;
    }

    @GetMapping("/syllabus/3/{idSyllabus}")
    public List<UnitDetail> getUnitDetailListByUnitId(@PathVariable("idSyllabus") long idSyllabus) {
        List<Unit> listUnit = getUnitListByIdSession(idSyllabus);
        List<UnitDetail> listUnitDetail = new ArrayList<>();
        for (Unit u : listUnit) {
            listUnitDetail.addAll(unitDetailService.getUnitDetailByUnitId(u.getId()));
        }
        return listUnitDetail;
    }

    @GetMapping("/syllabus/4/{idSyllabus}")
    public List<TrainingMaterial> getListTrainingMaterialByUnitDetailId(@PathVariable("idSyllabus") long idSyllabus) {
        List<UnitDetail> listUnitDetail = getUnitDetailListByUnitId(idSyllabus);
        List<TrainingMaterial> listTrainingMaterial = new ArrayList<>();
        for (UnitDetail ud : listUnitDetail) {
            listTrainingMaterial.addAll(trainingMaterialService.getListTrainingMaterialByUnitDetailId(ud.getId()));
        }
        return listTrainingMaterial;
    }
}
