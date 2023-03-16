package com.mockproject.service;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.UnitMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.IUnitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitService implements IUnitService {
    private final UnitRepository repository;
    private final TrainingClassRepository trainingClassRepository;
    private final TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;
    private final SessionRepository sessionRepository;

    @Override
    public List<UnitDTO> listBySessionId(Long sid) {
        Session session = new Session();
        session.setId(sid);
        return repository.findBySession(session).stream().map(UnitMapper.INSTANCE::toDTO).toList();
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
        return list.stream().map(p-> repository.findByIdAndStatus(p.getUnit().getId(), true).orElseThrow()).toList();
    }


    // Get a session from a date
    private Session getSession(long id, int dayNth) {
        // Get all sessions
        List<Session> sessions = getListUnitsByTrainingClassId(id).stream().map(p -> sessionRepository.findByIdAndStatus(p.getSession().getId(), true).orElseThrow()).toList();

        List<Long> sessionIds = sessions.stream().map(Session::getId).distinct().toList();
        return sessionRepository.findByIdAndStatus(sessionIds.get(dayNth - 1),true).orElseThrow();

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
        return repository.findBySessionAndStatusOrderByUnitNumber(session, true).orElseThrow();
    }
}
