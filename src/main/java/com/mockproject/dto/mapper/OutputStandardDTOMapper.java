package com.mockproject.dto.mapper;


import com.mockproject.dto.OutputStandardDTO;
import com.mockproject.entity.OutputStandard;

import java.util.function.Function;

public class OutputStandardDTOMapper implements Function<OutputStandard, OutputStandardDTO> {


    @Override
    public OutputStandardDTO apply(OutputStandard outputStandard) {
        return new OutputStandardDTO(
                outputStandard.getId(),
                outputStandard.getStandardCode(),
                outputStandard.getStandardName(),
                outputStandard.getDescription(),
                outputStandard.isStatus()
        );
    }
}
