package com.mockproject.service;

import com.mockproject.dto.UnitDTO;
import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;

import com.mockproject.mapper.UnitDetailMapper;
import com.mockproject.mapper.UnitMapper;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public UnitDetail getUnitDetailById(long id, boolean status){
        return unitDetailRepository.findByIdAndStatus(id, status);
    }

    public UnitDetail editUnitDetail(long id, long unitId, UnitDetailDTO unitDetailDTO){
        Optional<UnitDetail> unitDetail = Optional.ofNullable(unitDetailRepository.findByIdAndUnitIdAndStatus(id, unitId, true));
        unitDetail.orElseThrow(() -> new RuntimeException("UnitDetail doesn't exist."));
        unitDetailDTO.setUnitId(unitId);
        unitDetailDTO.setId(id);
        UnitDetail updateUnitDetail = unitDetailRepository.save(UnitDetailMapper.INSTANCE.toEntity(unitDetailDTO));
        return updateUnitDetail;
    }

}
