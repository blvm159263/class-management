package com.mockproject.service.interfaces;

import com.mockproject.dto.FsuDTO;

import java.util.List;

public interface IFsuService {

    FsuDTO getFsuById(long id);

    List<FsuDTO> getAllFsu();
}
