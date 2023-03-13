package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFormDTO {
    public UserFormDTO(UserDTO user)
    {
        setImage(user.getImage());
        setEmail(user.getEmail());
        setFullName(user.getFullName());
        setDob(user.getDob());
        setGender(user.isGender());
        setPhone(user.getPhone());
        setRoleId(user.getRoleId());
        setLevelId(user.getLevelId());
        setState(user.getState());
        setStatus(user.isStatus());
    }
    private String email;
    private String fullName;
    private String image;
    private int state;
    private LocalDate dob;
    private String phone;
    private boolean gender;
    private boolean status;
    private long roleId;
    private long levelId;
}
