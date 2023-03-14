package com.mockproject.service;

import com.mockproject.entity.Unit;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.IUnitService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UnitService implements IUnitService {
    private final UnitRepository repository;
    public List<Unit> getUnitBySessionId(long idSession){
        return repository.getListUnitBySessionId(idSession);
    }
}
