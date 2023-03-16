package com.mockproject.controller;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Unit;
import com.mockproject.service.interfaces.IUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Unit", description = "API realted Unit")
@RequestMapping(value = "/api/unit")
@SecurityRequirement(name = "Authorization")
public class UnitController {
    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";


    private final IUnitService unitService;

    @GetMapping("/{sessionId}")
    @Operation(summary = "Get all unit by session id")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<UnitDTO>> getAllUnitBySessionId(@PathVariable("sessionId") long sessionId){
        List<UnitDTO> listUnit = unitService.getAllUnitBySessionId(sessionId, true);
        return ResponseEntity.ok(listUnit);
    }

    @PostMapping("/create/{id}")
    @Operation(summary = "Create unit by session id")
    @Secured({CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> createUnit(@PathVariable("id") @Parameter(description = "Session id") long sessionId, @RequestBody List<UnitDTO> listUnit){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(unitService.createUnit(sessionId, listUnit, user.getUser()));
    }

    @PutMapping("/edit")
    @Operation(summary = "Edit unit by UnitDTO")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity<Unit> editUnit(@RequestBody UnitDTO unitDTO)throws IOException {
        Unit updateUnit = unitService.editUnit(unitDTO, true);
        return ResponseEntity.ok(updateUnit);
    }

    @PutMapping("delete/{id}")
    @Operation(summary = "Delete unit by unit id")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnit(@PathVariable("id") @Parameter(description = "Unit id") long unitId){
        return ResponseEntity.ok(unitService.deleteUnit(unitId, true));
    }

    @PutMapping("multi-delete/{id}")
    @Operation(summary = "Delete multi units by sessionId")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnits(@PathVariable("id") @Parameter(description = "Session id") long sessionId){
        return ResponseEntity.ok(unitService.deleteUnits(sessionId, true));
    }
}
