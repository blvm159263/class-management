package com.mockproject.service;

import com.mockproject.dto.ReadFileDto;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
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
    }

    @Override
    public Page<TrainingProgramDTO> findByNameContaining(Integer pageNo, Integer pageSize, String name, String name2) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<TrainingProgram> page = trainingProgramRepository.findAllByNameContainingOrCreatorFullNameContaining(pageable, name, name2);
        Page<TrainingProgramDTO> programDTOPage = page.map(TrainingProgramMapper.INSTANCE::toDTO);
        return programDTOPage;
    }

    public Long countAll() {
        return trainingProgramRepository.count();
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
                ;
                int lastTrainingProgramId = trainingProgramRepository.findTopByOrderByIdDesc().getId().intValue() + 1;

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

                List<TrainingProgramSyllabus> programSyllabus = syllabusList.stream()
                        .map(syllabus -> new TrainingProgramSyllabus(null, true, syllabus, trainingProgram))
                        .collect(Collectors.toList());

                trainingProgramRepository.save(trainingProgram);
                trainingProgramSyllabusService.saveAll(programSyllabus);
            }
            //add with csv file
        } else {
            List<String> Scanning = readFileDto.getScanning().stream().map(String::toLowerCase).collect(Collectors.toList());
            if (!Scanning.isEmpty()) {

                List<TrainingProgram> trainingProgramFilter = new ArrayList<>();
                if (Scanning.contains(PROGRAMID)) {
                    trainingProgramFilter = trainingProgramHashMap.keySet().stream()
                            .filter(trainingProgram -> trainingProgramRepository.getTrainingProgramById(trainingProgram.getProgramId()) != null)
                            .collect(Collectors.toList());
                } else if (Scanning.contains(PROGRAMNAME)) {
                    trainingProgramFilter = trainingProgramHashMap.keySet().stream()
                            .filter(trainingProgram -> trainingProgramRepository.findByName(trainingProgram.getName()) != null)
                            .collect(Collectors.toList());
                } else if (Scanning.size() == 2) {
                    trainingProgramFilter = trainingProgramHashMap.keySet().stream()
                            .filter(trainingProgram -> trainingProgramRepository.getTrainingProgramByIdOrName(trainingProgram.getProgramId(), trainingProgram.getName()) != null)
                            .collect(Collectors.toList());
                }
                String dupHanlde = readFileDto.getDuplicateHandle().toLowerCase();
                System.out.println(dupHanlde);
                if (dupHanlde.equals(ALLOW)) {

                } else if (dupHanlde.equals(SKIP)) {
                    trainingProgramFilter.stream().forEach(trainingProgram ->
                            trainingProgramHashMap.remove(trainingProgram));
                } else if (dupHanlde.equals(REPLACE)) {

                }
                for (TrainingProgram key : trainingProgramHashMap.keySet()){
                    System.out.println(key);
                }
                HashMap<TrainingProgram, List<Syllabus>> trainingProgramSyllabusHashMap = new HashMap<>();

                for (TrainingProgram key : trainingProgramHashMap.keySet()) {

                    List<Syllabus> syllabusList = syllabusService.getAllSyllabusEntityById(trainingProgramHashMap.get(key));

                    if (syllabusList.size() < trainingProgramHashMap.get(key).size()) {
                        throw new SyllabusException("One or more syllabus Ids not found", HttpStatus.BAD_REQUEST.value());
                    }
                    trainingProgramSyllabusHashMap.put(key, syllabusList);
                }
                for (TrainingProgram key : trainingProgramSyllabusHashMap.keySet()) {
                    var syllabusList = trainingProgramSyllabusHashMap.get(key);

                    int day = getDay(syllabusList);
                    BigDecimal hour = getHour(syllabusList);

                    int lastTrainingProgramId = trainingProgramRepository.findTopByOrderByIdDesc().getId().intValue() + 1;
                    key.setDay(day);
                    key.setHour(hour);
                    List<TrainingProgramSyllabus> programSyllabus = syllabusList.stream()
                            .map(syllabus -> new TrainingProgramSyllabus(null, true, syllabus, key))
                            .collect(Collectors.toList());

//                    trainingProgramRepository.save(key);
//                    trainingProgramSyllabusService.saveAll(programSyllabus);
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

        CSVParser parser = fileService.readFile(file, readFileDto.getEncodingType());
        // skip header of csv file
        parser.iterator().next();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        HashMap<TrainingProgram, List<Long>> trainingProgramHashMap = new HashMap<>();
        try {
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


}
