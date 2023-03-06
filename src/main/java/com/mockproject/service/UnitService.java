package com.mockproject.service;

import com.mockproject.entity.Unit;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.IUnitService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UnitService implements IUnitService {
    private final UnitRepository repository;

    public List<Unit> getAllUnitBySessionId(long sessionId, boolean status){
        List<Unit> listUnit = repository.findUnitBySessionIdAndStatus(sessionId, status);
        if (listUnit == null)
            return  new ArrayList<>();
        else return listUnit;
    }
}
