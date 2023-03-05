package com.mockproject.service;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.dto.mapper.TrainingMaterialDTOMapper;
import com.mockproject.entity.TrainingMaterial;
import com.mockproject.repository.TrainingMaterialRepository;
import com.mockproject.service.interfaces.ITrainingMaterialService;
import com.mockproject.service.interfaces.IUnitDetailService;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
@AllArgsConstructor
public class TrainingMaterialService implements ITrainingMaterialService {
    private final TrainingMaterialRepository repository;

    private final TrainingMaterialDTOMapper mapper;
    @Autowired
    private final IUnitDetailService unitDetailService;
    @Autowired
    private final IUserService userService;


    @Override
    public TrainingMaterialDTO uploadFile(MultipartFile file, long unitDetailId, long userId) throws IOException {
        TrainingMaterial trainingMaterial = repository.save(TrainingMaterial.builder()
                .uploadDate(LocalDate.now())
                .data(file.getBytes())
                .name(file.getName())
                .type(file.getContentType())
                .size(new BigDecimal(file.getSize()))
                .status(true)
                .unitDetail(unitDetailService.get(unitDetailId))
                .user(userService.get(userId))
                .build());
        if(trainingMaterial == null)
            throw new RuntimeException("File not found");
        return mapper.apply(trainingMaterial);
    }

    @Override
    public TrainingMaterialDTO getFile(long id){
        return mapper.apply(repository.getReferenceById(id));
    }
}
