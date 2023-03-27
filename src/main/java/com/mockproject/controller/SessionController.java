package com.mockproject.controller;

import com.mockproject.dto.SessionDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Session;
import com.mockproject.service.interfaces.ISessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Session API")
@RequestMapping(value = "/api/session")
@SecurityRequirement(name = "Authorization")
@Slf4j
public class SessionController {

    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

    private final ISessionService sessionService;

    @GetMapping("/{syllabusId}")
    @Operation(summary = "get all session by syllabus id")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<SessionDTO>> getAll(@PathVariable ("syllabusId") Long syllabusId){
        return ResponseEntity.ok(sessionService.getAllSessionBySyllabusId(syllabusId, true));
    }

    @PostMapping("/create/{id}")
    @Operation(summary = "Create sessions by syllabus id")
    @Secured({CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> createSessions(@PathVariable("id") @Parameter(description = "Syllabus id") Long syllabusId, @RequestBody List<SessionDTO> listSession){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(sessionService.createSession(syllabusId, listSession, user.getUser()));
    }

    @PutMapping("/edit")
    @Operation(summary = "Edit session by sessionDTO")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Session> editSession(@RequestBody SessionDTO sessionDTO)throws IOException {
        Session updateSession = sessionService.editSession(sessionDTO, true);
        return ResponseEntity.ok(updateSession);
    }

    @PutMapping("/delete/{id}")
    @Operation(summary = "Delete session by session id")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteSession(@PathVariable("id") @Parameter(description = "Session id") Long sessionId){
        return ResponseEntity.ok(sessionService.deleteSession(sessionId, true));
    }

    @PutMapping("/multi-delete/{id}")
    @Operation(summary = "Delete session by session id")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteSessions(@PathVariable("id")@Parameter(description = "Session id") Long syllabusId){
        return ResponseEntity.ok(sessionService.deleteSessions(syllabusId, true));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Session"),
            @ApiResponse(responseCode = "200", description = "When found Session",
            content = @Content(schema = @Schema(implementation = SessionDTO.class)))
    })
    @Operation(summary = "Get all Session by given syllabus ID")
    @GetMapping("list-by-syllus/{sid}")
    public ResponseEntity<?> listBySyllabusId(@Parameter(description = "Syllabus's ID") @PathVariable("sid") Long sid) {
        List<SessionDTO> list = sessionService.listBySyllabus(sid);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Session with Syllabus ID = " + sid);
        }
    }
}
