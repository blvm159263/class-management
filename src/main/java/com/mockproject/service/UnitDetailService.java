package com.mockproject.service;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.UnitDetailMapper;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitDetailService implements IUnitDetailService {
    private final UnitDetailRepository repository;

    public List<UnitDetailDTO> listByUnitIdTrue(Long id) {
        Unit unit = new Unit();
        unit.setId(id);
        return repository.findByUnitAndStatus(unit,true).stream().map(UnitDetailMapper.INSTANCE::toDTO).toList();
    }
}
