package com.mockproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchByDTO {
    @NotEmpty(message = "searchText can not be null.")
    private List<String> searchText;
    private LocalDate nowDate;
    private LocalDate startDate;
    private LocalDate endDate;
}
