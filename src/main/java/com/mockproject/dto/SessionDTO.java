package com.mockproject.dto;

import com.mockproject.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO implements Serializable {
    private long id;
    private int sessionNumber;
    private boolean status;
    private long syllabusId;
    private List<Unit> listUnit;
}
