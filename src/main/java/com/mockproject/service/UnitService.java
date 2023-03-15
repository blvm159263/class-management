package com.mockproject.service;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.UnitMapper;
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
    private final UnitRepository unitRepository;
    private final TrainingClassRepository trainingClassRepository;
    private final TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;


    @Override
    public List<UnitDTO> getAllUnitsForTheDate(long id, int day) {
        List<Unit> unitList = getListUnitsFromSession(id, day);
        return unitList.stream().map(UnitMapper.INSTANCE::toDTO).toList();
    }

    //Get list units that training class studies
    private List<Unit> getListUnitsById(long id) {
        //Get the training class
        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true);
        //Get list units
        List<TrainingClassUnitInformation> listUnits =
        trainingClassUnitInformationRepository.findByTrainingClassAndStatus(trainingClass, true);
        return listUnits.stream()
                .map(TrainingClassUnitInformation::getUnit)
                .filter(Unit::isStatus)
                .toList();
    }

    //Get a sessions from the date
    private Session getSession(long id, int day) {
        //Get all session
        List<Session> sessionList = getListUnitsById(id).stream()
                .map(Unit::getSession)
                .filter(Session::isStatus)
                .toList();
        int sessionNth = day - 1;
        if(sessionNth == 0 ) {
            return sessionList.get(0);
        }
        if(sessionList.get(sessionNth).getId() == sessionList.get(sessionNth - 1).getId()) {
            return null;
        }
        else {
            return sessionList.get(sessionNth);
        }
    }

    //Get list units from a session
    private List<Unit> getListUnitsFromSession(long id, int day) {
        Session session = getSession(id, day);
        return unitRepository.findBySessionAndStatusOrderByUnitNumberAsc(session, true);
    }


}
