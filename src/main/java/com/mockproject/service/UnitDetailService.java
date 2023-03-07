package com.mockproject.service;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.UnitDetail;

import com.mockproject.mapper.UnitDetailMapper;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UnitDetailService implements IUnitDetailService {
    private final UnitDetailRepository unitDetailRepository;

    public List<UnitDetail> getAllUnitDetailByUnitId(long unitId, boolean status) {
        return unitDetailRepository.findByUnitIdAndStatus(unitId, status);
    }

    public boolean createUnitDetail(long unitId, List<UnitDetailDTO> listUnitDetail){
        listUnitDetail.forEach((i) ->
        {
            i.setUnitId(unitId);
            unitDetailRepository.save(UnitDetailMapper.INSTANCE.toEntity(i));
        });
        return true;
    }

}
