package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.UnitMapper;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.IUnitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitService implements IUnitService {
    private final UnitRepository unitRepository;
    @Override
    public List<UnitDTO> getListUnit(List<SessionDTO> session){
        List<UnitDTO> listUnit = new ArrayList<>();
        for(SessionDTO s : session){
            listUnit.addAll(getUnitBySessionId(s.getId()));
        }
        return listUnit;
    }

    public List<UnitDTO> getUnitBySessionId(Long idSession){
        return unitRepository.getListUnitBySessionId(idSession).stream().map(UnitMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
