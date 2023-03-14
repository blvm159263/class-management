package com.mockproject.service.interfaces;

import com.mockproject.dto.FsuDTO;

import java.util.List;

public interface IFsuService {

    FsuDTO getFsuById(boolean status, long id);

    List<FsuDTO> getAllFsu(boolean status);
}
