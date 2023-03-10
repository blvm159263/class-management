package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;
import com.mockproject.service.SyllabusService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/syllabus")
public class SyllabusController {

    @Autowired
    public SyllabusService syllabusService;

    // list syllabus for user
    @GetMapping()
    public ResponseEntity<List<Syllabus>> getAll(){
        List<Syllabus> listSyllabus = syllabusService.getAll(true, true);
        return ResponseEntity.ok(listSyllabus);
    }

    // list syllabus for admin
    @GetMapping("/{id}")
    public ResponseEntity<Syllabus> getSyllabus(@PathVariable("id") long syllabusId){
        Syllabus syllabus = syllabusService.getSyllabusById(syllabusId, true, true);
        return ResponseEntity.ok(syllabus);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<Syllabus>> getAllSyllabus(){
        return ResponseEntity.ok(syllabusService.getSyllabusList(true));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Long> create(@RequestBody SyllabusDTO syllabus){
        long syllabusID = syllabusService.create(syllabus);
        return ResponseEntity.ok(syllabusID);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Syllabus> editSyllabus(@PathVariable("id") long id, @RequestBody SyllabusDTO syllabusDTO){
        Syllabus editsyllabus = syllabusService.editSyllabus(id, syllabusDTO, true);
        return ResponseEntity.ok(editsyllabus);
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteSyllabus(@PathVariable("id") long syllabusId){
        return ResponseEntity.ok(syllabusService.deleteSyllabus(syllabusId, true));
    }
}
