package com.mockproject.service;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.dto.mapper.UnitDetailDTOMapper;
import com.mockproject.entity.UnitDetail;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UnitDetailService implements IUnitDetailService {
    private final UnitDetailRepository repository;

    private final UnitDetailDTOMapper mapper;

    @Override
    public UnitDetail get(long id){
        return repository.getReferenceById(id);
    }
}
