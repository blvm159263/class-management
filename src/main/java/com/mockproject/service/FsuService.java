package com.mockproject.service;

import com.mockproject.dto.FsuDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.FsuMapper;
import com.mockproject.repository.FsuRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.IFsuService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FsuService implements IFsuService {

    private final FsuRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    @Override
    public FsuDTO getFsuByClassCode(String code) {
        TrainingClass tc =trainingClassRepository.findByClassCodeAndStatus(code, true).get(0);
        if(tc.getFsu().isStatus()) {
            return FsuMapper.INSTANCE.toDTO(tc.getFsu());
        }
        return null;
    }
}
