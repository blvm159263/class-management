package com.mockproject.service;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.entity.TrainingMaterial;
import com.mockproject.entity.UnitDetail;
import com.mockproject.entity.User;
import com.mockproject.mapper.TrainingMaterialMapper;
import com.mockproject.mapper.TrainingMaterialMapperImpl;
import com.mockproject.repository.TrainingMaterialRepository;
import com.mockproject.service.interfaces.ITrainingMaterialService;
import com.mockproject.service.interfaces.IUnitDetailService;
import com.mockproject.service.interfaces.IUserService;
import com.mockproject.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    public TrainingMaterialDTO getFile(long id, boolean status) throws DataFormatException, IOException {
        Optional<TrainingMaterial> trainingMaterial = trainingMaterialRepository.findByIdAndStatus(id, status);
        trainingMaterial.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        TrainingMaterialDTO trainingMaterialDTO = TrainingMaterialMapper.INSTANCE.toDTO(trainingMaterial.get());
        trainingMaterialDTO.setData(FileUtils.decompressFile(trainingMaterialDTO.getData()));
        return trainingMaterialDTO;
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
                        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
                    }
                }
        );
        return trainingMaterialDTOS;
    }

    public TrainingMaterialDTO updateFile(long id, MultipartFile file, long unitDetailsId, long userId) throws IOException {
        Optional<TrainingMaterial> trainingMaterial = trainingMaterialRepository.findById(id);
        trainingMaterial.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
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

    public List<TrainingMaterialDTO> getFiles(long unitDetailId, boolean status){
        List<TrainingMaterialDTO> trainingMaterialDTOS = new ArrayList<>();
        trainingMaterialRepository.findAllByUnitDetailIdAndStatus(unitDetailId, status).get().forEach(trainingMaterial -> {
            try {
                TrainingMaterialDTO trainingMaterialDTO = TrainingMaterialMapper.INSTANCE.toDTO(trainingMaterial);

                trainingMaterialDTO.setData(FileUtils.decompressFile(trainingMaterialDTO.getData()));
                trainingMaterialDTOS.add(trainingMaterialDTO);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return trainingMaterialDTOS;
    }


    public boolean deleteTrainingMaterial(long trainingMaterialId, boolean status){
        try {
            Optional<TrainingMaterial> trainingMaterial = trainingMaterialRepository.findByIdAndStatus(trainingMaterialId, status);
            trainingMaterial.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
            trainingMaterial.get().setStatus(false);
            trainingMaterialRepository.save(trainingMaterial.get());
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteTrainingMaterials(long unitDetailId, boolean status){
        Optional<List<TrainingMaterial>> listTrainingMaterial = trainingMaterialRepository.findAllByUnitDetailIdAndStatus(unitDetailId, true);
        listTrainingMaterial.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        listTrainingMaterial.get().forEach((i) -> deleteTrainingMaterial(i.getId(), true));
        return true;
    }
}
