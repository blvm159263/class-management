package com.mockproject.service;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.entity.*;
import com.mockproject.repository.DeliveryTypeRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.UnitDetailRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DeliveryTypeService.class})
@ExtendWith(SpringExtension.class)
class DeliveryTypeServiceTest {
    @MockBean
    private DeliveryTypeRepository deliveryTypeRepository;

    @MockBean
    private UnitDetailRepository unitDetailRepository;

    @MockBean
    private IUnitService iUnitService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    //Create Delivery Type
    DeliveryType deliveryType1 = new DeliveryType(1L, "On Meeting", true, null);
    DeliveryType deliveryType2 = new DeliveryType(2L, "Offline", true, null);
    DeliveryType deliveryType3 = new DeliveryType(3L, "Practice", true, null);

    //Create Training Class
    TrainingClass tc1 = new TrainingClass(1L, "Class Name 1", "TC1", null, null,
            null, null, 12, 30, 30, 25, "Planning", null,
            null, null, null, 1, true, null, null,
            null, null, null, null, null, null, null,
            null, null, null);

    //Create Unit
    Unit unit1 = new Unit(1L, "Unit title 123", 1, BigDecimal.TEN, true, null,
            null, null);
    Unit unit2 = new Unit(2L, "Unit title 234", 2, BigDecimal.TEN, true, null,
            null, null);

    //Create Unit Details
    UnitDetail unitDetails1 = new UnitDetail(1L, "Unit details title 1",  BigDecimal.TEN, true, true,
            unit1, deliveryType1, null, null);
    UnitDetail unitDetails2 = new UnitDetail(2L, "Unit details title 2",  BigDecimal.TEN, true, true,
            unit2, deliveryType2, null, null);
    UnitDetail unitDetails3 = new UnitDetail(3L, "Unit details title 3",  BigDecimal.TEN, true, true,
            unit1, deliveryType1, null, null);
    UnitDetail unitDetails4 = new UnitDetail(4L, "Unit details title 4",  BigDecimal.TEN, true, true,
            unit2, deliveryType3, null, null);


    /**
     * Method under test: {@link DeliveryTypeService#getByIdTrue(Long)}
     */
//    @Test
//    @Disabled
//    void canGetDeliveryTypeByIdTrue() {
//
//        Long id = 3L;
//
//        when(deliveryTypeRepository.findByIdAndStatus(id, true).orElseThrow()).thenReturn(deliveryType3);
//        DeliveryTypeDTO result = deliveryTypeService.getByIdTrue(3L);
//        assertEquals(3L, result.getId());
//        assertTrue(result.isStatus());
//        assertEquals("Practice", result.getTypeName());
//        verify(deliveryTypeRepository).findByIdAndStatus(3L, true);
//    }

    /**
     * Method under test: {@link DeliveryTypeService#getAllDeliveryTypesByTrainingClassId(Long)}
     */
    @Test
    void canGetAllDeliveryTypesByTrainingClassId() {

        //Create list unit
        List<Unit> unitList = new ArrayList<>();
        unitList.add(unit1);
        unitList.add(unit2);

        //Create list unit detail
        List<UnitDetail> unitDetailList = new ArrayList<>();
        unitDetailList.add(unitDetails1);
        unitDetailList.add(unitDetails2);
        unitDetailList.add(unitDetails3);
        unitDetailList.add(unitDetails4);

        unitList.stream().filter(Unit::isStatus).toList();
        when(iUnitService.getListUnitsByTrainingClassId(1L))
                .thenReturn(unitList);

        unitDetailList.stream().filter(UnitDetail::isStatus).toList();
        when(unitDetailRepository.findByUnitInAndStatus(unitList,true))
                .thenReturn(Optional.of(unitDetailList));
        unitDetailList.forEach(p ->
                when(deliveryTypeRepository
                .findByIdAndStatus(p.getDeliveryType().getId(), true))
                .thenReturn(Optional.of(unitDetailList.get(unitDetailList.indexOf(p)).getDeliveryType())));

        List<DeliveryTypeDTO> deliveryType = deliveryTypeService.getAllDeliveryTypesByTrainingClassId(1L);

        assertEquals(3, deliveryType.size());
        assertEquals(1L, deliveryType.get(0).getId());
        assertEquals("On Meeting", deliveryType.get(0).getTypeName());
        assertTrue(deliveryType.stream().filter(p -> !p.isStatus()).toList().isEmpty());

        verify(iUnitService).getListUnitsByTrainingClassId(1L);
        verify(unitDetailRepository).findByUnitInAndStatus(unitList, true);
        unitDetailList.forEach(p ->
                verify(deliveryTypeRepository, atLeastOnce())
                        .findByIdAndStatus(p.getDeliveryType().getId(), true));
    }

    @Test
    void itShouldThrowExceptionWhenTrainingClassDeliveryNotFound() {
        //Create list unit
        List<Unit> unitList = new ArrayList<>();
        unitList.add(unit1);
        unitList.add(unit2);

        //Create unit detail
        UnitDetail unitDetail = new UnitDetail();
        unitDetail.setUnit(unit1);
        unitDetail.setDeliveryType(deliveryType1);
        unitDetail.setStatus(false);

        //Case 1: Cannot find any unit by the follow training class Id
        when(iUnitService.getListUnitsByTrainingClassId(1L))
                .thenReturn(null);
        when(unitDetailRepository.findByUnitInAndStatus(null, true))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> deliveryTypeService.getAllDeliveryTypesByTrainingClassId(1L));

        verify(iUnitService).getListUnitsByTrainingClassId(1L);
        verify(unitDetailRepository).findByUnitInAndStatus(null, true);

        //Case 2: Can find units by the follow training class Id, but cannot find delivery type with UnitDetail ID
        // or this unit detail is set to false
        when(iUnitService.getListUnitsByTrainingClassId(1L))
                .thenReturn(unitList);
        when(unitDetailRepository.findByUnitInAndStatus(unitList, true))
                .thenReturn(Optional.of(new ArrayList<>(List.of(unitDetail))));
        when(deliveryTypeRepository
                .findByIdAndStatus(unitDetail.getDeliveryType().getId(), true))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> deliveryTypeService.getAllDeliveryTypesByTrainingClassId(1L));

        verify(iUnitService, atLeastOnce()).getListUnitsByTrainingClassId(1L);
        verify(unitDetailRepository).findByUnitInAndStatus(unitList, true);
        verify(deliveryTypeRepository).findByIdAndStatus(unitDetail.getDeliveryType().getId(), true);
    }
}

