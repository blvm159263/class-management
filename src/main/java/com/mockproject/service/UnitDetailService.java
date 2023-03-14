package com.mockproject.service;

import com.mockproject.entity.UnitDetail;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UnitDetailService implements IUnitDetailService {
    private final UnitDetailRepository repository;

    public List<UnitDetail> getUnitDetailByUnitId(long idUnit){
        return repository.getListUnitDetailByUnitId(idUnit);
    }
}
