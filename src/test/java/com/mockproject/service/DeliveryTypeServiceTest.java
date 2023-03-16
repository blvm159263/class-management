package com.mockproject.service;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.entity.DeliveryType;
import com.mockproject.repository.DeliveryTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DeliveryTypeService.class})
@ExtendWith(SpringExtension.class)
class DeliveryTypeServiceTest {
    @MockBean
    private DeliveryTypeRepository deliveryTypeRepository;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    DeliveryType deliveryType1 = new DeliveryType(1L, "On Meeting", true, null);
    DeliveryType deliveryType2 = new DeliveryType(2L, "Offline", false, null);
    DeliveryType deliveryType3 = new DeliveryType(3L, "Practice", true, null);

    /**
     * Method under test: {@link DeliveryTypeService#getByIdTrue(Long)}
     */
    @Test
    void canGetDeliveryTypeByIdTrue() {

        Long id = 3L;

        when(deliveryTypeRepository.findByIdAndStatus(id, true)).thenReturn(deliveryType3);
        DeliveryTypeDTO result = deliveryTypeService.getByIdTrue(3L);
        assertEquals(3L, result.getId());
        assertTrue(result.isStatus());
        assertEquals("Practice", result.getTypeName());
        verify(deliveryTypeRepository).findByIdAndStatus(anyLong(), anyBoolean());
    }
}

