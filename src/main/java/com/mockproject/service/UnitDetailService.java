package com.mockproject.service;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.CustomUserDetails;
import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;
import com.mockproject.entity.User;
import com.mockproject.mapper.UnitDetailMapper;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.ITrainingMaterialService;
import com.mockproject.service.interfaces.IUnitDetailService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitDetailService implements IUnitDetailService {

    private final UnitRepository unitRepository;

    private final UnitDetailRepository unitDetailRepository;

    private final ITrainingMaterialService trainingMaterialService;

    @Override
    public List<UnitDetailDTO> getAllUnitDetailByUnitId(Long unitId, boolean status) {
        Optional<List<UnitDetail>> unitDetails = unitDetailRepository.findByUnitIdAndStatus(unitId, status);
        unitDetails.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        List<UnitDetailDTO> unitDetailDTOList = new ArrayList<>();

        for (UnitDetail u: unitDetails.get()){
            unitDetailDTOList.add(UnitDetailMapper.INSTANCE.toDTO(u));
        }

        for (UnitDetailDTO u: unitDetailDTOList){
            List<TrainingMaterialDTO> trainingMaterialDTOList = trainingMaterialService.getFiles(u.getId(), true);
            u.setTrainingMaterialDTOList(trainingMaterialDTOList);
        }
        return unitDetailDTOList;
    }

    @Override
    public boolean createUnitDetail(Long unitId, List<UnitDetailDTO> listUnitDetail, User user){
        Optional<Unit> unit = unitRepository.findByIdAndStatus(unitId, true);
        unit.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        for (UnitDetailDTO i: listUnitDetail) {
            createUnitDetail(unitId, i, user);
        }
        return true;
    }

    @Override
    public boolean createUnitDetail(Long unitId, UnitDetailDTO unitDetailDTO, User user){
        Optional<Unit> unit = unitRepository.findByIdAndStatus(unitId, true);
        unit.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        BigDecimal duration = unit.get().getDuration();

        unitDetailDTO.setUnitId(unitId);
        duration = duration.add(unitDetailDTO.getDuration().divide(BigDecimal.valueOf(60)));
        UnitDetail unitDetail = unitDetailRepository.save(UnitDetailMapper.INSTANCE.toEntity(unitDetailDTO));
        trainingMaterialService.uploadFile(unitDetailDTO.getTrainingMaterialDTOList(), user, unitDetail.getId());

        //Set duration unit
        unit.get().setDuration(duration);
        unitRepository.save(unit.get());
        return true;
    }

    @Override
    public UnitDetail getUnitDetailById(Long id, boolean status){
        Optional<UnitDetail> unitDetail = unitDetailRepository.findByIdAndStatus(id, status);
        unitDetail.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        return unitDetail.get();
    }

    @Override
    public UnitDetail editUnitDetail(UnitDetailDTO unitDetailDTO, boolean status) throws IOException {
        Optional<UnitDetail> unitDetail = unitDetailRepository.findByIdAndStatus(unitDetailDTO.getId(), status);
        unitDetail.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        unitDetailDTO.setUnitId(unitDetail.get().getUnit().getId());

        Optional<Unit> unit = unitRepository.findByIdAndStatus(unitDetailDTO.getUnitId(), true);
        BigDecimal duration = unit.get().getDuration();

        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        duration = duration.add(unitDetailDTO.getDuration().divide(BigDecimal.valueOf(60)));
        if(unitDetailDTO.isStatus() == true){
            for (TrainingMaterialDTO t: unitDetailDTO.getTrainingMaterialDTOList()){
                if(t.getId() == null)
                    trainingMaterialService.uploadAFile(t, unitDetail.get(), user.getUser());
                else {
                    trainingMaterialService.updateFile(t.getId(),t, user.getUser(),true);
                }
            }
        }else {
            trainingMaterialService.deleteTrainingMaterials(unitDetailDTO.getId(),true);
        }

        UnitDetail updateUnitDetail = unitDetailRepository.save(UnitDetailMapper.INSTANCE.toEntity(unitDetailDTO));

        unit.get().setDuration(duration);
        unitRepository.save(unit.get());
        return updateUnitDetail;
    }

    @Override
    public boolean deleteUnitDetail(Long unitDetailId, boolean status){
        Optional<UnitDetail> unitDetail = unitDetailRepository.findByIdAndStatus(unitDetailId, status);
        unitDetail.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "unitDetail "+ unitDetailId));
        unitDetail.get().setStatus(false);
        System.out.println("unitDetail: " +unitDetailId);
        trainingMaterialService.deleteTrainingMaterials(unitDetailId,status);
        unitDetailRepository.save(unitDetail.get());
        return true;
    }

    @Override
    public boolean deleteUnitDetails(Long unitId, boolean status){
        Optional<List<UnitDetail>> unitDetails = unitDetailRepository.findByUnitIdAndStatus(unitId, status);
        ListUtils.checkList(unitDetails);
        unitDetails.get().forEach((i) -> deleteUnitDetail(i.getId(), status));
        return true;
    }

    @Override
    public boolean toggleUnitDetailType(Long unitDetailId, boolean status){
        Optional<UnitDetail> unitDetail = unitDetailRepository.findByIdAndStatus(unitDetailId, status);
        unitDetail.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        unitDetail.get().setType(unitDetail.get().isType() == true ? false: true);
        return true;
    }

    @Override
    public List<UnitDetail> getUnitDetailByUnitId(Long idUnit) {
        return unitDetailRepository.getListUnitDetailByUnitId(idUnit);
    }

    @Override
    public List<UnitDetailDTO> listByUnitIdTrue(Long id) {
        Unit unit = new Unit();
        unit.setId(id);
        return unitDetailRepository.findByUnitAndStatus(unit,true).stream().map(UnitDetailMapper.INSTANCE::toDTO).toList();
    }
}
