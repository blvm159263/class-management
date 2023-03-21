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
import com.mockproject.utils.FileUtils;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SyllabusService implements ISyllabusService {
    private final OutputStandardRepository outputStandardRepo;

    private final UnitDetailRepository unitDetailRepo;

    private final SyllabusRepository syllabusRepository;

    private final ISessionService sessionService;

    private final TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;
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

    private boolean checkOsdBeLongSyllabus(Long syllabusId, String search) {
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
    public boolean replace(SyllabusDTO syllabusDTO, boolean status){
        sessionService.deleteSessions(syllabusDTO.getId(), true);

        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        syllabusRepository.save(SyllabusMapper.INSTANCE.toEntity(syllabusDTO));

        sessionService.createSession(syllabusDTO.getId(), syllabusDTO.getSessionDTOList(), user.getUser());

        return true;
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
    private static final String TEMPLATE_FILE_PATH = "file-format\\syllabus-template.csv";
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
    public Syllabus getSyllabusById(Long id) {
        return syllabusRepository.findByIdAndStatus(id, true).get();
    }

    @Override
    public SyllabusDTO readFileCsv(MultipartFile file, int condition, int handle) throws IOException {

        final String[] HEADERS = {"Syllabus Name","Syllabus Code","Syllabus Version","Syllabus Level","Attendee Amount","Technical Requirements","Course Objectives","Quiz","Assignment","Final","Final Theory","Final Practice","GPA","Training Description","Retest Description","Marking Description","Waiver Criteria Description","Other Description","State","Status"};
        CSVParser parser = CSVParser.parse(file.getInputStream(), Charset.defaultCharset(), CSVFormat.DEFAULT.builder().setHeader(HEADERS).setSkipHeaderRecord(true).build());
        List<CSVRecord> records = parser.getRecords();

        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        SyllabusDTO syllabusDTO = new SyllabusDTO();
        for(CSVRecord record: records) {
            String name = record.get(0);
            if(name.isBlank())
              throw new NotFoundException("Name cannot empty");
            String code = record.get(1);
            if(code.isBlank())
                throw new NotFoundException("Code cannot empty");
            String version = record.get(2);
            String level = record.get(3);
            int attendee = Integer.parseInt(record.get(4));
            String technicalRequirements = record.get(5);
            String courseObjectives = record.get(6);
            BigDecimal quiz = new BigDecimal(Double.parseDouble(record.get(7)));
            BigDecimal assignment = new BigDecimal(Double.valueOf(record.get(8)));
            BigDecimal finalExam = new BigDecimal(Double.valueOf(record.get(9)));
            BigDecimal finalTheory = new BigDecimal(Double.valueOf(record.get(10)));
            BigDecimal finalPractice = new BigDecimal(Double.valueOf(record.get(11)));
            BigDecimal gpa = new BigDecimal(Double.parseDouble(record.get(12)));
            String trainingDes = record.get(13);
            String reTestDes = record.get(14);
            String markingDes = record.get(15);
            String waiverCriteriaDes = record.get(16);
            String otherDes = record.get(17);
            boolean status = true;
            boolean state = true;
            Syllabus syllabus = Syllabus.builder()
                    .name(name)
                    .code(code)
                    .version(version)
                    .level(level)
                    .attendee(attendee)
                    .technicalRequirements(technicalRequirements)
                    .courseObjectives(courseObjectives)
                    .dateCreated(java.time.LocalDate.now())
                    .lastDateModified(java.time.LocalDate.now())
                    .quiz(quiz)
                    .assignment(assignment)
                    .finalExam(finalExam)
                    .finalTheory(finalTheory)
                    .finalPractice(finalPractice)
                    .gpa(gpa)
                    .trainingDes(trainingDes)
                    .reTestDes(reTestDes)
                    .markingDes(markingDes)
                    .waiverCriteriaDes(waiverCriteriaDes)
                    .otherDes(otherDes)
                    .state(state)
                    .status(status)
                    .creator(user.getUser())
                    .lastModifier(user.getUser())
                    .build();
            syllabusDTO = SyllabusMapper.INSTANCE.toDTO(syllabus);
        }

        // condition Name or code
        // 1 Name
        // 2 Code
        // 3 Name and Code

        // handle Allow, Replace or Skip
        // 1 Allow
        // 2 Replace
        // 3 Skip
        if(condition == 3){
            if(handle == 1){
                return syllabusDTO;
            }
            else if (handle == 2) {
                Optional<List<Syllabus>> syllabusList = syllabusRepository.findByNameAndCodeAndStatus(syllabusDTO.getName(), syllabusDTO.getCode(),true);
                if (syllabusList.isEmpty()){
                    return syllabusDTO;
                } else {
                    Syllabus syllabus = syllabusList.get().get(syllabusList.get().size()-1);
                    syllabusDTO.setId(syllabus.getId());
                    return syllabusDTO;
                }
            }
            else if (handle == 3){
                Optional<List<Syllabus>> syllabusList = syllabusRepository.findByNameAndCodeAndStatus(syllabusDTO.getName(), syllabusDTO.getCode(),true);
                if (syllabusList.isEmpty()){
                    return syllabusDTO;
                }
                else {
                    return new SyllabusDTO();
                }
            }
        }
        else if (condition == 1){
            if(handle == 1) {
                return syllabusDTO;
            }
            else if (handle == 2) {
                Optional<List<Syllabus>> syllabusList = syllabusRepository.findByNameAndStatus(syllabusDTO.getName(), true);
                if (syllabusList.isEmpty()){
                    return syllabusDTO;
                } else {
                    Syllabus syllabus = syllabusList.get().get(syllabusList.get().size()-1);
                    syllabusDTO.setId(syllabus.getId());
                    return syllabusDTO;
                }
            }
            else if (handle == 3){
                Optional<List<Syllabus>> syllabusList = syllabusRepository.findByNameAndStatus(syllabusDTO.getName(), true);
                if (syllabusList.isEmpty()){
                    return syllabusDTO;
                }
                else {
                    return new SyllabusDTO();
                }
            }
        } else if (condition == 2){
            if(handle == 1){
                return syllabusDTO;
            }
            else if (handle == 2) {
                Optional<List<Syllabus>> syllabusList = syllabusRepository.findByCodeAndStatus(syllabusDTO.getName(), true);
                if (syllabusList.isEmpty()){
                    return syllabusDTO;
                } else {
                    Syllabus syllabus = syllabusList.get().get(syllabusList.get().size()-1);
                    syllabusDTO.setId(syllabus.getId());
                    return syllabusDTO;
                }
            }
            else if (handle == 3){
                Optional<List<Syllabus>> syllabusList = syllabusRepository.findByCodeAndStatus(syllabusDTO.getName(), true);
                if (syllabusList.isEmpty()){
                    return syllabusDTO;
                }
                else {
                    return new SyllabusDTO();
                }
            }
        }
        return syllabusDTO;
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

    public byte[] getTemplateCsvFile() throws IOException {
        byte[] bytes = FileUtils.getFileBytes(TEMPLATE_FILE_PATH);

        return bytes;
    }
}
