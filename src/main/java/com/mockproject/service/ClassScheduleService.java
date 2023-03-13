package com.mockproject.service;

import com.mockproject.specification.TrainingClassSpecification;
import com.mockproject.dto.*;
import com.mockproject.entity.ClassSchedule;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingProgramSyllabus;
import com.mockproject.mapper.ClassScheduleMapper;
import com.mockproject.mapper.TrainingClassFilterMap;
import com.mockproject.repository.ClassScheduleRepository;
import com.mockproject.repository.TrainingClassAdminRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.service.interfaces.IClassScheduleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ClassScheduleService implements IClassScheduleService {

    private final ClassScheduleRepository repository;
    private final TrainingClassRepository trainingClassRepository;
    private final TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;
    private TrainingClassFilterMap trainingClassFilterMap;
    private TrainingClassAdminRepository trainingClassAdminRepository;

    @Override
    public List<ClassScheduleDTO> listAll() {
        return repository.findAll().stream().map(ClassScheduleMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClassSchedule> listEntity() {
        return repository.findAll();
    }

    @Override
    public ClassSchedule save(ClassScheduleDTO dto) {
        return repository.save(ClassScheduleMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public Long countDayBefore(LocalDate date, Long id) {
        return repository.countAllByDateBeforeAndTrainingClassId(date, id);
    }


    @Override
    public List<TrainingClassFilterResponseDTO> getTrainingClassByDay(LocalDate date) {
        return trainingClassRepository.findAllByListClassSchedulesDate(date)
                .stream().map(trainingClass -> getTrainingClassDetail(trainingClass, date)).collect(Collectors.toList());
    }

    @Override
    public List<TrainingClassFilterResponseDTO> getTrainingClassByWeek(TrainingClassFilterRequestDTO filterRequestDTO) {
        var trainingClassFiltered = trainingClassRepository.findAll(TrainingClassSpecification.findByFilter(filterRequestDTO));
        List<TrainingClassFilterResponseDTO> result = new ArrayList<>();
        trainingClassFiltered.stream().distinct().forEach(trainingClass -> {
            trainingClass.getListClassSchedules().stream().forEach(classSchedule -> {
                if (classSchedule.getDate().isAfter(filterRequestDTO.getStartDate().minusDays(1)) && classSchedule.getDate().isBefore(filterRequestDTO.getEndDate().plusDays(1)))
                    result.add(getTrainingClassDetail(trainingClass, classSchedule.getDate()));
            });
        });
        return result;
    }

    @Override
    public TrainingClassFilterResponseDTO getTrainingClassDetail(TrainingClass trainingClass, LocalDate date) {
        var learnedDay = repository.countAllByDateBeforeAndTrainingClassId(date, trainingClass.getId()) + 1;

        var durationDay = String.valueOf(learnedDay).concat("/" + trainingClass.getDay());
        var syllabusesList = trainingClass.getTrainingProgram().getListTrainingProgramSyllabuses()
                .stream().map(TrainingProgramSyllabus::getSyllabus).collect(Collectors.toList());
        var trainingClassAdmin = trainingClassAdminRepository.findAllByTrainingClassId(trainingClass.getId());
        List<UnitResponseDTO> unit = new ArrayList<>();
        //check syllabus to get unit
        for (int i = 0; i < syllabusesList.size(); i++) {
            if (learnedDay > syllabusesList.get(i).getDay()) {
                learnedDay -= syllabusesList.get(i).getDay();
            } else {
                Long finalLearnedDay = learnedDay;
                log.info(syllabusesList.get(i).getCode());
                unit = syllabusesList.get(i).getListSessions()
                        .stream().filter(session -> session.getSessionNumber() == finalLearnedDay)
                        .flatMap(session -> session.getListUnit().stream())
                        .map(Unit -> {
                            UnitResponseDTO unitDTO = new UnitResponseDTO();
                            unitDTO.setUnitTitle(Unit.getUnitTitle());
                            unitDTO.setUnitNumber(Unit.getUnitNumber());
                            return unitDTO;
                        }).collect(Collectors.toList());
            }
        }

        var trainerName = trainingClassUnitInformationRepository.findAllByTrainingClassId(trainingClass.getId())
                .stream().map(trainingClassUnitInformation -> trainingClassUnitInformation.getTrainer().getFullName()).collect(Collectors.toList());
        return trainingClassFilterMap.toTrainingClassFilterResponseDTO(trainingClass,
                trainerName,
                durationDay,
                date, unit);
    }

    @Override
    public List<TrainingClass> searchTrainingClassByWeek(String search) {
        Specification<TrainingClass> specification = TrainingClassSpecification.searchByAll(search);
        List<TrainingClass> result = trainingClassRepository.findAll(specification);
        return result;
    }

}
