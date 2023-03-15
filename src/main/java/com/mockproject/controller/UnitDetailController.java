package com.mockproject.controller;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.UnitDetail;
import com.mockproject.service.interfaces.IUnitDetailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.mockproject.service.interfaces.IUnitDetailService;
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
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "/api/unit-detail")
public class UnitDetailController {

    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

    private final IUnitDetailService unitDetailService;

    @GetMapping("/{unitId}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<UnitDetailDTO>> getAllUnitDetailByUnitId(@PathVariable("unitId") long unitId) {
        List<UnitDetailDTO> listUnitDetail = unitDetailService.getAllUnitDetailByUnitId(unitId, true);
        return ResponseEntity.ok(listUnitDetail);
    }

    @PostMapping("/create/{id}")
    @Secured({CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> createUnitDetails(@PathVariable("id") long unitId, @RequestBody List<UnitDetailDTO> listUnitDetail) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(unitDetailService.createUnitDetail(unitId, listUnitDetail, user.getUser()));
    }

    @PutMapping("/edit")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<UnitDetail> editUnitDetail(@RequestBody UnitDetailDTO unitDetailDTO) throws IOException {
        UnitDetail updateUnitDetail = unitDetailService.editUnitDetail(unitDetailDTO, true);
        return ResponseEntity.ok(updateUnitDetail);
    }

    @PutMapping("/delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnitDetail(@PathVariable("id") long unitDetailId) {
        return ResponseEntity.ok(unitDetailService.deleteUnitDetail(unitDetailId, true));
    }

    @PutMapping("/multi-delete/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteUnitDetails(@PathVariable("id") long unitId) {
        return ResponseEntity.ok(unitDetailService.deleteUnitDetails(unitId, true));
    }

    @PutMapping("/toggle/{id}")
    @Secured({MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> toggleUnitDetailType(@PathVariable("id") long unitDetailId) {
        return ResponseEntity.ok(unitDetailService.toggleUnitDetailType(unitDetailId, true));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Unit Detail"),
            @ApiResponse(responseCode = "200", description = "When get all Unit Detail successfully!" +
                                                                " Will return List unit detail",
            content = @Content(schema = @Schema(implementation = UnitDetailDTO.class)))
    })
    @Operation(summary = "Get all Unit Detail of Unit by given Unit ID")
    @GetMapping("list-by-unit/{uid}")
    public ResponseEntity<?> listByUnitId(@Parameter(description = "Unit ID want to get detail") @PathVariable("uid") Long uid) {
        List<UnitDetailDTO> list = unitDetailService.listByUnitIdTrue(uid);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Unit Detail with Unit ID = " + uid);
        }
    }
}
