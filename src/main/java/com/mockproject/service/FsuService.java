package com.mockproject.service;

import com.mockproject.dto.FsuDTO;
import com.mockproject.entity.Fsu;
import com.mockproject.mapper.FsuMapper;
import com.mockproject.repository.FsuRepository;
import com.mockproject.service.interfaces.IFsuService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FsuService implements IFsuService {

    private final FsuRepository fsuRepo;

    @Override
    public FsuDTO getFsuById(long id) {
        return FsuMapper.INSTANCE.toDTO(fsuRepo.findById(id).orElse(new Fsu()));
    }

    @Override
    public List<FsuDTO> getAllFsu() {
        return fsuRepo.findAll().stream().map(FsuMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
