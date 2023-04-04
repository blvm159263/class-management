package com.mockproject.service;

import com.mockproject.dto.TowerDTO;
import com.mockproject.entity.*;
import com.mockproject.repository.TowerRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.service.interfaces.IUnitService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TowerService.class})
@ExtendWith(SpringExtension.class)
class TowerServiceTest {
    @MockBean
    private TowerRepository towerRepository;
    @MockBean
    private TrainingClassRepository trainingClassRepository;
    @MockBean
    private TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;
    @MockBean
    private IUnitService unitService;

    @Autowired
    private TowerService towerService;

    //Create Location
    Location location1 = new Location(1L, "Location 1", "123 Le Loi", true, null, null);
    Location location2 = new Location(2L, "Location 2", "333 Le Hong Phong", false, null, null);

    //Create Tower
    Tower tower1 = new Tower(1L, "FTown 1", "123 Le Loi", true, location1,null);
    Tower tower2 = new Tower(2L, "FTown 2", "66 Nguyen Trai", false, location2,null);
    Tower tower3 = new Tower(3L, "FTown 3", "999 Ba Trieu", true, location1,null);

    //Create training Class
    TrainingClass tc1 = new TrainingClass(1L, "Class Name 1", "TC1", null, null,
            null, null, 12, 30, 30, 25, "Planning", null,
            null, null, null, 1, true, null, null,
            location1, null, null, null, null, null, null,
            null, null, null);

    TrainingClass tc2 = new TrainingClass(2L, "Class Name 2", "TC2", null, null,
            null, null, 10, 20, 20, 20, "Planning", null,
            null, null, null, 1, true, null, null,
            location2, null, null, null, null, null, null,
            null, null, null);

    // Create Unit
    Unit unit1 = new Unit(1L, "Unit title 123", 1, BigDecimal.TEN, true, null, null, null);
    Unit unit2 = new Unit(2L, "Unit title 234", 2, BigDecimal.TEN, true, null, null, null);

    //Create Training Class Unit Information
    TrainingClassUnitInformation ui1 = new TrainingClassUnitInformation(1L, true, null, unit1, tc1, tower1);
    TrainingClassUnitInformation ui2 = new TrainingClassUnitInformation(2L, true, null, unit2, tc2, tower2);
    TrainingClassUnitInformation ui3 = new TrainingClassUnitInformation(3L, true, null, unit2, tc1, tower3);


    /**
     * Method under test: {@link TowerService#listByTowerIdTrue(Long)}
     */
    @Test
    void canListTowerWithStatusTrueByGivenLocationId() {

        List<Tower> list = new ArrayList<>();
        list.add(tower1);
        list.add(tower2);
        list.add(tower3);

        Location location = new Location();
        location.setId(1L);

        when(towerRepository.findByLocationAndStatus(location, true))
                .thenReturn(list.stream().filter(p-> p.isStatus() && p.getLocation().getId() == location.getId()).toList());

        List<TowerDTO> result = towerService.listByTowerIdTrue(location.getId());
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("FTown 1", result.get(0).getTowerName());
        assertEquals(3L, result.get(1).getId());
        assertEquals("999 Ba Trieu", result.get(1).getAddress());
        verify(towerRepository).findByLocationAndStatus(location,true);
    }

    /**
     * Method under test: {@link TowerService#getAllTowersByTrainingClassId(Long)}
     */
    @Test
    void canGetAllTowersByTrainingClassId() {

        List<TrainingClassUnitInformation> trainingClassUnitInformations = new ArrayList<>();
        trainingClassUnitInformations.add(ui1);
        trainingClassUnitInformations.add(ui2);

        Long trainingClassId = 1L;
        tc1.setListTrainingClassUnitInformations(trainingClassUnitInformations);

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(tc1));

        List<TowerDTO> result = towerService.getAllTowersByTrainingClassId(tc1.getId());

        assertEquals(1L, result.get(0).getId());
        assertEquals("FTown 1", result.get(0).getTowerName());
        assertEquals("123 Le Loi", result.get(0).getAddress());
        assertTrue(result.stream().filter(p -> !p.isStatus()).toList().isEmpty());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);
    }

    /**
     * Method under test: {@link TowerService#getTowerForTheDayByTrainingClassId(Long, int)}
     */
    @Test
    void canGetTowerForTheDayByTrainingClassId() {
        Long trainingClassId = 1L;
        int dayNth = 1;

        List<Unit> unitList = new ArrayList<>();
        unitList.add(unit1);
        unitList.add(unit2);

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(tc1));
        when(unitService.getListUnitsInASessionByTrainingClassId(tc1.getId(), dayNth))
                .thenReturn(unitList);
        when(trainingClassUnitInformationRepository.findByUnitAndTrainingClassAndStatus(unit1 , tc1, true))
                .thenReturn(Optional.of(ui1) );
        when(trainingClassUnitInformationRepository.findByUnitAndTrainingClassAndStatus(unit2 , tc1, true))
                .thenReturn(Optional.of(ui3) );

        List<TowerDTO> towers = towerService.getTowerForTheDayByTrainingClassId(trainingClassId, dayNth);
        assertEquals(1L, towers.get(0).getId());
        assertEquals("FTown 1", towers.get(0).getTowerName());
        assertEquals("123 Le Loi", towers.get(0).getAddress());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);
        verify(unitService).getListUnitsInASessionByTrainingClassId(trainingClassId, dayNth);
        verify(trainingClassUnitInformationRepository).findByUnitAndTrainingClassAndStatus(unit1, tc1, true);
        verify(trainingClassUnitInformationRepository).findByUnitAndTrainingClassAndStatus(unit2, tc1, true);

    }
}

