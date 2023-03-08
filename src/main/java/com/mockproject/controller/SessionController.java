package com.mockproject.controller;

import com.mockproject.dto.SessionDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;
import com.mockproject.service.SessionService;
import org.apache.el.parser.BooleanNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/session")
public class SessionController {
    @Autowired
    public SessionService sessionService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Session>> getAll(@PathVariable ("id") long syllabusId){
        return ResponseEntity.ok(sessionService.getAllSessionBySyllabusId(syllabusId, true));
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Boolean> createSessions(@PathVariable("id") long syllabusId, @RequestBody List<SessionDTO> listSession){
        return ResponseEntity.ok(sessionService.createSession(syllabusId, listSession));
    }

    @PutMapping("/edit/{syllabusId}/{id}")
    public ResponseEntity<Session> editSession(@PathVariable("id") long id, @PathVariable("syllabusId") long syllabusId,@RequestBody SessionDTO sessionDTO){
        Session updateSession = sessionService.editSession(id, syllabusId, sessionDTO);
        return ResponseEntity.ok(updateSession);
    }
}
