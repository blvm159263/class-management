package com.mockproject.controller;

import com.mockproject.entity.Syllabus;
import com.mockproject.service.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/abi")
public class SyllabusController {
    @Autowired
    private SyllabusService syllabusService;
//    @GetMapping("/content-program")
//    public List<Syllabus> getSyllabus(@RequestParam("content") String name){
//        System.out.println(syllabusService.getSyllabusName(name));
//        return syllabusService.getSyllabusName(name);
//    }
}
