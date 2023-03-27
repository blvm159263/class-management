package com.mockproject.service;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.TrainingProgramSyllabus;
import com.mockproject.entity.User;
import com.mockproject.exception.file.FileRequestException;
import com.mockproject.mapper.TrainingProgramMapper;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramService implements ITrainingProgramService {
    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainingProgramSyllabusService trainingProgramSyllabusService;
    private final SyllabusService syllabusService;
    private final UserRepository userRepository;
    public void save(Long sylId, String name){
        Syllabus syllabus = syllabusService.getSyllabusById(sylId);
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
        programSyllabus.setSyllabus(syllabus);
        trainingProgramRepository.save(trainingProgram);
        trainingProgramSyllabusService.addSyllabus(programSyllabus);
    }
    public List<TrainingProgram> getAll(){
        return trainingProgramRepository.findAll();
    }

    @Override
    public void downloadCsvFile(PrintWriter printWriter, List<TrainingProgram> trainingPrograms) {
        printWriter.write("program_id,name,dateCreated,lastDateModified,status,listSyllabusesId\n");
    }

    @Override
    public List<TrainingProgramDTO> GetTrainingProgramDataFromCsv(InputStream fileInputStream)  throws IOException{
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        List<TrainingProgramDTO> trainingPrograms = new ArrayList<>();
        for(CSVRecord record: csvRecords){
            TrainingProgramDTO trainingProgram = new TrainingProgramDTO();
//            System.out.println(record.get("Created by"));
            trainingProgram.setProgramId(Integer.parseInt(record.get("program_id")));
            trainingProgram.setName(record.get("name"));
            trainingProgram.setDateCreated(LocalDate.parse(record.get("dateCreated")));
//            trainingProgram.setCreator(user);
            trainingProgram.setDay(0);
            trainingProgram.setHour(new BigDecimal(0));
            trainingProgram.setLastDateModified(LocalDate.parse(record.get("lastDateModified")));
            trainingProgram.setStatus(Boolean.parseBoolean(record.get("status")));
            String[] splitted = record.get("listSyllabusesId").split("/");
            for(Iterator<String> iterator = Arrays.stream(splitted).iterator(); iterator.hasNext();){
                trainingProgram.setSyllabusID(Long.parseLong(iterator.next()));
            }
//            trainingProgram.setLastModifier(user);
            trainingPrograms.add(trainingProgram);
        }
        return trainingPrograms;
    }

    @Override
    public void allowCsvFile(MultipartFile file, Long userId, String check) throws IOException {
        List<TrainingProgramDTO> trainingPrograms = GetTrainingProgramDataFromCsv(file.getInputStream());
        User user = userRepository.findFirstByIdAndStatus(userId, true);
//        for(TrainingProgramDTO trainingProgram : trainingPrograms){
//            System.out.println(trainingProgram.getProgramId());
//            System.out.println(trainingProgram.getName());
//        }
        for(Iterator<TrainingProgramDTO> trainingProgram = trainingPrograms.iterator(); trainingProgram.hasNext();) {
            TrainingProgramDTO trainingProgramDTO = trainingProgram.next();
            TrainingProgram program = new TrainingProgram();
            program.setProgramId(trainingProgramDTO.getProgramId());
            program.setCreator(user);
            program.setLastModifier(user);
            program.setName(trainingProgramDTO.getName());
            program.setHour(trainingProgramDTO.getHour());
            program.setDay(trainingProgramDTO.getDay());
            program.setStatus(trainingProgramDTO.isStatus());
            program.setLastDateModified(trainingProgramDTO.getLastDateModified());
            program.setDateCreated(trainingProgramDTO.getDateCreated());
            System.out.println(program.getProgramId());
            System.out.println(trainingProgramRepository.existsByName(program.getName()));
            System.out.println(trainingProgramRepository.existsByProgramId(program.getProgramId()));
            if ("Program ID".equals(check) && trainingProgramRepository.existsByName(program.getName())) {
                System.out.println(program.getName());
            } else if ("Program name".equals(check) && trainingProgramRepository.existsByProgramId(program.getProgramId())) {
                System.out.println(program.getProgramId());
            }
            else{
                Syllabus syllabus = syllabusService.getSyllabusById(trainingProgramDTO.getSyllabusID());
                TrainingProgramSyllabus programSyllabus = new TrainingProgramSyllabus();
                programSyllabus.setTrainingProgram(program);
                programSyllabus.setSyllabus(syllabus);
                programSyllabus.setStatus(false);
                trainingProgramRepository.save(program);
                trainingProgramSyllabusService.addSyllabus(programSyllabus);
            }

        }
    }

    @Override
    public void replaceCsvFile(MultipartFile file, Long userId, String check) throws IOException {
        List<TrainingProgramDTO> trainingPrograms = GetTrainingProgramDataFromCsv(file.getInputStream());
        User user = userRepository.findFirstByIdAndStatus(userId, true);
        for(Iterator<TrainingProgramDTO> trainingProgram = trainingPrograms.iterator(); trainingProgram.hasNext();){
            TrainingProgramDTO trainingProgramDTO = trainingProgram.next();
            TrainingProgram program = new TrainingProgram();
            program.setProgramId(trainingProgramDTO.getProgramId());
            program.setCreator(user);
            program.setLastModifier(user);
            program.setName(trainingProgramDTO.getName());
            program.setHour(trainingProgramDTO.getHour());
            program.setDay(trainingProgramDTO.getDay());
            program.setStatus(trainingProgramDTO.isStatus());
            program.setLastDateModified(trainingProgramDTO.getLastDateModified());
            program.setDateCreated(trainingProgramDTO.getDateCreated());
            System.out.println(program.getProgramId());
            if("Program ID".equals(check) && trainingProgramRepository.existsByProgramId(program.getProgramId())){
                List<TrainingProgram> programs = trainingProgramRepository.getTrainingProgramByProgramId(program.getProgramId());
                for(Iterator<TrainingProgram> trainingProgramIterator = programs.iterator(); trainingProgramIterator.hasNext();){
                    TrainingProgram trainingProgram1 = trainingProgramIterator.next();
                    if(trainingProgram1.getProgramId()==program.getProgramId()) program.setId(trainingProgram1.getId());
                    trainingProgramRepository.save(program);
                }
            } else if ("Program name".equals(check) && trainingProgramRepository.existsByName(program.getName())) {
                List<TrainingProgram> programs = trainingProgramRepository.getTrainingProgramByName(program.getName());
                for(Iterator<TrainingProgram> trainingProgramIterator = programs.iterator(); trainingProgramIterator.hasNext();){
                    TrainingProgram trainingProgram1 = trainingProgramIterator.next();
                    if(trainingProgram1.getName().equals(program.getName())) program.setId(trainingProgram1.getId());
                    trainingProgramRepository.save(program);
                }
            }else if (trainingProgramRepository.existsByProgramId(program.getProgramId())&&
                    trainingProgramRepository.existsByName(program.getName())){
                List<TrainingProgram> programs = trainingProgramRepository.getTrainingProgramByNameAndProgramId(program.getName(), program.getProgramId());
                for(Iterator<TrainingProgram> trainingProgramIterator = programs.iterator(); trainingProgramIterator.hasNext();){
                    TrainingProgram trainingProgram1 = trainingProgramIterator.next();
                    if(trainingProgram1.getName().equals(program.getName())) program.setId(trainingProgram1.getId());
                    trainingProgramRepository.save(program);
                }
            } else {
                Syllabus syllabus = syllabusService.getSyllabusById(trainingProgramDTO.getSyllabusID());
                TrainingProgramSyllabus programSyllabus = new TrainingProgramSyllabus();
                programSyllabus.setTrainingProgram(program);
                programSyllabus.setSyllabus(syllabus);
                programSyllabus.setStatus(false);
                trainingProgramSyllabusService.addSyllabus(programSyllabus);
                trainingProgramRepository.save(program);
            }
        }
    }

    @Override
    public void skipCsvFile(MultipartFile file, Long userId, String check) throws IOException {
        List<TrainingProgramDTO> trainingPrograms = GetTrainingProgramDataFromCsv(file.getInputStream());
        User user = userRepository.findFirstByIdAndStatus(userId, true);
        for(Iterator<TrainingProgramDTO> trainingProgram = trainingPrograms.iterator(); trainingProgram.hasNext();) {
            TrainingProgramDTO trainingProgramDTO = trainingProgram.next();
            TrainingProgram program = new TrainingProgram();
            program.setProgramId(trainingProgramDTO.getProgramId());
            program.setCreator(user);
            program.setLastModifier(user);
            program.setName(trainingProgramDTO.getName());
            program.setHour(trainingProgramDTO.getHour());
            program.setDay(trainingProgramDTO.getDay());
            program.setStatus(trainingProgramDTO.isStatus());
            program.setLastDateModified(trainingProgramDTO.getLastDateModified());
            program.setDateCreated(trainingProgramDTO.getDateCreated());
            if ("Program ID".equals(check) && trainingProgramRepository.existsByProgramId(program.getProgramId())) {
                System.out.println(program.getProgramId());
            } else if ("Program name".equals(check) && trainingProgramRepository.existsByName(program.getName())) {
                System.out.println(program.getName());
            } else if (trainingProgramRepository.existsByProgramId(program.getProgramId()) &&
                    trainingProgramRepository.existsByName(program.getName())) {
                System.out.println(program.getName()+program.getProgramId());
            } else{
                Syllabus syllabus = syllabusService.getSyllabusById(trainingProgramDTO.getSyllabusID());
                TrainingProgramSyllabus programSyllabus = new TrainingProgramSyllabus();
                programSyllabus.setTrainingProgram(program);
                programSyllabus.setSyllabus(syllabus);
                programSyllabus.setStatus(false);
                trainingProgramRepository.save(program);
                trainingProgramSyllabusService.addSyllabus(programSyllabus);
            }
        }
    }


    @Override
    public TrainingProgram getTrainingProgramById(Long id) {
        return trainingProgramRepository.getTrainingProgramById(id);
    }

    @Override
    public List<TrainingProgram> getAll(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<TrainingProgram> result = trainingProgramRepository.findAll(paging);
        return result.getContent();
    }

    @Override
    public List<TrainingProgram> getByName(String keyword) {
        return trainingProgramRepository.getTrainingProgramByNameContains(keyword);
    }

    @Override
    public Long countAll() {
        return trainingProgramRepository.count();
    }

    @Override
    public List<TrainingProgram> getByCreatorFullname(String keyword) {
        return trainingProgramRepository.getAllByCreatorFullNameContains(keyword);
    }

    @Override
    public List<TrainingProgramDTO> searchByName(String name) {
        return trainingProgramRepository.findByNameContainingAndStatus(name, true).stream().map(TrainingProgramMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
