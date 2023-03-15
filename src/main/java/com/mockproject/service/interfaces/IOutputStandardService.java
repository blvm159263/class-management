package com.mockproject.service.interfaces;

import com.mockproject.dto.OutputStandardDTO;

import java.util.List;

public interface IOutputStandardService {

    OutputStandardDTO getOutputStandardById(long outputStandardId, boolean status);
    List<OutputStandardDTO> getOutputStandard(boolean status);
}
