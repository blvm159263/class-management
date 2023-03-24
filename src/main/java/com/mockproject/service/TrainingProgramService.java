package com.mockproject.service;

<<<<<<< HEAD

=======
import com.mockproject.dto.ReadFileDto;
>>>>>>> g3_branch
import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.TrainingProgramAddDto;
import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.TrainingProgramSyllabus;
import com.mockproject.exception.FileException;
import com.mockproject.exception.SyllabusException;
import com.mockproject.mapper.TrainingProgramMapper;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.IFileService;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
<<<<<<< HEAD
import java.util.List;
=======
import java.time.format.DateTimeFormatter;
import java.util.*;
>>>>>>> g3_branch
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
<<<<<<< HEAD
public class TrainingProgramService implements ITrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;

    private final SyllabusService syllabusService;

    @Override
    public List<TrainingProgram> getByName(String keyword) {
        return trainingProgramRepository.getTrainingProgramByNameContains(keyword);
=======
@Slf4j
public class TrainingProgramService implements ITrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;
    private final SyllabusService syllabusService;
    private final TrainingProgramSyllabusService trainingProgramSyllabusService;
    private final IFileService fileService;

    private final String PROGRAMID = "program id";
    private final String PROGRAMNAME = "program name";
    private final String ALLOW = "allow";
    private final String REPLACE = "replace";
    private final String SKIP = "skip";


    private int getDay(List<Syllabus> syllabusList) {
        return syllabusList.stream()
                .map(syllabus -> syllabus.getDay())
                .reduce(0, Integer::sum);
    }

    private BigDecimal getHour(List<Syllabus> syllabusList) {
        return syllabusList.stream()
                .map(syllabus -> syllabus.getHour())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<TrainingProgram> getAll() {
        return trainingProgramRepository.findAll();
>>>>>>> g3_branch
    }

    @Override
    public Page<TrainingProgramDTO> findByNameContaining(Integer pageNo, Integer pageSize, String name, String name2) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<TrainingProgram> page = trainingProgramRepository.findAllByNameContainingOrCreatorFullNameContaining(pageable, name, name2);
        Page<TrainingProgramDTO> programDTOPage = page.map(TrainingProgramMapper.INSTANCE::toDTO);
        return programDTOPage;
    }

    @Override
    public Long countAll() {
        return trainingProgramRepository.count();
    }

    @Override
    public List<TrainingProgramDTO> getByCreatorFullname(String keyword) {
        return trainingProgramRepository.getAllByCreatorFullNameContains(keyword).stream().map(TrainingProgramMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TrainingProgramDTO> searchByName(String name) {
        return trainingProgramRepository.findByNameContainingAndStatus(name, true).stream().map(TrainingProgramMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
    @Override
    public Page<TrainingProgramDTO> getAll(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<TrainingProgram> page = trainingProgramRepository.findAll(pageable);
        Page<TrainingProgramDTO> programDTOPage = page.map(TrainingProgramMapper.INSTANCE::toDTO);
        return programDTOPage;
    }

    @Override
    public TrainingProgramDTO getTrainingProgramById(Long id) {
<<<<<<< HEAD
        TrainingProgram trainingProgram = trainingProgramRepository.getTrainingProgramById(id);
        return TrainingProgramMapper.INSTANCE.toDTO(trainingProgram);
    }

    public void save(Long sylId, String name){
        SyllabusDTO syllabus = syllabusService.getSyllabusById(sylId);
        TrainingProgram trainingProgram = new TrainingProgram();
        trainingProgram.setName(name);
        trainingProgram.setDateCreated(LocalDate.now());
        trainingProgram.setLastDateModified(LocalDate.now());
        trainingProgram.setDay(syllabus.getDay());
        trainingProgram.setHour(syllabus.getHour());
        trainingProgram.setStatus(true);
        TrainingProgramSyllabus programSyllabus = new TrainingProgramSyllabus();
        programSyllabus.setTrainingProgram(trainingProgram);
        programSyllabus.setStatus(true);
//        programSyllabus.setSyllabus(syllabus);
        trainingProgramRepository.save(trainingProgram);
//        trainingProgramSyllabusService.addSyllabus(programSyllabus);
    }

=======
        return TrainingProgramMapper.INSTANCE.toDTO(trainingProgramRepository.getTrainingProgramById(id));
    }

    @Override
    public void save(TrainingProgramAddDto trainingProgramDTO, HashMap<TrainingProgram, List<Long>> trainingProgramHashMap, ReadFileDto readFileDto) {
        //add without read csv file
        if (trainingProgramHashMap == null || trainingProgramHashMap.isEmpty()) {
            // get list of syllabus
            List<Syllabus> syllabusList = syllabusService.getAllSyllabusEntityById(trainingProgramDTO.getSyllabusIdList());

            if (syllabusList.size() < trainingProgramDTO.getSyllabusIdList().size()) {
                throw new SyllabusException("One or more syllabus Ids not found", HttpStatus.BAD_REQUEST.value());
            } else {
                LocalDate nowDay = LocalDate.now();
                int day = getDay(syllabusList);
                BigDecimal hour = getHour(syllabusList);

                int lastTrainingProgramId = trainingProgramRepository.findTopByOrderByIdDesc().getId().intValue() + 1;
                //create trainingProgram
                TrainingProgram trainingProgram = TrainingProgram.builder()
                        .name(trainingProgramDTO.getName())
                        .dateCreated(nowDay)
                        .lastDateModified(nowDay)
                        .day(day)
                        .hour(hour)
                        .status(true)
                        .programId(lastTrainingProgramId)
                        .creator(null)
                        .lastModifier(null)
                        .build();
                // create programSyllabus
                List<TrainingProgramSyllabus> programSyllabus = syllabusList.stream()
                        .map(syllabus -> new TrainingProgramSyllabus(null, true, syllabus, trainingProgram))
                        .collect(Collectors.toList());
                // save to database
                trainingProgramRepository.save(trainingProgram);
                trainingProgramSyllabusService.saveAll(programSyllabus);
            }
            //add with csv file
        } else {

            List<String> scanning = readFileDto.getScanning().stream().map(String::toLowerCase).collect(Collectors.toList());

            if (!scanning.isEmpty()) {
                List<TrainingProgram> trainingProgramFilter = filterExistTrainingClassByScanning(scanning,trainingProgramHashMap);

                String dupHandle = readFileDto.getDuplicateHandle().toLowerCase();
                //check by scanning
                if (dupHandle.equals(ALLOW)) {

                } else if (dupHandle.equals(SKIP)) {
                    trainingProgramFilter.stream().forEach(trainingProgram ->
                    {
                        trainingProgramHashMap.remove(trainingProgram);
                    });
                } else if (dupHandle.equals(REPLACE)) {

                } else {
                    throw new FileException("Duplicate handle was wrong type", HttpStatus.BAD_REQUEST.value());
                }

                HashMap<TrainingProgram, List<Syllabus>> trainingProgramSyllabusHashMap = new HashMap<>();
                // find syllabus by id
                for (TrainingProgram key : trainingProgramHashMap.keySet()) {

                    List<Syllabus> syllabusList = syllabusService.getAllSyllabusEntityById(trainingProgramHashMap.get(key));

                    if (syllabusList.size() < trainingProgramHashMap.get(key).size()) {
                        throw new SyllabusException("One or more syllabus Ids not found", HttpStatus.BAD_REQUEST.value());
                    }
                    trainingProgramSyllabusHashMap.put(key, syllabusList);
                }
                // set syllabus and key to
                for (TrainingProgram key : trainingProgramSyllabusHashMap.keySet()) {
                    var syllabusList = trainingProgramSyllabusHashMap.get(key);

                    int day = getDay(syllabusList);
                    BigDecimal hour = getHour(syllabusList);

//                    int lastTrainingProgramId = trainingProgramRepository.findTopByOrderByIdDesc().getId().intValue() + 1;
                    key.setDay(day);
                    key.setHour(hour);
                    List<TrainingProgramSyllabus> programSyllabus = syllabusList.stream()
                            .map(syllabus -> new TrainingProgramSyllabus(null, true, syllabus, key))
                            .collect(Collectors.toList());

                    trainingProgramRepository.save(key);
                    trainingProgramSyllabusService.saveAll(programSyllabus);
                }
            }
        }
    }


    @Override
    public String addFromFileCsv(MultipartFile file, ReadFileDto readFileDto) {
        Long creatorId, lastModifierId;
        int programId;
        LocalDate dateCreated, lastDateModified;
        String name;
        BigDecimal hour;
        int day;
        boolean status;
        List<Long> listTrainingClassesId, listTrainingProgramSyllabusesId;

        CSVParser parser = fileService.readFile(file, readFileDto.getEncodingType(),readFileDto.getSeparator());

        // skip header of csv file
        parser.iterator().next();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        HashMap<TrainingProgram, List<Long>> trainingProgramHashMap = new HashMap<>();
        try {
            if(parser.getRecords().size()<5){
                throw new FileException("File is Wrong format", HttpStatus.BAD_REQUEST.value());
            }
            for (CSVRecord record : parser.getRecords()) {
                try {
                    dateCreated = LocalDate.parse(record.get(2), dateFormat);
                    lastDateModified = LocalDate.parse(record.get(3), dateFormat);
                } catch (DateTimeException e) {
                    throw new FileException("Date time is wrong( format: yyyy-MM-dd)", HttpStatus.BAD_REQUEST.value());
                }
                try {
                    programId = Integer.parseInt(record.get(0));
                    name = record.get(1);
                    status = Boolean.parseBoolean(record.get(4));
//                    StringTokenizer stringTokenizer = new StringTokenizer(record.get(5));
//                    stringTokenizer
                    listTrainingProgramSyllabusesId = Arrays.stream(record.get(5).split("/"))
                            .map(syllabusesId -> Long.parseLong(syllabusesId))
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    throw new FileException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
                }

                var trainingProgram = new TrainingProgram(null, programId, name, dateCreated, lastDateModified, null, 0, status, null, null, null, null);
                trainingProgramHashMap.put(trainingProgram, listTrainingProgramSyllabusesId);
            }
            save(null, trainingProgramHashMap, readFileDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "No error";
    }
    private List<TrainingProgram> filterExistTrainingClassByScanning(List<String> scanning,
                                                                     HashMap<TrainingProgram, List<Long>>trainingProgramHashMap){
        List<TrainingProgram> trainingProgramFilter = new ArrayList<>();
        if (scanning.contains(PROGRAMID)) {
            trainingProgramFilter = trainingProgramHashMap.keySet().stream()
                    .filter(trainingProgram -> trainingProgramRepository.existsByProgramId(trainingProgram.getProgramId()))
                    .collect(Collectors.toList());
        } else if (scanning.contains(PROGRAMNAME)) {
            trainingProgramFilter = trainingProgramHashMap.keySet().stream()
                    .filter(trainingProgram -> trainingProgramRepository.existsByName(trainingProgram.getName()))
                    .collect(Collectors.toList());
        } else if (scanning.size() == 2) {
            trainingProgramFilter = trainingProgramHashMap.keySet().stream()
                    .filter(trainingProgram -> trainingProgramRepository.existsByProgramIdOrName(trainingProgram.getProgramId(), trainingProgram.getName()))
                    .collect(Collectors.toList());
        }
        return trainingProgramFilter;
    }

>>>>>>> g3_branch
}
