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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository syllabusRepo;

    private final OutputStandardRepository outputStandardRepo;

    private final UnitDetailRepository unitDetailRepo;

    private final SyllabusRepository syllabusRepository;

    private final ISessionService sessionService;

    private final TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;

    private static final int RESULTS_PER_PAGE = 10;

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
    public List<SyllabusDTO> getAll(boolean state, boolean status) {
        Optional<List<Syllabus>> syllabusList = syllabusRepository.findByStateAndStatus(state, status);
        ListUtils.checkList(syllabusList);
        List<SyllabusDTO> syllabusDTOList = new ArrayList<>();
        for (Syllabus s : syllabusList.get()){
            syllabusDTOList.add(SyllabusMapper.INSTANCE.toDTO(s));
        }
        return syllabusDTOList;
    }

    @Override
    public Page<SyllabusDTO> getListSyllabus(boolean status, LocalDate fromDate, LocalDate toDate,
                                             List<String> search, String[] sort, Optional<Integer> page) {
        List<Sort.Order> order = new ArrayList<>();
        int skipCount = page.orElse(0) * RESULTS_PER_PAGE;
        Set<String> sourceFieldList = getAllFields(new Syllabus().getClass());
        if(sort[0].contains(",")){
            for (String sortItem: sort) {
                String[] subSort = sortItem.split(",");
                if(ifPropertpresent(sourceFieldList, subSort[0])){
                    order.add(new Sort.Order(getSortDirection(subSort[1]), transferProperty(subSort[0])));
                } else {
                    throw new NotFoundException(subSort[0] + " is not a propertied of Syllabus!");
                }
            }
        } else {
            if(sort.length == 1){
                throw new ArrayIndexOutOfBoundsException("Sort direction(asc/desc) not found!");
            }
            if(ifPropertpresent(sourceFieldList, sort[0])){
                order.add(new Sort.Order(getSortDirection(sort[1]), transferProperty(sort[0])));
            } else {
                throw new NotFoundException(sort[0] + " is not a propertied of Syllabus!");
            }
        }
        List<Syllabus> pages = syllabusRepo.getListSyllabus(status, fromDate, toDate, search.size() > 0 ? search.get(0) : "", getListSyllabusIdByOSD(search.size() > 0 ? search.get(0) : ""), Sort.by(order));
        if (search.size() > 1){
            for (int i = 1; i < search.size(); i++) {
                String subSearch = search.get(i).toUpperCase();
                pages = pages.stream().filter(s
                        -> s.getName().toUpperCase().contains(subSearch) ||
                                s.getCode().toUpperCase().contains(subSearch) ||
                                s.getCreator().getFullName().toUpperCase().contains(subSearch) ||
                                checkOsdBelongSyllabus(s.getId(), subSearch))
                        .collect(Collectors.toList());
            }
        }
        if(pages.size() > 0){
            return new PageImpl<>(
                    pages.stream().skip(skipCount).limit(RESULTS_PER_PAGE).map(SyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                    PageRequest.of(page.orElse(0), RESULTS_PER_PAGE, Sort.by(order)),
                    pages.size());
        } else {
            throw new NotFoundException("Syllabus not found!");
        }
    }

    private static String transferProperty(String property){
        switch (property) {
            case "creator":
                return "creator.fullName";
            default:
                return property;
        }
    }

    private boolean checkOsdBelongSyllabus(long syllabusId, String search) {
            if (getListSyllabusIdByOSD(search).contains(syllabusId)) {
                return true;
            }
        return false;
    }

    private static boolean ifPropertpresent(final Set<String> properties, final String propertyName) {
        if (properties.contains(propertyName)) {
            return true;
        }
        return false;
    }

    private static Set<String> getAllFields(final Class<?> type) {
        Set<String> fields = new HashSet<>();
        //loop the fields using Java Reflections
        for (Field field : type.getDeclaredFields()) {
            fields.add(field.getName());
        }
        //recursive call to getAllFields
        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
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
    public List<SyllabusDTO> getSyllabusList(boolean status){
        Optional<List<Syllabus>> syllabusList = syllabusRepository.findAllByStatus(status);
        ListUtils.checkList(syllabusList);
        List<SyllabusDTO> syllabusDTOList = new ArrayList<>();

        for (Syllabus s: syllabusList.get()) {
            syllabusDTOList.add(SyllabusMapper.INSTANCE.toDTO(s));
        }
        return syllabusDTOList;
    }

    @Override
    public List<Long> getListSyllabusIdByOSD(String osd) {
        List<UnitDetail> detailList = unitDetailRepo.findByStatusAndOutputStandardIn(true, outputStandardRepo.findByStatusAndStandardCodeContainingIgnoreCase(true, osd));
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
