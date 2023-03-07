package com.mockproject.service;

import com.mockproject.dto.TrainingClassFilterResponseDTO;
import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.TrainingClassFilterMap;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.IClassScheduleService;
import com.mockproject.service.interfaces.ILocationService;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TrainingClassService implements ITrainingClassService {
    private final TrainingClassRepository repository;

}
