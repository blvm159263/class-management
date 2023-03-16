package com.mockproject.controller;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.dto.ContactDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Contact API")
@RequiredArgsConstructor
@RequestMapping("api/contact")
public class ContactController {
    private final ContactService contactService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any Training Class"),
            @ApiResponse(responseCode = "200", description = "When we have found the training class",
                    content = @Content(schema = @Schema(implementation = ContactDTO.class)))
    })
    @Operation(
            summary = "Get contact information of the trainer by Training Class ID",
            description = "List the information of the contact trainer by Training Class ID"
    )
    @GetMapping("trainer/trainingClass/{id}")
    public ResponseEntity<?> getContactTrainerById(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert the training class ID to get the information of the trainer</b>",
                    example = "1"
            ) long id) {
        try {
            return ResponseEntity.ok(contactService.getContactById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Can't find any contact information with training class Id is " + id);
        }
    }
}
