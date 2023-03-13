package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.SessionMapper;
import com.mockproject.mapper.UnitMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.IUnitService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UnitService implements IUnitService {
    private final UnitRepository unitRepository;
    private final UnitDetailService unitDetailService;
    private final SessionRepository sessionRepository;
    private final SyllabusRepository syllabusRepository;

    public UnitService(UnitRepository unitRepository, UnitDetailService unitDetailService, SessionRepository sessionRepository, SyllabusRepository syllabusRepository) {
        this.unitRepository = unitRepository;
        this.unitDetailService = unitDetailService;
        this.sessionRepository = sessionRepository;
        this.syllabusRepository = syllabusRepository;
    }

    public List<Unit> getAllUnitBySessionId(long sessionId, boolean status){
        Optional<List<Unit>> listUnit = unitRepository.findUnitBySessionIdAndStatus(sessionId, status);
        ListUtils.checkList(listUnit);
        return listUnit.get();
    }

    public boolean createUnit(long sessionId, List<UnitDTO> listUnit, long userId){
        Optional<Session> session = sessionRepository.findByIdAndStatus(sessionId, true);
        session.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        BigDecimal duration = BigDecimal.valueOf(0);
        for (UnitDTO i: listUnit) {
            i.setSessionId(sessionId);
            Unit unit = unitRepository.save(UnitMapper.INSTANCE.toEntity(i));
            unitDetailService.createUnitDetail(unit.getId(), i.getUnitDetailDTOList(), userId);
            unit = unitRepository.findByIdAndStatus(unit.getId(), true).get();
            duration = duration.add(unit.getDuration());
        }

        // Set duration syllabus
        long syllabusId = sessionRepository.findByIdAndStatus(sessionId, true).get().getSyllabus().getId();
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(syllabusId, true);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        syllabus.get().setHour(duration);
        return true;
    }

    public Unit editUnit(long id, UnitDTO unitDTO, boolean status){
        Optional<Unit> unit = unitRepository.findByIdAndStatus(id, status);
        unit.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        unitDTO.setId(id);
        unitDTO.setSessionId(unit.get().getSession().getId());
        Unit updateUnit = unitRepository.save(UnitMapper.INSTANCE.toEntity(unitDTO));
        return updateUnit;
    }

    public boolean deleteUnit(long unitId, boolean status){
        Optional<Unit> unit = unitRepository.findByIdAndStatus(unitId, status);
        unit.orElseThrow(() -> new  ResponseStatusException(HttpStatus.NO_CONTENT,"Unit "+ unitId));
        unit.get().setStatus(false);
        System.out.println("unit: "+unitId);
        unitDetailService.deleteUnitDetails(unitId, status);
        unitRepository.save(unit.get());
        return true;
    }

    public boolean deleteUnits(long sessionId, boolean status){
        Optional<List<Unit>> units = unitRepository.findAllBySessionIdAndStatus(sessionId, status);
        ListUtils.checkList(units);
        units.get().forEach((i) -> deleteUnit(i.getId(), status));
        return true;
    }
}
