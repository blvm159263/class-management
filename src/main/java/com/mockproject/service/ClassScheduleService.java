package com.mockproject.service;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.entity.ClassSchedule;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.ClassScheduleMapper;
import com.mockproject.repository.ClassScheduleRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.IClassScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassScheduleService implements IClassScheduleService{

    private final ClassScheduleRepository repository;
    private final TrainingClassRepository trainingClassRepository;

    @Override
    public List<ClassScheduleDTO> listAll() {
        return repository.findAll().stream().map(ClassScheduleMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean saveClassScheduleForTrainingClass(List<LocalDate> listDate, Long tcId) {
//        Long count = listDto.stream().map(p -> repository.save(ClassScheduleMapper.INSTANCE.toEntity(p))).filter(Objects::nonNull).count();
        TrainingClass tc = new TrainingClass();
        tc.setId(tcId);
        List<ClassSchedule> list = listDate.stream().map(p-> new ClassSchedule(null, p, true,tc)).toList();
        List<ClassSchedule> result = repository.saveAll(list);
        return !result.isEmpty();
    }

    @Override
    public List<ClassScheduleDTO> getClassScheduleByTrainingClassId(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<ClassSchedule> schedules = repository.findByTrainingClassAndStatusOrderByDateAsc(tc, true).orElseThrow();
        return schedules.stream().map(ClassScheduleMapper.INSTANCE::toDTO).toList();
    }


}
