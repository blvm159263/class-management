package com.mockproject.service;

import com.mockproject.dto.FsuDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.FsuMapper;
import com.mockproject.repository.FsuRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.IFsuService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FsuService implements IFsuService {

    private final FsuRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    @Override
    public FsuDTO getFsuByClassId(long id) {
        TrainingClass tc =trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        return FsuMapper.INSTANCE.toDTO(tc.getFsu());
    }
}
