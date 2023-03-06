package com.mockproject.service;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.dto.mapper.TrainingMaterialDTOMapper;
import com.mockproject.entity.TrainingMaterial;
import com.mockproject.repository.TrainingMaterialRepository;
import com.mockproject.service.interfaces.ITrainingMaterialService;
import com.mockproject.service.interfaces.IUnitDetailService;
import com.mockproject.service.interfaces.IUserService;
import com.mockproject.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.zip.DataFormatException;

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
        if(file.getSize() > 40*1024*1024)
            throw new RuntimeException("The maximum file size is 40MB");
        TrainingMaterial trainingMaterial = repository.save(TrainingMaterial.builder()
                .uploadDate(LocalDate.now())
                .data(FileUtils.compressFile(file.getBytes()))
                .name(file.getName())
                .type(file.getContentType())
                .size(new BigDecimal(file.getSize()))
                .status(true)
                .unitDetail(unitDetailService.get(unitDetailId))
                .user(userService.get(userId))
                .build());
        return mapper.apply(trainingMaterial);
    }

    @Override
    public TrainingMaterialDTO getFile(long id) throws DataFormatException, IOException {
        TrainingMaterial trainingMaterial = repository.getReferenceById(id);
        trainingMaterial.setData(FileUtils.decompressFile(trainingMaterial.getData()));
        return mapper.apply(trainingMaterial);
    }
}
