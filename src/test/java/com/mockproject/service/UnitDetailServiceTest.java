package com.mockproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.DeliveryType;
import com.mockproject.entity.OutputStandard;
import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;
import com.mockproject.repository.UnitDetailRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UnitDetailService.class})
@ExtendWith(SpringExtension.class)
class UnitDetailServiceTest {


    @MockBean
    private UnitDetailRepository unitDetailRepository;

    @Autowired
    private UnitDetailService unitDetailService;

    Unit unit1 = new Unit(1L, "Unit title 123", 1, BigDecimal.TEN, true, null, null, null);
    Unit unit2 = new Unit(2L, "Unit title 234", 2, BigDecimal.TEN, true, null, null, null);

    UnitDetail unitDetail1 = new UnitDetail(1L, "Unit Detail 1", BigDecimal.TEN, true,
            true, unit1, new DeliveryType(), new OutputStandard(), null);
    UnitDetail unitDetail2 = new UnitDetail(2L, "Unit Detail 2", BigDecimal.TEN, true,
            false, unit1, new DeliveryType(), new OutputStandard(), null);
    UnitDetail unitDetail3 = new UnitDetail(3L, "Unit Detail 3", BigDecimal.TEN, true,
            true, unit1, new DeliveryType(), new OutputStandard(), null);
    UnitDetail unitDetail4 = new UnitDetail(4L, "Unit Detail 4", BigDecimal.TEN, true,
            true, unit2, new DeliveryType(), new OutputStandard(), null);

    /**
     * Method under test: {@link UnitDetailService#listByUnitIdTrue(long)}
     */
    @Test
    void canListUnitDetailWithStatusTrueByUnitId() {
        List<UnitDetail> list = new ArrayList<>();
        list.add(unitDetail1);
        list.add(unitDetail2);
        list.add(unitDetail3);
        list.add(unitDetail4);

        Unit unit = new Unit();
        unit.setId(1L);

        when(unitDetailRepository.findByUnitAndStatus(unit, true)).thenReturn(list.stream()
                .filter(p -> p.isStatus() && p.getUnit().getId() == unit.getId()).toList());

        List<UnitDetailDTO> result = unitDetailService.listByUnitIdTrue(unit.getId());

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Unit Detail 1",result.get(0).getTitle());
        assertEquals(3L, result.get(1).getId());
        assertEquals("Unit Detail 3",result.get(1).getTitle());

        verify(unitDetailRepository).findByUnitAndStatus((Unit) any(), anyBoolean());
    }
}

