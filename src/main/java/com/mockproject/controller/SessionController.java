package com.mockproject.controller;

import com.mockproject.dto.SessionDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;
import com.mockproject.service.SessionService;
import org.apache.el.parser.BooleanNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/session")
public class SessionController {

    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

    @Autowired
    public SessionService sessionService;

    @GetMapping("/{id}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<Session>> getAll(@PathVariable ("id") long syllabusId){
        return ResponseEntity.ok(sessionService.getAllSessionBySyllabusId(syllabusId, true));
    }

    @PostMapping("/create/{id}")
    @Secured({CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> createSessions(@PathVariable("id") long syllabusId, @RequestBody List<SessionDTO> listSession){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(sessionService.createSession(syllabusId, listSession, user.getUser()));
    }

    @PutMapping("/edit/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Session> editSession(@PathVariable("id") long id,@RequestBody SessionDTO sessionDTO){
        Session updateSession = sessionService.editSession(id, sessionDTO, true);
        return ResponseEntity.ok(updateSession);
    }

    @PutMapping("/delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteSession(@PathVariable("id") long sessionId){
        return ResponseEntity.ok(sessionService.deleteSession(sessionId, true));
    }

    @PutMapping("/multi-delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteSessions(@PathVariable("id") long syllabusId){
        return ResponseEntity.ok(sessionService.deleteSessions(syllabusId, true));
    }
}
