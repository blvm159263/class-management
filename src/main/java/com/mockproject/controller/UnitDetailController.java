package com.mockproject.controller;

import com.mockproject.dto.UnitDTO;
import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;
import com.mockproject.service.UnitDetailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/unit-detail")
public class UnitDetailController {

    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

    @Autowired
    public UnitDetailService unitDetailService;

    @GetMapping("/{id}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<UnitDetail>> getAllUnitDetailByUnitId(@PathVariable("id") @NotBlank long unitId){
        List<UnitDetail> listUnitDetail = unitDetailService.getAllUnitDetailByUnitId(unitId, true);
        return ResponseEntity.ok(listUnitDetail);
    }

    @PostMapping("/create/{id}")
    @Secured({CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> createUnitDetail(@PathVariable("id") @NotBlank long unitId, @Valid @RequestBody List<UnitDetailDTO> listUnitDetail){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(unitDetailService.createUnitDetail(unitId,listUnitDetail, user.getUser()));
    }

    @PutMapping("/edit/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<UnitDetail> editUnitDetail(@PathVariable("id") @NotBlank long id, @Valid @RequestBody UnitDetailDTO unitDetailDTO){
        UnitDetail updateUnitDetail = unitDetailService.editUnitDetail(id, unitDetailDTO, true);
        return ResponseEntity.ok(updateUnitDetail);
    }

    @PutMapping("/delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnitDetail(@PathVariable("id") @NotBlank long unitDetailId){
        return ResponseEntity.ok(unitDetailService.deleteUnitDetail(unitDetailId, true));
    }

    @PutMapping("/multi-delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnitDetails(@PathVariable("id") @NotBlank long unitId){
        return ResponseEntity.ok(unitDetailService.deleteUnitDetails(unitId, true));
    }

    @PutMapping("/toggle/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> toggleUnitDetailType(@PathVariable("id") @NotBlank long unitDetailId){
        return ResponseEntity.ok(unitDetailService.toggleUnitDetailType(unitDetailId, true));
    }
}
