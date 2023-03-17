package com.mockproject.service;

import com.mockproject.dto.UnitDTO;
import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.UnitMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.repository.SessionRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import com.mockproject.service.interfaces.IUnitService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitService implements IUnitService {
    private final UnitRepository unitRepository;
    private final IUnitDetailService unitDetailService;
    private final SessionRepository sessionRepository;
    private final SyllabusRepository syllabusRepository;
    private final TrainingClassRepository trainingClassRepository;
    private final TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;


    @Override
    public List<UnitDTO> getAllUnitBySessionId(long sessionId, boolean status){
        Optional<List<Unit>> listUnit = unitRepository.findUnitBySessionIdAndStatus(sessionId, status);
        ListUtils.checkList(listUnit);
        List<UnitDTO> unitDTOList = new ArrayList<>();

        for (Unit u : listUnit.get()) {
            unitDTOList.add(UnitMapper.INSTANCE.toDTO(u));
        }

        for (UnitDTO u: unitDTOList){
            List<UnitDetailDTO> unitDetailDTOList = unitDetailService.getAllUnitDetailByUnitId(u.getId(), true);
            u.setUnitDetailDTOList(unitDetailDTOList);
        }
        return unitDTOList;
    }

    @Override
    public boolean createUnit(long sessionId, List<UnitDTO> listUnit, User user){
        Optional<Session> session = sessionRepository.findByIdAndStatus(sessionId, true);
        session.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));

        for (UnitDTO i: listUnit) {
            createUnit(sessionId, i, user);
        }
        return true;
    }

    @Override
    public boolean createUnit(long sessionId, UnitDTO unitDTO, User user){
        Optional<Session> session = sessionRepository.findByIdAndStatus(sessionId, true);
        session.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(session.get().getSyllabus().getId(),true);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        BigDecimal duration = syllabus.get().getHour();

        unitDTO.setSessionId(sessionId);
        Unit unit = UnitMapper.INSTANCE.toEntity(unitDTO);
        unit.setDuration(BigDecimal.valueOf(0));
        unitRepository.save(unit);
        unitDetailService.createUnitDetail(unit.getId(), unitDTO.getUnitDetailDTOList(), user);
        unit = unitRepository.findByIdAndStatus(unit.getId(), true).get();
        duration = duration.add(unit.getDuration());


        // Set duration syllabus
        syllabus.get().setHour(duration);
        return true;
    }

    @Override
    public Unit editUnit(UnitDTO unitDTO, boolean status) throws IOException {
        Optional<Unit> unit = unitRepository.findByIdAndStatus(unitDTO.getId(), status);
        unit.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));

        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(unitDTO.isStatus() == true){
            unit.get().setDuration(BigDecimal.valueOf(0));
            unitRepository.save(unit.get());
            for (UnitDetailDTO u: unitDTO.getUnitDetailDTOList()){
                if (u.getId() == null){
                    unitDetailService.createUnitDetail(unitDTO.getId(),u,user.getUser());
                }else {
                    unitDetailService.editUnitDetail(u, true);
                }
            }
        }else{
            unitDetailService.deleteUnitDetails(unitDTO.getId(), true);
        }

        unit = unitRepository.findByIdAndStatus(unitDTO.getId(), status);
        unit.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));

        unitDTO.setSessionId(unit.get().getSession().getId());
        unitDTO.setDuration(unit.get().getDuration());
        Unit updateUnit = unitRepository.save(UnitMapper.INSTANCE.toEntity(unitDTO));

        return updateUnit;
    }

    @Override
    public boolean deleteUnit(long unitId, boolean status){
        Optional<Unit> unit = unitRepository.findByIdAndStatus(unitId, status);
        unit.orElseThrow(() -> new  ResponseStatusException(HttpStatus.NO_CONTENT,"Unit "+ unitId));
        unit.get().setStatus(false);
        System.out.println("unit: "+unitId);
        unitDetailService.deleteUnitDetails(unitId, status);
        unitRepository.save(unit.get());
        return true;
    }

    @Override
    public boolean deleteUnits(long sessionId, boolean status){
        Optional<List<Unit>> units = unitRepository.findAllBySessionIdAndStatus(sessionId, status);
        ListUtils.checkList(units);
        units.get().forEach((i) -> deleteUnit(i.getId(), status));
        return true;
    }

    @Override
    public List<Unit> getUnitBySessionId(long idSession){
        return unitRepository.getListUnitBySessionId(idSession);
    }

    @Override
    public List<UnitDTO> listBySessionId(Long sid) {
        Session session = new Session();
        session.setId(sid);
        return unitRepository.findBySession(session).stream().map(UnitMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<UnitDTO> getAllUnitsForADateByTrainingClassId(long id, int dayNth) {
        List<Unit> units = getListUnitsInASessionByTrainingClassId(id, dayNth);
        return units.stream().map(UnitMapper.INSTANCE::toDTO).toList();
    }


    @Override
    // get all units from a class
    public List<Unit> getListUnitsByTrainingClassId(long id){
        // Get Class
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();

        // Get all units
        List<TrainingClassUnitInformation> list = trainingClassUnitInformationRepository.findByTrainingClassAndStatus(tc, true).orElseThrow();
        return list.stream().map(p-> unitRepository.findByIdAndStatus(p.getUnit().getId(), true).orElseThrow()).toList();
    }


    // Get a session from a date
    private Session getSession(long id, int dayNth) {
        // Get all sessions
        List<Session> sessions = getListUnitsByTrainingClassId(id).stream().map(p -> sessionRepository.findByIdAndStatus(p.getSession().getId(), true).orElseThrow()).toList();

        List<Long> sessionIds = sessions.stream().map(Session::getId).distinct().toList();
        return sessionRepository.findByIdAndStatus(sessionIds.get(dayNth - 1), true).orElseThrow();

//        Map<Integer, Long> list = new HashMap<>();
//        list.put(1, sessions.get(0).getId());
//
//
//        for (int i = 1; i < sessions.size(); i++) {
//            if (sessions.get(i).getId() == sessions.get(i - 1).getId()) {
//                list.put(i + 1, null);
//            } else list.put(i + 1, sessions.get(i).getId());
//        }
//
//        return sessionRepository.findByIdAndStatus(list.get(dayNth), true).orElseThrow();
    }

    @Override
    // Get list units from a session
    public List<Unit> getListUnitsInASessionByTrainingClassId(long id, int dayNth){
        Session session = getSession(id, dayNth);
        return unitRepository.findBySessionAndStatusOrderByUnitNumber(session, true).orElseThrow();
    }
}
