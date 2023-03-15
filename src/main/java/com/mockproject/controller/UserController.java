package com.mockproject.controller;

import com.mockproject.service.interfaces.IUserService;
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
@Tag(name = "User API")
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final IUserService userService;

    @Operation(
            summary = "Get the information of the trainer of the training class by the training class code",
            description = "<b>List the information of the trainer of the training class</b>"
    )
    @GetMapping("trainingClass/trainer/{classCode}")
    public ResponseEntity<?> getTrainerByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02"
            )
            String code) {
        return ResponseEntity.ok(userService.getTrainerByClassCode(code));
    }

    @Operation(
            summary = "Get the information of the trainer that the training class will study on that day",
            description = "<b>List the information of the trainer of the training class on that day</b>"
    )
    @GetMapping("trainingClass/trainerOnDay")
    public ResponseEntity<?> getTrainerOnTheDay(
            @Parameter(
                    description = "<b>Insert the training class Id</b>"
            ) long id,
            @Parameter(
                    description = "<b>Insert Id of the day</b>"
            ) int day) {
        return ResponseEntity.ok(userService.getTrainerOntheDayById(id, day));
    }

//    @Operation(
//            summary = "Get the information of the trainer that the training class studies by Id",
//            description = "<b>List the information of the trainer that the training class studies</b>"
//    )
//    @GetMapping("trainingClass/trainer/{trainerId}")
//    public ResponseEntity<?> getTrainerById(
//            @PathVariable("trainerId")
//            @Parameter(
//                    description = "<b>Insert Id of the Trainer</b>",
//                    example = "1"
//            )
//            long id) {
//        return ResponseEntity.ok(userService.getTrainerById(id));
//    }

    @Operation(
            summary = "Get the information of the creator of the training class by the training class code",
            description = "<b>List the information of the creator of the training class</b>"
    )
    @GetMapping("trainingClass/creator/{classCode}")
    public ResponseEntity<?> getCreatorByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02"
            )
            String code) {
        return ResponseEntity.ok(userService.getCreatorByClassCode(code));
    }

    @Operation(
            summary = "Get the information of the reviewer of the training class by the training class code",
            description = "<b>List the information of the reviewer of the training class</b>"
    )
    @GetMapping("trainingClass/reviewer/{classCode}")
    public ResponseEntity<?> getReviewerByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02"
            )
            String code) {
        return ResponseEntity.ok(userService.getReviewerByClassCode(code));
    }

    @Operation(
            summary = "Get the information of the approver of the training class by the training class code",
            description = "<b>List the information of the approver of the training class</b>"
    )
    @GetMapping("trainingClass/approver/{classCode}")
    public ResponseEntity<?> getApproverByClassCode(
            @PathVariable("classCode")
            @Parameter(
                    description = "<b>Insert Training Class Code</b>",
                    example = "DN22_IN_FT_02"
            )
            String code) {
        return ResponseEntity.ok(userService.getApproverByClassCode(code));
    }
}
