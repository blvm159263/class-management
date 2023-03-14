package com.mockproject.dto;

import com.mockproject.service.LevelService;
import com.mockproject.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFormDTO {
    private String fullName;
    private String image;
    private int state;
    private LocalDate dob;
    private String phone;
    private Long roleId;
    private Long levelId;
}
