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
public class TrainingMaterialService{
    private final TrainingMaterialRepository trainingMaterialRepository;

    @Autowired
    private final UnitDetailService unitDetailService;
    @Autowired
    private final UserService userService;

    public List<TrainingMaterial> getAllTrainingMaterialByUnitDetailId(long unitDetailId, boolean status){
        List<TrainingMaterial> listTrainingMaterial = trainingMaterialRepository.findByUnitDetailIdAndStatus(unitDetailId, status);
        return listTrainingMaterial;
    }

    private TrainingMaterialDTO uploadAFile(MultipartFile file, UnitDetail unitDetail, User user) throws IOException {
        TrainingMaterial trainingMaterial = trainingMaterialRepository.save(TrainingMaterial.builder()
                .uploadDate(LocalDate.now())
                .data(FileUtils.compressFile(file.getBytes()))
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(new BigDecimal(file.getSize()))
                .status(true)
                .unitDetail(unitDetail)
                .user(user)
                .build());
        return TrainingMaterialMapper.INSTANCE.toDTO(trainingMaterial);
    }

    public TrainingMaterialDTO getFile(long id) throws DataFormatException, IOException {
        Optional<TrainingMaterial> trainingMaterial = trainingMaterialRepository.findById(id);
        trainingMaterial.orElseThrow(() -> new RuntimeException("ID doesn't exist"));
        trainingMaterial.get().setData(FileUtils.decompressFile(trainingMaterial.get().getData()));
        return TrainingMaterialMapper.INSTANCE.toDTO(trainingMaterial.get());
    }

    public List<TrainingMaterialDTO> uploadFile(MultipartFile[] files, long unitDetailsId, long userId) {
        User user = userService.getUserById(userId);
        UnitDetail unitDetail = unitDetailService.getUnitDetailById(unitDetailsId, true);
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

    public TrainingMaterialDTO updateFile(long id, MultipartFile file, long unitDetailsId, long userId) throws IOException {
        Optional<TrainingMaterial> trainingMaterial = trainingMaterialRepository.findById(id);
        trainingMaterial.orElseThrow(() -> new RuntimeException("ID doesn't exist"));
        return TrainingMaterialMapper.INSTANCE.toDTO(trainingMaterialRepository.save(TrainingMaterial.builder()
                .id(id)
                .data(FileUtils.compressFile(file.getBytes()))
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(new BigDecimal(file.getSize()))
                .uploadDate(LocalDate.now())
                .unitDetail(unitDetailService.getUnitDetailById(unitDetailsId, true))
                .user(userService.getUserById(userId))
                .build()));
    }
}
