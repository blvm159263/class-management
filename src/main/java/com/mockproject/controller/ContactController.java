package com.mockproject.controller;

import com.mockproject.dto.ContactDTO;
import com.mockproject.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    @Operation(
            summary = "Get contact information of the trainer by Training Class ID",
            description = "List the information of the contact trainer by Training Class ID"
    )
    @GetMapping("trainer/trainingClass/{id}")
    public ResponseEntity<?> getContactTrainerById(
            @PathVariable("id")
            @Parameter(
                    description = "<b>Insert the training class ID</b>",
                    example = "1"
            ) long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }
}
