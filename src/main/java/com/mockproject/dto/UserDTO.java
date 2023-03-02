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
    private long id;
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
    private long attendeeId;
}
