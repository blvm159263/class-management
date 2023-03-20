package com.mockproject.controller;

import com.mockproject.dto.TowerDTO;
import com.mockproject.service.interfaces.ITowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tower API")
@RequestMapping("api/tower")
@SecurityRequirement(name = "Authorization")
public class TowerController {

    private final ITowerService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Tower"),
            @ApiResponse(responseCode = "200", description = "When found Tower",
            content = @Content(schema = @Schema(implementation = TowerDTO.class)))
    })
    @Operation(summary = "Get all Tower have status true by given Location Id")
    @GetMapping("location/{id}")
    public ResponseEntity<?> listByLocationIdTrue(@Parameter(description = "Location ID want to get Tower") @PathVariable("id") Long id) {
        List<TowerDTO> list = service.listByTowerIdTrue(id);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any Tower with Location ID = " + id);
        }
    }
}
