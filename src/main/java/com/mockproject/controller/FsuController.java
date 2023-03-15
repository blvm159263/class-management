package com.mockproject.controller;

import com.mockproject.service.interfaces.IFsuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Fsu API")
@RequestMapping("/api/fsu")
@SecurityRequirement(name = "Authorization")
public class FsuController {

    public static final String VIEW = "ROLE_View_Class";
    public static final String MODIFY = "ROLE_Modify_Class";
    public static final String CREATE = "ROLE_Create_Class";
    public static final String FULL_ACCESS = "ROLE_Full access_Class";
    private final IFsuService fsuService;

    @GetMapping("/list")
    @Operation(
            summary = "Get fsu list"
    )
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity<?> getAllFsu(){
        return ResponseEntity.ok(fsuService.getAllFsu(true));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get fsu by ID"
    )
    @Secured({VIEW, MODIFY, FULL_ACCESS, CREATE})
    public ResponseEntity<?> getFsuById(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert ID to get fsu<b>",
                    example = "1"
            ) long id) {
        return ResponseEntity.ok(fsuService.getFsuById(true, id));
    }
}
