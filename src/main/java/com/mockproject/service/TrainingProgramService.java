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
        printWriter.write("ID, Program name, Created on(yyyy-mm-dd), Duration day, Duration time\n");
        for (TrainingProgram trainingProgram: trainingPrograms){
            printWriter.write(trainingProgram.getProgramId()+","+
                    trainingProgram.getName()+","+trainingProgram.getDateCreated()+","+trainingProgram.getCreator().getFullName()+","+
                    trainingProgram.getDay()+","+trainingProgram.getHour()+"\n");

        }
    }

    @Override
    public List<TrainingProgram> GetTrainingProgramDataFromCsv(InputStream fileInputStream, Long userId)  throws IOException{
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        List<TrainingProgram> trainingPrograms = new ArrayList<>();
        for(CSVRecord record: csvRecords){
            TrainingProgram trainingProgram = new TrainingProgram();
//            System.out.println(record.get("Created by"));
            trainingProgram.setProgramId(Integer.parseInt(record.get("ID")));
            trainingProgram.setName(record.get("Program name"));
            trainingProgram.setDateCreated(LocalDate.parse(record.get("Created on(yyyy-mm-dd)")));
//            trainingProgram.setCreator(user);
            trainingProgram.setDay(Integer.parseInt(record.get("Duration day")));
            trainingProgram.setHour(new BigDecimal(record.get("Duration time")));
            trainingProgram.setLastDateModified(trainingProgram.getDateCreated());
            User user = userRepository.findFirstByIdAndStatus(userId, true);
            trainingProgram.setCreator(user);
            trainingProgram.setLastModifier(user);
            trainingProgram.setLastDateModified(trainingProgram.getDateCreated());
            trainingProgram.setStatus(true);
//            trainingProgram.setLastModifier(user);
            trainingPrograms.add(trainingProgram);
        }
        return trainingPrograms;
    }

    @Override
    public void allowCsvFile(MultipartFile file, Long userId, String check) throws IOException {
        List<TrainingProgram> trainingPrograms = GetTrainingProgramDataFromCsv(file.getInputStream(), userId);
        for(Iterator<TrainingProgram> trainingProgram = trainingPrograms.iterator(); trainingProgram.hasNext();) {
            TrainingProgram program = trainingProgram.next();
            if ("Program ID".equals(check) && trainingProgramRepository.existsByName(program.getName())) {
                System.out.println(program.getName());
            } else if ("Program name".equals(check) && trainingProgramRepository.existsByProgramId(program.getProgramId())) {
                System.out.println(program.getProgramId());
            }
            else{
                trainingProgramRepository.save(program);
            }

        }
    }

    @Override
    public void replaceCsvFile(MultipartFile file, Long userId, String check) throws IOException {
        List<TrainingProgram> trainingPrograms = GetTrainingProgramDataFromCsv(file.getInputStream(), userId);
        for(Iterator<TrainingProgram> trainingProgram = trainingPrograms.iterator(); trainingProgram.hasNext();){
            TrainingProgram program = trainingProgram.next();
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
            } else trainingProgramRepository.save(program);
        }
    }

    @Override
    public void skipCsvFile(MultipartFile file, Long userId, String check) throws IOException {
        List<TrainingProgram> trainingPrograms = GetTrainingProgramDataFromCsv(file.getInputStream(), userId);
        for(Iterator<TrainingProgram> trainingProgram = trainingPrograms.iterator(); trainingProgram.hasNext();) {
            TrainingProgram program = trainingProgram.next();
            if ("Program ID".equals(check) && trainingProgramRepository.existsByProgramId(program.getProgramId())) {
                System.out.println(program.getProgramId());
            } else if ("Program name".equals(check) && trainingProgramRepository.existsByName(program.getName())) {
                System.out.println(program.getName());
            } else if (trainingProgramRepository.existsByProgramId(program.getProgramId()) &&
                    trainingProgramRepository.existsByName(program.getName())) {
                System.out.println(program.getName()+program.getProgramId());
            } else{
                trainingProgramRepository.save(program);
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
