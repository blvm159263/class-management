package com.mockproject.service.interfaces;

import com.mockproject.dto.SyllabusDTO;

import java.util.List;

public interface ISyllabusService {
    List<SyllabusDTO> getAll();
    SyllabusDTO get(long id);
}
