package com.mockproject.service;

import com.mockproject.dto.UnitDetailDTO;
//<<<<<<< HEAD
import com.mockproject.entity.UnitDetail;
//=======
import com.mockproject.entity.Unit;
//>>>>>>> ed0aa085b95a13c82c9adb2ccaee04db52e424cf
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

    private final UnitDetailMapper mapper;

    @Override
    public UnitDetail get(long id) {
        return repository.getReferenceById(id);
    }
//=======
    public List<UnitDetailDTO> listByUnitIdTrue(Long id) {
        Unit unit = new Unit();
        unit.setId(id);
        return repository.findByUnitAndStatus(unit,true).stream().map(UnitDetailMapper.INSTANCE::toDTO).toList();
//>>>>>>> ed0aa085b95a13c82c9adb2ccaee04db52e424cf
    }
}
