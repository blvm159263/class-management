package com.mockproject.service;

import com.mockproject.dto.FsuDTO;
import com.mockproject.mapper.FsuMapper;
import com.mockproject.repository.FsuRepository;
import com.mockproject.service.interfaces.IFsuService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FsuService implements IFsuService {

    private final FsuRepository repository;

    @Override
    public List<FsuDTO> listAllTrue() {
        return repository.findByStatus(true).stream().map(FsuMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
    @Override
    public FsuDTO getFsuById(boolean status, long id) {
        Fsu fsu = fsuRepo.findByStatusAndId(status, id).orElseThrow(() -> new NotFoundException("Fsu not found with id: "+ id));
        return FsuMapper.INSTANCE.toDTO(fsu);
    }

    @Override
    public List<FsuDTO> getAllFsu(boolean status) {
        return fsuRepo.findAllByStatus(status).stream().map(FsuMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
