package com.mockproject.controller;

import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.TrainingProgramSyllabusMapper;
import com.mockproject.service.*;
import com.mockproject.service.interfaces.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Training Program Detail API")
@RequestMapping("/api/trainingprogramdetail")
@SecurityRequirement(name = "Authorization")
public class TrainingProgramDetailController {

    public static final String VIEW = "ROLE_View_Training program";
    public static final String MODIFY = "ROLE_Modify_Training program";
    public static final String CREATE = "ROLE_Create_Training program";
    public static final String FULL_ACCESS = "ROLE_Full access_Training program";

    private final ITrainingProgramService trainingProgramService;
    private final ITrainingProgramSyllabusService trainingProgramSyllabusService;
    private final ISyllabusService syllabusService;
    private final ISessionService sessionService;
    private final IUnitService unitService;
    private final IUnitDetailService unitDetailService;
    private final ITrainingMaterialService trainingMaterialService;

    @GetMapping("/")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    public TrainingProgram getTrainingProgramById(@RequestParam long id) {
        return trainingProgramService.getTrainingProgramById(id);
    }

    public List<TrainingProgramSyllabusDTO> getTrainingProgramSyllabusListById(@PathVariable("id") long id) {
        return trainingProgramSyllabusService.getAllSyllabusByTrainingProgramId(id, true);
    }

    @GetMapping("/syllabus/{id}")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
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
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    public List<Session> getSessionListBySyllabusId(@PathVariable("idSyllabus") long idSyllabus) {
        return sessionService.getSessionListBySyllabusId(idSyllabus);
    }

    @GetMapping("/syllabus/2/{idSyllabus}")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
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