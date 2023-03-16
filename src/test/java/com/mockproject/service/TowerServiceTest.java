package com.mockproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mockproject.dto.TowerDTO;
import com.mockproject.entity.Location;
import com.mockproject.entity.Tower;
import com.mockproject.repository.TowerRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TowerService.class})
@ExtendWith(SpringExtension.class)
class TowerServiceTest {
    @MockBean
    private TowerRepository towerRepository;

    @Autowired
    private TowerService towerService;

    Location location1 = new Location(1L, "Location 1", "123 Le Loi", true, null, null);
    Location location2 = new Location(2L, "Location 2", "333 Le Hong Phong", false, null, null);


    Tower tower1 = new Tower(1L, "FTown 1", "123 Le Loi", true, location1,null);
    Tower tower2 = new Tower(2L, "FTown 2", "66 Nguyen Trai", false, location2,null);
    Tower tower3 = new Tower(3L, "FTown 3", "999 Ba Trieu", true, location1,null);



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
}

