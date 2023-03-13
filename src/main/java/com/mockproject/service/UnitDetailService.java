package com.mockproject.service;

import com.mockproject.dto.UnitDTO;
import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;

import com.mockproject.mapper.UnitDetailMapper;
import com.mockproject.mapper.UnitMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.IUnitDetailService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UnitDetailService implements IUnitDetailService {
    private final UnitDetailRepository unitDetailRepository;
    private final TrainingMaterialService trainingMaterialService;
    private final UnitRepository unitRepository;
    private final SyllabusRepository syllabusRepository;
    private final SessionRepository sessionRepository;

    public UnitDetailService(UnitDetailRepository unitDetailRepository, TrainingMaterialService trainingMaterialService, UnitRepository unitRepository, SyllabusRepository syllabusRepository, SessionRepository sessionRepository) {
        this.unitDetailRepository = unitDetailRepository;
        this.trainingMaterialService = trainingMaterialService;
        this.unitRepository = unitRepository;
        this.syllabusRepository = syllabusRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<UnitDetail> getAllUnitDetailByUnitId(long unitId, boolean status) {
        Optional<List<UnitDetail>> unitDetails = unitDetailRepository.findByUnitIdAndStatus(unitId, status);
        unitDetails.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        return unitDetails.get();
    }

    public boolean createUnitDetail(long unitId, List<UnitDetailDTO> listUnitDetail, long userId){
        Optional<Unit> unit = unitRepository.findByIdAndStatus(unitId, true);
        unit.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        BigDecimal duration = BigDecimal.valueOf(0);
        for (UnitDetailDTO i: listUnitDetail) {
            i.setUnitId(unitId);
            duration = duration.add(i.getDuration());
            UnitDetail unitDetail = unitDetailRepository.save(UnitDetailMapper.INSTANCE.toEntity(i));
            System.out.println("Unit detail :"+ unitDetail.getId());
//            trainingMaterialService.uploadFile(unitDetail.getListMaterials(),unitDetail.getId(), userId);
        }

        //Set duration
        long sessionId = unitRepository.findByIdAndStatus(unitId, true).get().getSession().getId();
        long syllabusId = sessionRepository.findByIdAndStatus(sessionId, true).get().getSyllabus().getId();
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(syllabusId,true);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        syllabus.get().setHour(duration);
        syllabusRepository.save(syllabus.get());
        return true;
    }



    public UnitDetail getUnitDetailById(long id, boolean status){
        Optional<UnitDetail> unitDetail = unitDetailRepository.findByIdAndStatus(id, status);
        unitDetail.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        return unitDetail.get();
    }

    public UnitDetail editUnitDetail(long id, UnitDetailDTO unitDetailDTO, boolean status){
        Optional<UnitDetail> unitDetail = unitDetailRepository.findByIdAndStatus(id, status);
        unitDetail.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        unitDetailDTO.setId(id);
        unitDetailDTO.setUnitId(unitDetail.get().getUnit().getId());
        UnitDetail updateUnitDetail = unitDetailRepository.save(UnitDetailMapper.INSTANCE.toEntity(unitDetailDTO));
        return updateUnitDetail;
    }

    public boolean deleteUnitDetail(long unitDetailId, boolean status){
        Optional<UnitDetail> unitDetail = unitDetailRepository.findByIdAndStatus(unitDetailId, status);
        unitDetail.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "unitDetail "+ unitDetailId));
        unitDetail.get().setStatus(false);
        System.out.println("unitDetail: " +unitDetailId);
        trainingMaterialService.deleteTrainingMaterials(unitDetailId,status);
        unitDetailRepository.save(unitDetail.get());
        return true;
    }

    public boolean deleteUnitDetails(long unitId, boolean status){
        Optional<List<UnitDetail>> unitDetails = unitDetailRepository.findByUnitIdAndStatus(unitId, status);
        ListUtils.checkList(unitDetails);
        unitDetails.get().forEach((i) -> deleteUnitDetail(i.getId(), status));
        return true;
    }

    public boolean toggleUnitDetailType(long unitDetailId, boolean status){
        Optional<UnitDetail> unitDetail = unitDetailRepository.findByIdAndStatus(unitDetailId, status);
        unitDetail.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        unitDetail.get().setType(unitDetail.get().isType() == true ? false: true);
        return true;
    }

    public void getSessionBySessionIdAndStatus(long sessionId, boolean status) {
    }
}
