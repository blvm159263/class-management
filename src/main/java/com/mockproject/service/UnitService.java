package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.SessionMapper;
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
import java.util.Optional;

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

    public Unit editUnit(long id, long sessionId, UnitDTO unitDTO){
        Optional<Unit> unit = Optional.ofNullable(unitRepository.findByIdAndSessionIdAndStatus(id, sessionId, true));
        unit.orElseThrow(() -> new RuntimeException("Unit doesn't exist."));
        unitDTO.setSessionId(sessionId);
        unitDTO.setId(id);
        Unit updateUnit = unitRepository.save(UnitMapper.INSTANCE.toEntity(unitDTO));
        return updateUnit;
    }
}
