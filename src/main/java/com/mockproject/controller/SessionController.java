package com.mockproject.controller;

import com.mockproject.dto.SessionDTO;
import com.mockproject.service.interfaces.ISessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/session")
public class SessionController {

    private final ISessionService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Session"),
            @ApiResponse(responseCode = "200", description = "When found Session",
            content = @Content(schema = @Schema(implementation = SessionDTO.class)))
    })
    @Operation(summary = "Get all Session by given syllabus ID")
    @GetMapping("list-by-syllus/{sid}")
    public ResponseEntity<?> listBySyllabusId(@Parameter(description = "Syllabus's ID") @PathVariable("sid") Long sid) {
        List<SessionDTO> list = service.listBySyllabus(sid);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Session with Syllabus ID = " + sid);
        }
    }
}
