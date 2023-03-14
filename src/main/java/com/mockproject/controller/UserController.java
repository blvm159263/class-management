package com.mockproject.controller;

import com.mockproject.dto.UserDTO;
import com.mockproject.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final IUserService userService;

//    @GetMapping("trainingClass/trainer/{classCode}")
//    public List<UserDTO> getTrainerByClassCode(@PathVariable("classCode") String code) {
//        return userService.getTrainerByClassCode(code);
//    }

    @GetMapping("trainingClass/trainer/{trainerId}")
    public List<UserDTO> getTrainerById(@PathVariable("trainerId") long id) {
        return userService.getTrainerById(id);
    }

    @GetMapping("trainingClass/creator/{classCode}")
    public UserDTO getCreatorByClassCode(@PathVariable("classCode") String code) {
        return userService.getCreatorByClassCode(code);
    }

    @GetMapping("trainingClass/reviewer/{classCode}")
    public UserDTO getReviewerByClassCode(@PathVariable("classCode") String code) {
        return userService.getReviewerByClassCode(code);
    }

    @GetMapping("trainingClass/approver/{classCode}")
    public UserDTO getApproverByClassCode(@PathVariable("classCode") String code) {
        return userService.getApproverByClassCode(code);
    }
}
