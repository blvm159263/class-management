package com.mockproject.service;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.UnitMapper;
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
    private final UnitRepository unitRepository;

    public List<Unit> getAllUnitBySessionId(long sessionId, boolean status){
        List<Unit> listUnit = unitRepository.findUnitBySessionIdAndStatus(sessionId, status);
        if (listUnit == null)
            return  new ArrayList<>();
        else return listUnit;
    }

    public boolean createUnit(long sessionId, List<UnitDTO> listUnit){
        listUnit.forEach((i) ->
        {
            i.setSessionId(sessionId);
            unitRepository.save(UnitMapper.INSTANCE.toEntity(i));
        });
        return true;
    }
}
