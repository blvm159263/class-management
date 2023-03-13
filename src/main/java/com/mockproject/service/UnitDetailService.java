package com.mockproject.service;

import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitDetailService implements IUnitDetailService {
    private final UnitDetailRepository repository;

}
