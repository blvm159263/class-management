package com.mockproject.service.interfaces;

import com.mockproject.dto.FsuDTO;
import com.mockproject.repository.FsuRepository;

import java.util.List;

public interface IFsuService {

    FsuDTO getFsuById(boolean status, long id);

    List<FsuDTO> getAllFsu(boolean status);
}
