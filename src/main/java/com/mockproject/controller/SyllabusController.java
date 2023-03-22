package com.mockproject.controller;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.service.interfaces.ISyllabusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Syllabus API")
@RequestMapping(value = "/api/syllabus")
@SecurityRequirement(name = "Authorization")
@Slf4j
public class SyllabusController {

    public static final String VIEW = "ROLE_View_Syllabus";
    public static final String MODIFY = "ROLE_Modify_Syllabus";
    public static final String CREATE = "ROLE_Create_Syllabus";
    public static final String FULL_ACCESS = "ROLE_Full access_Syllabus";

    private final ISyllabusService syllabusService;

    @GetMapping()
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<SyllabusDTO>> getAll(){
        List<SyllabusDTO> listSyllabus = syllabusService.getAll(true, true);
        return ResponseEntity.ok(listSyllabus);
    }

    @GetMapping("/{syllabusId}")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<SyllabusDTO> getSyllabus(@PathVariable("syllabusId") Long syllabusId){
        SyllabusDTO syllabus = syllabusService.getSyllabusById(syllabusId, true, true);
        return ResponseEntity.ok(syllabus);
    }

    @GetMapping("get-all")
    @Secured({VIEW, MODIFY, CREATE, FULL_ACCESS})
    public ResponseEntity<List<SyllabusDTO>> getAllSyllabus(){
        return ResponseEntity.ok(syllabusService.getSyllabusList(true));
    }

    @PostMapping(value = "/create")
    @Secured({CREATE,FULL_ACCESS})
    public ResponseEntity<Long> create(@RequestBody SyllabusDTO syllabus){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long syllabusID = syllabusService.create(syllabus, user.getUser());
        return ResponseEntity.ok(syllabusID);
    }

    @PutMapping("edit")
    @Secured({MODIFY,CREATE, FULL_ACCESS})
    public ResponseEntity<Syllabus> editSyllabus(@RequestBody SyllabusDTO syllabusDTO)throws IOException {
        Syllabus editsyllabus = syllabusService.editSyllabus(syllabusDTO, true);
        return ResponseEntity.ok(editsyllabus);
    }


    @PutMapping("delete/{id}")
    @Secured({MODIFY,CREATE, FULL_ACCESS})
    public ResponseEntity<Boolean> deleteSyllabus(@PathVariable("id") Long syllabusId){
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

    @GetMapping("/list")
    @Operation(
            summary = "Get syllabus list",
            description = "<b>List of syllabus according to search, sort, filter, and pages<b>"
    )
    public ResponseEntity<?> getListSyllabus(
            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Created date - Start day (yyyy-mm-dd)<b>",
                    example = "2000-03-13"
            )
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Created date - End day (yyyy-mm-dd)<b>",
                    example = "3000-03-13"
            )
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate toDate,

            @RequestParam(defaultValue = "")
            @Parameter(
                    description = "<b>Search by syllabus name, code, creator's name, or output standard<b>",
                    example = ""
            )
            List<String> search,

            @RequestParam(defaultValue = "0")
            @Parameter(
                    description = "<b>Insert page number (0 => first page)<b>",
                    example = "0"
            )
            Optional<Integer> page,

            @RequestParam(defaultValue = "name,asc")
            @Parameter(
                    description = "<b>Sort by attribute descending/ascending"
                            + "<li>dateCreated,asc => sort by dateCreated ascending</li>"
                            + "<li>creator,desc => sort by creator's name descending</li></u><b>",
                    example = "name,asc"
            )
            String[] sort) {
        return ResponseEntity
                .ok(syllabusService.getListSyllabus(true,  fromDate, toDate, search, sort, page));
    }

}
