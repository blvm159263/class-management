package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String image;
    private int state;
    private LocalDate dob;
    private String phone;
    private boolean gender;
    private boolean status;
    private Long roleId;
    private Long levelId;
    private Long attendeeId;
}
