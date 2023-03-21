package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;
import com.mockproject.service.interfaces.ISyllabusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/syllabus")
public class SyllabusController {

    private final ISyllabusService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any syllabus"),
            @ApiResponse(responseCode = "200", description = "When found list of syllabus",
            content = @Content(schema = @Schema(implementation = SyllabusDTO.class)))
    })
    @Operation(summary = "Get all Syllabus by given Training Program ID")
    @GetMapping("/list-by-training-program/{id}")
    public ResponseEntity<?> listSyllabusByTrainingProgramId(@Parameter(description = "Training Class's ID that want to get Syllabus")
                                                             @PathVariable("id") Long id) {
        List<SyllabusDTO> list = service.listByTrainingProgramIdTrue(id);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any syllabus with Training Program = " + id);
        }
    }

    @PostMapping("read-file")
    public ResponseEntity<List<SyllabusDTO>> readSyllabusCsv(@RequestParam("file")MultipartFile file) throws IOException {
//        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id(4L).build();
        return ResponseEntity.ok(service.readFileCsv(file, user));
    }

    @GetMapping("get-template-file")
    public ResponseEntity<byte[]> getTemplateFile() throws IOException {
        return ResponseEntity.ok(service.getTemplateCsvFile());
//        return ResponseEntity.ok()
//                .contentType(MediaType.asMediaType(MimeType.valueOf("text/csv")))
//                .body(service.getTemplateCsvFile());
    }
}
