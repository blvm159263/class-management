package com.mockproject.controller;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Unit;
import com.mockproject.service.interfaces.IUnitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.mockproject.service.interfaces.IUnitService;
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
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/unit")
@SecurityRequirement(name = "Authorization")
public class UnitController {
    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";


    private final IUnitService unitService;

    @GetMapping("/{sessionId}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<UnitDTO>> getAllUnitBySessionId(@PathVariable("sessionId") long sessionId){
        List<UnitDTO> listUnit = unitService.getAllUnitBySessionId(sessionId, true);
        return ResponseEntity.ok(listUnit);
    }

    @PostMapping("/create/{id}")
    @Secured({CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> createUnit(@PathVariable("id") long sessionId,@RequestBody List<UnitDTO> listUnit){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(unitService.createUnit(sessionId, listUnit, user.getUser()));
    }

    @PutMapping("/edit")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Unit> editUnit(@RequestBody UnitDTO unitDTO)throws IOException {
        Unit updateUnit = unitService.editUnit(unitDTO, true);
        return ResponseEntity.ok(updateUnit);
    }

    @PutMapping("delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnit(@PathVariable("id") long unitId){
        return ResponseEntity.ok(unitService.deleteUnit(unitId, true));
    }

    @PutMapping("multi-delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnits(@PathVariable("id") long sessionId) {
        return ResponseEntity.ok(unitService.deleteUnits(sessionId, true));
    }

    private final IUnitService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Unit"),
            @ApiResponse(responseCode = "200", description = "When found Unit",
            content = @Content(schema = @Schema(implementation = UnitDTO.class)))
    })
    @Operation(summary = "Get All Unit by given Session ID")
    @GetMapping("list-by-session/{sid}")
    public ResponseEntity<?> listBySessionId(@Parameter(description = "Session ID") @PathVariable("sid") Long sid) {
        List<UnitDTO> list = service.listBySessionId(sid);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Unit!");
        }
    }
}
