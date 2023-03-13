package com.mockproject.service.interfaces;

import com.mockproject.dto.OutputStandardDTO;

import java.util.List;

public interface IOutputStandardService {

    List<OutputStandardDTO> getOsdBySyllabusId(boolean status, long id);
}
