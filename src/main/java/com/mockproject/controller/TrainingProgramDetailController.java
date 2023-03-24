package com.mockproject.controller;

import com.mockproject.dto.*;
import com.mockproject.service.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Training Program Detail API")
@RequestMapping("/api/trainingprogramdetail")
@SecurityRequirement(name = "Authorization")
@Slf4j
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
    @Operation(summary = "Get training program by ID")
    public ResponseEntity getTrainingProgramById(@RequestParam Long id) {
        return ResponseEntity.ok(trainingProgramService.getTrainingProgramById(id));
    }

    @GetMapping("/{id}")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    @Operation(summary = "Get training program syllabus by ID")
    public ResponseEntity getTrainingProgramSyllabusListById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(trainingProgramSyllabusService.getTrainingProgramSyllabusListById(id));
    }

    @GetMapping("/syllabus/{trainingProgramID}")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    @Operation(summary = "Get syllabus list by training program ID")
    public ResponseEntity getSyllabusByTrainingProgramId(@PathVariable("trainingProgramID") Long trainingProgramID) {
        List<TrainingProgramSyllabusDTO> listTrainingProgramSyllabus = trainingProgramSyllabusService.getTrainingProgramSyllabusListById(trainingProgramID);
        List<SyllabusDTO> list = new ArrayList<>();
        for (int i = 0; i < listTrainingProgramSyllabus.size(); i++) {
            SyllabusDTO s = syllabusService.getSyllabusById(listTrainingProgramSyllabus.get(i).getSyllabusId());
            list.add(s);
        }
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find anything in list Syllabus");
        }
    }

    @GetMapping("/syllabus/session/{syllabusID}")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    @Operation(summary = "Get session list by syllabus ID")
    public ResponseEntity getSessionListBySyllabusId(@PathVariable("syllabusID") Long syllabusID) {
        return ResponseEntity.ok(sessionService.getSessionListBySyllabusId(syllabusID));
    }

    @GetMapping("/syllabus/session/unit/{sessionID}")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    @Operation(summary = "Get unit list by sessionID")
    public ResponseEntity getUnitListBySessionID(@PathVariable("sessionID") Long sessionID) {
//        List<SessionDTO> listSession = sessionService.getSessionListBySyllabusId(sessionID);
//        List<UnitDTO> listUnit = unitService.getListUnit(listSession);
        List<UnitDTO> listUnit = unitService.getUnitBySessionId(sessionID);
        if (!listUnit.isEmpty()) {
            return ResponseEntity.ok(listUnit);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find anything in list Unit");
        }
    }

    @GetMapping("/syllabus/session/unit/unit-detail/{unitID}")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    @Operation(summary = "Get unit-detail list by unitID")
    public ResponseEntity getUnitDetailListByUnitId(@PathVariable("unitID") Long unitID) {
//        List<SessionDTO> listSession = sessionService.getSessionListBySyllabusId(unitID);
//        List<UnitDTO> listUnit = unitService.getListUnit(listSession);
//        List<UnitDetailDTO> listUnitDetail = unitDetailService.getListUnitDetail(listUnit);
        List<UnitDetailDTO> listUnitDetail = unitDetailService.getUnitDetailByUnitId(unitID);
        if (!listUnitDetail.isEmpty()) {
            return ResponseEntity.ok(listUnitDetail);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find anything in list UnitDetail");
        }
    }

    @GetMapping("/syllabus/session/unit/unit-detail/training-material/{unitDetailID}")
    @Secured({VIEW, FULL_ACCESS, MODIFY, CREATE})
    @Operation(summary = "Get training-material list by unitDetailID")
    public ResponseEntity getListOfTrainingMaterialByUnitDetailId(@PathVariable("unitDetailID") Long unitDetailID) {
//        List<SessionDTO> listSession = sessionService.getSessionListBySyllabusId(unitDetailID);
//        List<UnitDTO> listUnit = unitService.getListUnit(listSession);
//        List<UnitDetailDTO> listUnitDetail = unitDetailService.getListUnitDetail(listUnit);
//        List<TrainingMaterialDTO> listTrainingMaterial = trainingMaterialService.getListTrainingMaterial(listUnitDetail);
        List<TrainingMaterialDTO> listTrainingMaterial = trainingMaterialService.getListTrainingMaterialByUnitDetailId(unitDetailID);
        if (!listTrainingMaterial.isEmpty()) {
            return ResponseEntity.ok(listTrainingMaterial);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find anything in list Training Material");
        }
    }
}
