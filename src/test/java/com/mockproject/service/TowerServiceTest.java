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
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        //Create list training class unit information
        List<TrainingClassUnitInformation> trainingClassUnitInformation = new ArrayList<>();
        trainingClassUnitInformation.add(ui1);
        trainingClassUnitInformation.add(ui3);

        //Set List Training Class Unit Information
        tc1.setListTrainingClassUnitInformations(trainingClassUnitInformation);

        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.of(tc1));
        when(trainingClassUnitInformationRepository.findByTrainingClassAndStatus(tc1, true))
                .thenReturn(Optional.of(trainingClassUnitInformation));
        trainingClassUnitInformation.forEach(p ->
                when(towerRepository.findByIdAndStatus(p.getTower().getId(), true))
                        .thenReturn(Optional.of(trainingClassUnitInformation
                                .get(trainingClassUnitInformation.indexOf(p)).getTower())));


        List<TowerDTO> result = towerService.getAllTowersByTrainingClassId(tc1.getId());

        assertEquals(1L, result.get(0).getId());
        assertEquals("FTown 1", result.get(0).getTowerName());
        assertEquals("123 Le Loi", result.get(0).getAddress());
        assertTrue(result.stream().filter(p -> !p.isStatus()).toList().isEmpty());

        verify(trainingClassRepository).findByIdAndStatus(1L, true);
        verify(trainingClassUnitInformationRepository).findByTrainingClassAndStatus(tc1, true);
        trainingClassUnitInformation.forEach(p ->
                verify(towerRepository).findByIdAndStatus(p.getTower().getId(), true));
    }

    @Test
    void itShouldThrowExceptionWhenNotFoundTrainingClassTower() {
        //Case 1: Not found any training class
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> towerService.getAllTowersByTrainingClassId(1L));

        //Case 2: Not found any training class unit
        when(trainingClassRepository.findByIdAndStatus(2L, true))
                .thenReturn(Optional.empty());
        when(trainingClassUnitInformationRepository.findByTrainingClassAndStatus(new TrainingClass(), true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> towerService.getAllTowersByTrainingClassId(2L));

        //Case 3: Not found any training class tower
        when(trainingClassRepository.findByIdAndStatus(3L, true))
                .thenReturn(Optional.empty());
        when(trainingClassUnitInformationRepository.findByTrainingClassAndStatus(new TrainingClass(), true))
                .thenReturn(Optional.empty());
        when(towerRepository.findByIdAndStatus(tower1.getId(), true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> towerService.getAllTowersByTrainingClassId(3L));
    }

    /**
     * Method under test: {@link TowerService#getTowerForTheDayByTrainingClassId(Long, int)}
     */
    @Test
    void canGetTowerForTheDayByTrainingClassId() {

        //Create id
        AtomicReference<Long> id = new AtomicReference<>(1L);

        //Create list unit
        List<Unit> unitList = new ArrayList<>();
        unitList.add(unit1);
        unitList.add(unit2);

        //Assign value for each unit
        unitList.forEach(u -> {
            u.setId(id.get());
            id.getAndSet(id.get() + 1);
            u.setListTrainingClassUnitInformations(new ArrayList<>(List.of(new TrainingClassUnitInformation())));
            u.setStatus(true);
        });

        //Reset id
        id.getAndSet(1L);

        //Create list training class unit infor
        List<TrainingClassUnitInformation> informationList = new ArrayList<>();

        //Assign value for each training class unit infor
        unitList.forEach(i -> {
            TrainingClassUnitInformation trainingClassUnitInfor = i.getListTrainingClassUnitInformations().get(0);
            trainingClassUnitInfor.setId(id.get());
            id.getAndSet(id.get() + 1);
            trainingClassUnitInfor.setTrainingClass(tc1);
            trainingClassUnitInfor.setUnit(i);
            trainingClassUnitInfor.setTrainer(null);
            trainingClassUnitInfor.setTower(id.get() % 2 == 0 ? tower1 : tower3);
            trainingClassUnitInfor.setStatus(true);
            informationList.add(trainingClassUnitInfor);
        });

        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.of(tc1));
        when(unitService.getListUnitsInASessionByTrainingClassId(tc1.getId(), 1))
                .thenReturn(unitList);
        unitList.stream().filter(Unit::isStatus).toList();

        unitList.forEach(u ->
                when(trainingClassUnitInformationRepository
                        .findByUnitAndTrainingClassAndStatus(u, tc1, true))
                        .thenReturn(Optional.of(u.getListTrainingClassUnitInformations().get(0))));

        informationList.forEach(i ->
                when(towerRepository.findByIdAndStatus(i.getTower().getId(), true))
                        .thenReturn(Optional.of(informationList
                                .get(informationList.indexOf(i)).getTower())));

        List<TowerDTO> towers = towerService.getTowerForTheDayByTrainingClassId(1L, 1);

        assertEquals(2, towers.size());
        assertEquals(1L, towers.get(0).getId());
        assertEquals("FTown 1", towers.get(0).getTowerName());
        assertEquals("123 Le Loi", towers.get(0).getAddress());
        assertEquals("FTown 3", towers.get(1).getTowerName());
        assertEquals("999 Ba Trieu", towers.get(1).getAddress());
        assertTrue(towers.stream().filter(t -> !t.isStatus()).toList().isEmpty());

        verify(trainingClassRepository).findByIdAndStatus(1L, true);
        verify(unitService).getListUnitsInASessionByTrainingClassId(1L, 1);
        unitList.forEach(u -> verify(trainingClassUnitInformationRepository)
                .findByUnitAndTrainingClassAndStatus(u, tc1, true));
        informationList.forEach(i ->
                verify(towerRepository).findByIdAndStatus(i.getTower().getId(), true));
    }

    @Test
    void itShouldThrowExceptionWhenNotFoundTrainingClassTowerForTheDate() {
        //Create Unit
        Unit u1 = new Unit();
        u1.setStatus(false);

        //Create list unit
        List<Unit> unitList = new ArrayList<>(List.of(u1));

        //Create training class unit information
        TrainingClassUnitInformation tr1 = new TrainingClassUnitInformation();
        tr1.setTrainingClass(tc1);
        tr1.setTower(tower1);
        tr1.setStatus(true);

        TrainingClassUnitInformation tr2 = new TrainingClassUnitInformation();
        tr2.setTrainingClass(tc1);
        tr2.setTower(tower3);
        tr2.setStatus(true);

        //Case 1: Not found any training class
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> towerService.getTowerForTheDayByTrainingClassId(1L, 1));


        //Case 2: Not found any training class unit
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.of(tc1));
        when(unitService.getListUnitsInASessionByTrainingClassId(1L, 1))
                .thenReturn(unitList);
        when(trainingClassUnitInformationRepository.findByUnitAndTrainingClassAndStatus(u1, tc1, true))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> towerService.getTowerForTheDayByTrainingClassId(1L, 1));

        verify(trainingClassRepository, times(2)).findByIdAndStatus(1L, true);
        verify(unitService).getListUnitsInASessionByTrainingClassId(1L, 1);
        verify(trainingClassUnitInformationRepository).findByUnitAndTrainingClassAndStatus(u1, tc1, true);
    }
}

