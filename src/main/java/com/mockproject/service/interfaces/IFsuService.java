package com.mockproject.service.interfaces;

import com.mockproject.dto.FsuDTO;

public interface IFsuService {
    FsuDTO getFsuByClassCode(String code, boolean status);
}
