package com.mockproject.controller;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Session;
import com.mockproject.entity.Unit;
import com.mockproject.service.UnitService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/unit")
public class UnitController {

    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

    @Autowired
    public UnitService unitService;

    @GetMapping("/{id}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<Unit>> getAllUnitBySessionId(@PathVariable("id") @NotBlank long sessionId){
        List<Unit> listUnit = unitService.getAllUnitBySessionId(sessionId, true);
        return ResponseEntity.ok(listUnit);
    }

    @PostMapping("/create/{id}")
    @Secured({CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> createUnit(@PathVariable("id") @NotBlank long sessionId,@Valid @RequestBody List<UnitDTO> listUnit){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(unitService.createUnit(sessionId, listUnit, user.getUser()));
    }

    @PutMapping("/edit/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Unit> editUnit(@PathVariable("id") @NotBlank long id, @Valid @RequestBody UnitDTO unitDTO){
        Unit updateUnit = unitService.editUnit(id, unitDTO, true);
        return ResponseEntity.ok(updateUnit);
    }

    @PutMapping("delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnit(@PathVariable("id") @NotBlank long unitId){
        return ResponseEntity.ok(unitService.deleteUnit(unitId, true));
    }

    @PutMapping("multi-delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnits(@PathVariable("id") @NotBlank long sessionId){
        return ResponseEntity.ok(unitService.deleteUnits(sessionId, true));
    }
}
