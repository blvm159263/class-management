package com.mockproject.service;

import com.mockproject.dto.TowerDTO;
import com.mockproject.entity.Location;
import com.mockproject.entity.Tower;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    Location location1 = new Location(1L, "Location 1", "123 Le Loi", true, null, null);
    Location location2 = new Location(2L, "Location 2", "333 Le Hong Phong", false, null, null);


    Tower tower1 = new Tower(1L, "FTown 1", "123 Le Loi", true, location1,null);
    Tower tower2 = new Tower(2L, "FTown 2", "66 Nguyen Trai", false, location2,null);
    Tower tower3 = new Tower(3L, "FTown 3", "999 Ba Trieu", true, location1,null);


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

    TrainingClassUnitInformation ui1 = new TrainingClassUnitInformation(1L, true, null, null, tc1, tower1);
    TrainingClassUnitInformation ui2 = new TrainingClassUnitInformation(2L, true, null, null, tc2, tower2);


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
                .thenReturn(list.stream().filter(p-> p.isStatus() && p.getLocation().getId()==location.getId()).toList());

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
        List<Tower> towerList = new ArrayList<>();
        towerList.add(tower1);
        towerList.add(tower2);
        towerList.add(tower3);

        List<TrainingClassUnitInformation> trainingClassUnitInformations = new ArrayList<>();
        trainingClassUnitInformations.add(ui1);
        trainingClassUnitInformations.add(ui2);

        Long trainingClassId = 1L;
        TrainingClass trainingClass = new TrainingClass();
        trainingClass.setId(trainingClassId);
        trainingClass.setListTrainingClassUnitInformations(trainingClassUnitInformations);

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(trainingClass));

        List<TowerDTO> result = towerService.getAllTowersByTrainingClassId(trainingClass.getId());
        assertEquals(1L, result.get(0).getId());
        assertEquals("FTown 1", result.get(0).getTowerName());
        assertEquals("123 Le Loi", result.get(0).getAddress());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);

    }
}

