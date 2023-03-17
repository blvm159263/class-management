package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchByDTO {
    private List<String> searchText;
    private LocalDate nowDate;
    private LocalDate startDate;
    private LocalDate endDate;
}
