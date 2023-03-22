package com.mockproject.service;

import com.mockproject.dto.UnitDTO;
import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.UnitDetail;
import com.mockproject.mapper.UnitDetailMapper;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitDetailService implements IUnitDetailService {
    private final UnitDetailRepository unitDetailRepository;

    @Override
    public List<UnitDetailDTO> getListUnitDetail(List<UnitDTO> listUnit){
        List<UnitDetailDTO> listUnitDetail = new ArrayList<>();
        for(UnitDTO u: listUnit){
            listUnitDetail.addAll(getUnitDetailByUnitId(u.getId()));
        }
        return listUnitDetail;
    }
    public List<UnitDetailDTO> getUnitDetailByUnitId(long idUnit) {
        return unitDetailRepository.getListUnitDetailByUnitId(idUnit).stream().map(UnitDetailMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
