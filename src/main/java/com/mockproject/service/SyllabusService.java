package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.OutputStandardRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.repository.TrainingProgramSyllabusRepository;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.ISessionService;
import com.mockproject.service.interfaces.ISyllabusService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository syllabusRepository;

    private final ISessionService sessionService;

    private final TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;

    private final UnitDetailRepository detailRepository;

    private final OutputStandardRepository outputStandardRepository;

    @Override
    public List<SyllabusDTO> listByTrainingProgramIdTrue(Long trainingProgramId) {
        TrainingProgram tp = new TrainingProgram();
        tp.setId(trainingProgramId);
        List<TrainingProgramSyllabus> listTPS = trainingProgramSyllabusRepository.findByTrainingProgramAndStatus(tp, true);
        List<Syllabus> listSyllabus = new ArrayList<>();
//        listTPS.forEach(p -> listSyllabus.add(syllabusRepository.findById(p.getSyllabus())));
//        if(listTPS.isEmpty()){
//            return null;
//        }
        listTPS.forEach(p -> listSyllabus.add(p.getSyllabus()));
        return listSyllabus.stream().map(SyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
    // List syllabus for user
    @Override
    public List<SyllabusDTO> getAll(boolean state, boolean status){
        Optional<List<Syllabus>> syllabusList = syllabusRepository.findByStateAndStatus(state, status);
        ListUtils.checkList(syllabusList);

        List<SyllabusDTO> syllabusDTOList = new ArrayList<>();
        for (Syllabus s : syllabusList.get()){
            syllabusDTOList.add(SyllabusMapper.INSTANCE.toDTO(s));
        }
        return syllabusDTOList;
    }

    // List syllabus for admin
    @Override
    public Page<SyllabusDTO> getListSyllabus(boolean status, LocalDate fromDate, LocalDate toDate,
                                             String search, String[] sort, Optional<Integer> page) {
        List<Sort.Order> order = new ArrayList<>();
        if(sort[0].contains(",")){
            for (String sortItem: sort) {
                String[] subSort = sortItem.split(",");
                order.add(new Sort.Order(getSortDirection(subSort[1]),subSort[0]));
            }
        }else {
            order.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
        }
        Pageable pageable = PageRequest.of(page.orElse(0), 10, Sort.by(order));
        Page<Syllabus> pages = syllabusRepository.getListSyllabus(status, fromDate, toDate, search, getListSyllabusIdByOSD(search), pageable);
        if(pages.getContent().size() > 0){
            return new PageImpl<>(
                    pages.stream().map(SyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                    pages.getPageable(),
                    pages.getTotalElements());
        } else {
            throw new NotFoundException("Syllabus not found!");
        }
    }

    @Override
    public List<SyllabusDTO> getSyllabusList(boolean status){
        Optional<List<Syllabus>> syllabusList = syllabusRepository.findAllByStatus(status);
        ListUtils.checkList(syllabusList);
        List<SyllabusDTO> syllabusDTOList = new ArrayList<>();

        for (Syllabus s: syllabusList.get()) {
            syllabusDTOList.add(SyllabusMapper.INSTANCE.toDTO(s));
        }
        return syllabusDTOList;
    }

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @Override
    public List<Long> getListSyllabusIdByOSD(String osd) {
        List<UnitDetail> detailList = detailRepository.findByStatusAndOutputStandardIn(true, outputStandardRepository.findByStatusAndStandardCodeContainingIgnoreCase(true, osd));
        return detailList.stream().map(ob
                -> ob.getUnit().getSession().getSyllabus().getId()).collect(Collectors.toList());
    }

    @Override
    public Long create(SyllabusDTO syllabus, User user){
        syllabus.setCreatorId(user.getId());
        syllabus.setLastModifierId(user.getId());
        syllabus.setDateCreated(java.time.LocalDate.now());
        syllabus.setLastDateModified(java.time.LocalDate.now());
        syllabus.setHour(BigDecimal.valueOf(0));
        Syllabus newSyllabus = syllabusRepository.save(SyllabusMapper.INSTANCE.toEntity(syllabus));
        sessionService.createSession(newSyllabus.getId(), syllabus.getSessionDTOList(), user);
        return newSyllabus.getId();
    }

    @Override
    public Syllabus editSyllabus(SyllabusDTO syllabusDTO, boolean status) throws IOException{
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(syllabusDTO.getId(), status);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Set day and hour
        syllabus.get().setDay(0);
        syllabus.get().setHour(BigDecimal.valueOf(0));
        syllabusRepository.save(syllabus.get());

        syllabusDTO.setLastModifierId(user.getUser().getId());
        syllabusDTO.setLastDateModified(java.time.LocalDate.now());

        if(syllabusDTO.isStatus() == true)
        {
            for (SessionDTO s : syllabusDTO.getSessionDTOList()) {
                if (s.getId() == null) {
                    sessionService.createSession(syllabusDTO.getId(), s, user.getUser());
                }else {
                    sessionService.editSession(s, true);
                }
            }
        } else {
            sessionService.deleteSessions(syllabusDTO.getId(), status);
        }
        syllabus = syllabusRepository.findByIdAndStatus(syllabusDTO.getId(), true);
        syllabusDTO.setHour(syllabus.get().getHour());
        syllabusDTO.setDay(syllabus.get().getListSessions().size());

        return syllabusRepository.save(SyllabusMapper.INSTANCE.toEntity(syllabusDTO));
    }

    @Override
    public boolean deleteSyllabus(Long syllabusId, boolean status){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(syllabusId, status);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        syllabus.get().setStatus(false);
        sessionService.deleteSessions(syllabusId, status);
        syllabusRepository.save(syllabus.get());
        return true;
    }

    @Override
    public Syllabus getSyllabusById(Long id){
        return syllabusRepository.getSyllabusById(id);
    }

    @Override
    public SyllabusDTO getSyllabusById(Long syllabusId,boolean state, boolean status){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStateAndStatus(syllabusId, state, status);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        SyllabusDTO syllabusDTO = SyllabusMapper.INSTANCE.toDTO(syllabus.get());
        List<SessionDTO> sessionDTOList = sessionService.getAllSessionBySyllabusId(syllabusId, true);
        syllabusDTO.setSessionDTOList(sessionDTOList);
        return syllabusDTO;
    }
}
