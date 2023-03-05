package com.mockproject.service;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.entity.ClassSchedule;
import com.mockproject.mapper.ClassScheduleMapper;
import com.mockproject.repository.ClassScheduleRepository;
import com.mockproject.service.interfaces.IClassScheduleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ClassScheduleService implements IClassScheduleService{

    private final ClassScheduleRepository repository;

    @Override
    public List<ClassScheduleDTO> listAll() {
        return repository.findAll().stream().map(ClassScheduleMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClassSchedule> listEntity() {
//        List<ClassScheduleDTO> list = repository.findAll().stream().map(ClassScheduleMapper.INSTANCE::toClassScheduleDTO).collect(Collectors.toList());
//        return list.stream().map(ClassScheduleMapper.INSTANCE::toEntity).collect(Collectors.toList());
        return repository.findAll();
    }

    @Override
    public ClassSchedule save(ClassScheduleDTO dto) {
        return repository.save(ClassScheduleMapper.INSTANCE.toEntity(dto));
    }


}
