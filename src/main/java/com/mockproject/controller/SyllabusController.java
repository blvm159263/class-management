package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.TrainingProgramSyllabusDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;
import com.mockproject.service.interfaces.ISyllabusService;
import com.mockproject.service.interfaces.ITrainingProgramSyllabusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Syllabus", description = "API related Syllabus")
@RequestMapping(value = "/api/syllabus")
@SecurityRequirement(name = "Authorization")
public class SyllabusController {

    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

    private final ISyllabusService syllabusService;

    private final ITrainingProgramSyllabusService trainingProgramSyllabusService;

    @GetMapping("/getSyllabusByTrainingProgram/{trainingProgramId}")
    @Operation(summary = "Get all syllabus by training program id")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<TrainingProgramSyllabusDTO>> getAllTrainingProgramSyllabus(@PathVariable("trainingProgramId") long id){
        List<TrainingProgramSyllabusDTO> list = trainingProgramSyllabusService.getAllSyllabusByTrainingProgramId(id, true);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{syllabusId}")
    @Operation(summary = "Get syllabus by syllabus id")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<SyllabusDTO> getSyllabus(@PathVariable("syllabusId") long syllabusId){
        SyllabusDTO syllabus = syllabusService.getSyllabusById(syllabusId, true, true);
        return ResponseEntity.ok(syllabus);
    }

    @GetMapping("get-all")
    @Operation(summary = "Get all syllabus")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<SyllabusDTO>> getAllSyllabus(){
        return ResponseEntity.ok(syllabusService.getSyllabusList(true));
    }

    @PostMapping(value = "/replace")
    @Operation(description = "Replace Syllabus")
    @Secured({CREATE,FULL_ACCESS})
    public ResponseEntity<Boolean> replace(@RequestBody SyllabusDTO syllabusDTO){
        return ResponseEntity.ok(syllabusService.replace(syllabusDTO, true));
    }

    @PostMapping(value = "/create")
    @Operation(description = "Create Syllabus")
    @Secured({CREATE,FULL_ACCESS})
    public ResponseEntity<Long> create(@RequestBody SyllabusDTO syllabus){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long syllabusID = syllabusService.create(syllabus, user.getUser());
        return ResponseEntity.ok(syllabusID);
    }

    @PutMapping("edit")
    @Operation(summary = "Edit syllabus by SyllabusDTO")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity<Syllabus> editSyllabus(@RequestBody SyllabusDTO syllabusDTO)throws IOException {
        Syllabus editsyllabus = syllabusService.editSyllabus(syllabusDTO, true);
        return ResponseEntity.ok(editsyllabus);
    }

    @PutMapping("delete/{id}")
    @Operation(summary = "Delete syllabus by syllabusId")
    @Secured({MODIFY, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteSyllabus(@PathVariable("id")@Parameter(description = "Syllabus id") long syllabusId){
        return ResponseEntity.ok(syllabusService.deleteSyllabus(syllabusId, true));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "When don't find any syllabus"),
            @ApiResponse(responseCode = "200", description = "When found list of syllabus",
                    content = @Content(schema = @Schema(implementation = SyllabusDTO.class)))
    })
    @Operation(summary = "Get all Syllabus by given Training Program ID")
    @GetMapping("/list-by-training-program/{id}")
    public ResponseEntity<?> listSyllabusByTrainingProgramId(@Parameter(description = "Training Class's ID that want to get Syllabus")
                                                             @PathVariable("id") Long id) {
        List<SyllabusDTO> list = syllabusService.listByTrainingProgramIdTrue(id);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Don't find any syllabus with Training Program = " + id);
        }
    }

    @PostMapping(path = "read-file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Read file")
    public ResponseEntity<SyllabusDTO> readSyllabusCsv(@RequestPart("file")MultipartFile file,
                                                       @Parameter(description = "1. Name\n" +
                                                                                "2. Code\n" +
                                                                                "3. Name and Code") int condition,
                                                       @Parameter(description = "1. Allow\n" +
                                                                                "2. Replace\n" +
                                                                                "3. Skip") int handle) throws IOException {
//        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(syllabusService.readFileCsv(file, condition, handle));
    }

    @GetMapping("get-template-file")
    @Operation(summary = "Download file")
    public ResponseEntity<byte[]> getTemplateFile() throws IOException {

        String filename = "Syllabus_import.csv";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(syllabusService.getTemplateCsvFile());
//        return ResponseEntity.ok()
//                .contentType(MediaType.asMediaType(MimeType.valueOf("text/csv")))
//                .body(service.getTemplateCsvFile());
    }
}
