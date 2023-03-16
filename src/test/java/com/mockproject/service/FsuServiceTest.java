package com.mockproject.service;

import com.mockproject.dto.FsuDTO;
import com.mockproject.entity.Fsu;
import com.mockproject.repository.FsuRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FsuService.class})
@ExtendWith(SpringExtension.class)
class FsuServiceTest {
    @MockBean
    private FsuRepository fsuRepository;

    @Autowired
    private FsuService fsuService;

    Fsu fsu1 = new Fsu(1L, "Fsu 1" , "Desc 1", true, null);
    Fsu fsu2 = new Fsu(2L, "Fsu 2" , "Desc 2", false, null);
    Fsu fsu3 = new Fsu(3L, "Fsu 3" , "Desc 3", true, null);

    /**
     * Method under test: {@link FsuService#listAllTrue()}
     */
    @Test
    void canListAllFsuWithStatusTrue() {
        List<Fsu> list = new ArrayList<>();
        list.add(fsu1);
        list.add(fsu2);
        list.add(fsu3);

        when(fsuRepository.findByStatus(true)).thenReturn(list.stream().filter(Fsu::isStatus).toList());

        List<FsuDTO> result = fsuService.listAllTrue();

        assertEquals(2, result.size());
        assertEquals("Fsu 1", result.get(0).getFsuName());
        assertTrue(result.stream().filter(p-> !p.isStatus()).toList().isEmpty());

        verify(fsuRepository).findByStatus(anyBoolean());
    }
}

