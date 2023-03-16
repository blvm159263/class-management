package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgramSyllabus;
import com.mockproject.service.interfaces.ISyllabusService;
import com.mockproject.service.interfaces.ITrainingProgramSyllabusService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/syllabus")
@SecurityRequirement(name = "Authorization")
public class SyllabusController {
    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

    private final ISyllabusService syllabusService;

    @Autowired
    public ITrainingProgramSyllabusService trainingProgramSyllabusService;

    @GetMapping()
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<SyllabusDTO>> getAll(){
        List<SyllabusDTO> listSyllabus = syllabusService.getAll(true, true);
        return ResponseEntity.ok(listSyllabus);
    }

    @GetMapping("/getSyllabusByTrainingProgram/{trainingProgramId}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<TrainingProgramSyllabusDTO>> getAllTrainingProgramSyllabus(@PathVariable("trainingProgramId") long id){
        List<TrainingProgramSyllabusDTO> list = trainingProgramSyllabusService.getAllSyllabusByTrainingProgramId(id, true);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{syllabusId}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<SyllabusDTO> getSyllabus(@PathVariable("syllabusId") long syllabusId){
        SyllabusDTO syllabus = syllabusService.getSyllabusById(syllabusId, true, true);
        return ResponseEntity.ok(syllabus);
    }

    @GetMapping("get-all")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<SyllabusDTO>> getAllSyllabus(){
        return ResponseEntity.ok(syllabusService.getSyllabusList(true));
    }

    @PostMapping(value = "/create")
    @Secured({CREATE,FULL_ACCESS})
    public ResponseEntity<Long> create(@RequestBody SyllabusDTO syllabus){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long syllabusID = syllabusService.create(syllabus, user.getUser());
        return ResponseEntity.ok(syllabusID);
    }

    @PutMapping("edit")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity<Syllabus> editSyllabus(@RequestBody SyllabusDTO syllabusDTO)throws IOException {
        Syllabus editsyllabus = syllabusService.editSyllabus(syllabusDTO, true);
        return ResponseEntity.ok(editsyllabus);
    }


    @PutMapping("delete/{id}")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteSyllabus(@PathVariable("id") long syllabusId){
        return ResponseEntity.ok(syllabusService.deleteSyllabus(syllabusId, true));
    }
}
