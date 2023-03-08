package com.mockproject.service;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.entity.TrainingMaterial;
import com.mockproject.entity.UnitDetail;
import com.mockproject.entity.User;
import com.mockproject.mapper.TrainingMaterialMapper;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@Transactional
@AllArgsConstructor
public class TrainingMaterialService implements ITrainingMaterialService {
    private final TrainingMaterialRepository repository;

    private final TrainingMaterialMapper mapper;
    @Autowired
    private final IUnitDetailService unitDetailService;
    @Autowired
    private final IUserService userService;

    private TrainingMaterialDTO uploadAFile(MultipartFile file, UnitDetail unitDetail, User user) throws IOException {
        TrainingMaterial trainingMaterial = repository.save(TrainingMaterial.builder()
                .uploadDate(LocalDate.now())
                .data(FileUtils.compressFile(file.getBytes()))
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(new BigDecimal(file.getSize()))
                .status(true)
                .unitDetail(unitDetail)
                .user(user)
                .build());
        return mapper.toDTO(trainingMaterial);
    }

    @Override
    public List<TrainingMaterialDTO> uploadFile(MultipartFile[] files, long unitDetailsId, long userId) {
        User user = userService.get(userId);
        UnitDetail unitDetail = unitDetailService.get(unitDetailsId);
        List<TrainingMaterialDTO> trainingMaterialDTOS = new ArrayList<>();
        Arrays.asList(files).forEach(
            (file) -> {
            try {
                trainingMaterialDTOS.add(uploadAFile(file, unitDetail, user));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        );
        return trainingMaterialDTOS;
    }

    @Override
    public TrainingMaterialDTO getFile(long id) throws DataFormatException, IOException {
        Optional<TrainingMaterial> trainingMaterial = repository.findById(id);
        trainingMaterial.orElseThrow(() -> new RuntimeException("ID doesn't exist"));
        TrainingMaterialDTO trainingMaterialDTO = mapper.toDTO(trainingMaterial.get());
        trainingMaterialDTO.setData(FileUtils.decompressFile(trainingMaterialDTO.getData()));
        return trainingMaterialDTO;
    }

    @Override
    public TrainingMaterialDTO updateFile(long id, MultipartFile file, long unitDetailsId, long userId) throws IOException {
        Optional<TrainingMaterial> trainingMaterial = repository.findById(id);
        trainingMaterial.orElseThrow(() -> new RuntimeException("ID doesn't exist"));
        return mapper.toDTO(repository.save(TrainingMaterial.builder()
                .id(id)
                .data(FileUtils.compressFile(file.getBytes()))
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(new BigDecimal(file.getSize()))
                .uploadDate(LocalDate.now())
                .unitDetail(unitDetailService.get(unitDetailsId))
                .user(userService.get(userId))
                .build()));
    }


}
