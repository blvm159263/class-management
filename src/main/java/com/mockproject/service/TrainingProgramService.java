package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.TrainingProgramSyllabus;
import com.mockproject.mapper.TrainingProgramMapper;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingProgramService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingProgramService implements ITrainingProgramService{

    private final TrainingProgramRepository trainingProgramRepository;
    private final SyllabusService syllabusService;
    private final TrainingProgramSyllabusService trainingProgramSyllabusService;

    public List<TrainingProgram> getAll(){
        return trainingProgramRepository.findAll();
    }

    @Override
    public Page<TrainingProgramDTO> findByNameContaining(Integer pageNo, Integer pageSize, String name, String name2) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<TrainingProgram> page = trainingProgramRepository.findAllByNameContainingOrCreatorFullNameContaining(pageable, name,name2);
        Page<TrainingProgramDTO> programDTOPage = page.map(TrainingProgramMapper.INSTANCE::toDTO);
        return programDTOPage;
    }

    public Long countAll() {
        return trainingProgramRepository.count();
    }

    @Override
    public Page<TrainingProgramDTO> getAll(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<TrainingProgram> page = trainingProgramRepository.findAll(pageable);
        Page<TrainingProgramDTO> programDTOPage = page.map(TrainingProgramMapper.INSTANCE::toDTO);
        return programDTOPage;
    }
    @Override
    public TrainingProgramDTO getTrainingProgramById(Long id) {
        return TrainingProgramMapper.INSTANCE.toDTO(trainingProgramRepository.getTrainingProgramById(id));
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


}
