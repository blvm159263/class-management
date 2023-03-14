package com.mockproject.service;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.entity.TrainingMaterial;
import com.mockproject.entity.UnitDetail;
import com.mockproject.entity.User;
import com.mockproject.mapper.TrainingMaterialMapper;
import com.mockproject.repository.TrainingMaterialRepository;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@Transactional
@AllArgsConstructor
public class TrainingMaterialService{

    private final TrainingMaterialRepository trainingMaterialRepository;

    private final UnitDetailService unitDetailService;
    @Autowired
    public TrainingMaterialService(@Lazy  UnitDetailService unitDetailService, TrainingMaterialRepository trainingMaterialRepository, UserService userService){
        this.unitDetailService = unitDetailService;
        this.trainingMaterialRepository = trainingMaterialRepository;
        this.userService = userService;
    }
    private final UserService userService;

    public TrainingMaterialDTO uploadAFile(TrainingMaterialDTO createDTO,UnitDetail unitDetail, User user) throws IOException {
        TrainingMaterial trainingMaterial = trainingMaterialRepository.save(TrainingMaterial.builder()
                .uploadDate(LocalDate.now())
                .data(createDTO.getData())
                .name(createDTO.getName())
                .type(createDTO.getType())
                .size(createDTO.getSize())
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
        return trainingMaterialDTO;
    }


    public List<TrainingMaterialDTO> uploadFile(List<TrainingMaterialDTO> createTrainingMaterialDTOList, User user, long unitDetailID) {
        UnitDetail unitDetail = unitDetailService.getUnitDetailById(unitDetailID, true);
        List<TrainingMaterialDTO> trainingMaterialDTOS = new ArrayList<>();
        createTrainingMaterialDTOList.forEach(
                (dto) -> {
                    try {
                        trainingMaterialDTOS.add(uploadAFile(dto, unitDetail, user));
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
                    }
                }
        );
        return trainingMaterialDTOS;
    }

    public TrainingMaterialDTO updateFile(long id, TrainingMaterialDTO createDTO, User user, boolean status) throws IOException {
        Optional<TrainingMaterial> trainingMaterial = trainingMaterialRepository.findByIdAndStatus(id, status);
        trainingMaterial.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        return TrainingMaterialMapper.INSTANCE.toDTO(trainingMaterialRepository.save(TrainingMaterial.builder()
                .id(id)
                .data(createDTO.getData())
                .name(createDTO.getName())
                .type(createDTO.getType())
                .size(createDTO.getSize())
                .uploadDate(LocalDate.now())
                .unitDetail(unitDetailService.getUnitDetailById(trainingMaterial.get().getUnitDetail().getId(), true))
                .user(user)
                .status(createDTO.isStatus())
                .build()));
    }

    public List<TrainingMaterialDTO> getFiles(long unitDetailId, boolean status){
        List<TrainingMaterialDTO> trainingMaterialDTOS = new ArrayList<>();
        Optional<List<TrainingMaterial>> trainingMaterials = trainingMaterialRepository.findAllByUnitDetailIdAndStatus(unitDetailId, status);
        ListUtils.checkList(trainingMaterials);
        trainingMaterials.get().forEach(trainingMaterial -> {
            try {
                TrainingMaterialDTO trainingMaterialDTO = TrainingMaterialMapper.INSTANCE.toDTO(trainingMaterial);
                trainingMaterialDTOS.add(trainingMaterialDTO);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
        });
        return trainingMaterialDTOS;
    }


    public boolean deleteTrainingMaterial(long trainingMaterialId, boolean status){
        try {
            Optional<TrainingMaterial> trainingMaterial = trainingMaterialRepository.findByIdAndStatus(trainingMaterialId, status);
            trainingMaterial.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Training "+ trainingMaterialId));
            trainingMaterial.get().setStatus(false);

            trainingMaterialRepository.save(trainingMaterial.get());
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteTrainingMaterials(long unitDetailId, boolean status){
        Optional<List<TrainingMaterial>> listTrainingMaterial = trainingMaterialRepository.findAllByUnitDetailIdAndStatus(unitDetailId, status);
        listTrainingMaterial.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        listTrainingMaterial.get().forEach((i) -> deleteTrainingMaterial(i.getId(), status));
        return true;
    }
}
