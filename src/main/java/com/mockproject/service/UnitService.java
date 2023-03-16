package com.mockproject.service;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.UnitMapper;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.IUnitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitService implements IUnitService {
    private final UnitRepository repository;

    @Override
    public List<UnitDTO> listBySessionId(Long sid) {
        Session session = new Session();
        session.setId(sid);
        return repository.findBySession(session).stream().map(UnitMapper.INSTANCE::toDTO).toList();
    }
}
