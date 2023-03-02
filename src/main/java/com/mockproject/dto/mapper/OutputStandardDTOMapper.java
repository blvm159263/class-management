package com.mockproject.dto.mapper;


import com.mockproject.dto.OutputStandardDTO;
import com.mockproject.entity.OutputStandard;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
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
