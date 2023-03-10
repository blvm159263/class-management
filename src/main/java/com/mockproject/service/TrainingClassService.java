package com.mockproject.service;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.dto.TrainingClassAdminDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.AttendeeMapper;
import com.mockproject.mapper.ClassScheduleMapper;
import com.mockproject.mapper.TrainingClassAdminMapper;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService{

    private final TrainingClassRepository repository;





}