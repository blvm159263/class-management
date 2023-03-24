package com.mockproject.service;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.entity.Unit;
import com.mockproject.repository.*;
import com.mockproject.service.interfaces.IUnitDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UnitService.class})
@ExtendWith(SpringExtension.class)
class UnitServiceTest {
    @MockBean
    private UnitRepository unitRepository;
    @MockBean
    private IUnitDetailService unitDetailService;
    @MockBean
    private SessionRepository sessionRepository;
    @MockBean
    private SyllabusRepository syllabusRepository;
    @MockBean
    private TrainingClassRepository trainingClassRepository;
    @MockBean
    private TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;

    @Autowired
    private UnitService unitService;

    // Create Session
    Session s1 = new Session(1L, 1, true, null, null);
    Session s2 = new Session(2L, 2, true, null, null);

    // Create Unit
    Unit unit1 = new Unit(1L, "Unit title 123", 1, BigDecimal.TEN, true, s1, null, null);
    Unit unit2 = new Unit(2L, "Unit title 234", 2, BigDecimal.TEN, true, s2, null, null);
    Unit unit3 = new Unit(3L, "Unit title 345", 3, BigDecimal.TEN, true, s1, null, null);

    //Create Training Class
    TrainingClass trainingClass1 = new TrainingClass(1L, "Class Name 1", "TC1", null, null,
            null, null, 12, 30, 30, 25, "Planning", null,
            null, null, null, 1, true, null, null,
            null, null, null, null, null, null, null,
            null, null, null);

    //Create Training Class Unit Information
    TrainingClassUnitInformation ui1 = new TrainingClassUnitInformation(1L, true, null, unit1, trainingClass1, null);
    TrainingClassUnitInformation ui2 = new TrainingClassUnitInformation(2L, true, null, unit2, trainingClass1, null);

    /**
     * Method under test: {@link UnitService#listBySessionId(Long)}
     */
    @Test
    void canListUnitByGivenSessionId() {
        ArrayList<Unit> unitList = new ArrayList<>();
        unitList.add(unit1);
        unitList.add(unit2);
        unitList.add(unit3);

        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        when(unitRepository.findBySession(session)).thenReturn(unitList.stream().filter(p -> p.getSession().getId() == s1.getId()).toList());
        List<UnitDTO> result = unitService.listBySessionId(1L);
        assertEquals(2, result.size());
        assertEquals("Unit title 123", result.get(0).getUnitTitle());
        verify(unitRepository).findBySession((Session) any());
    }

    /**
     * Method under test: {@link UnitService#getListUnitsByTrainingClassId(Long)}
     */
    @Test
    void getGetListUnitsByTrainingClassId() {
        Long trainingClassId = 1L;

        List<TrainingClassUnitInformation> classUnitInformations = new ArrayList<>();
        classUnitInformations.add(ui1);
        classUnitInformations.add(ui2);

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(trainingClass1));
        when(trainingClassUnitInformationRepository.findByTrainingClassAndStatus(trainingClass1, true))
                .thenReturn(Optional.of(classUnitInformations));

        List<Unit> unitList = unitService.getListUnitsByTrainingClassId(trainingClassId);
        assertEquals(2, unitList.size());
        assertEquals(1L, unitList.get(0).getId());
        assertEquals("Unit title 123", unitList.get(0).getUnitTitle());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);
        verify(trainingClassUnitInformationRepository).findByTrainingClassAndStatus(trainingClass1, true);
    }
}

