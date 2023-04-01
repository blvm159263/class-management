package com.mockproject.service;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.dto.TrainingClassAdminDTO;
import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.dto.TrainingClassUnitInformationDTO;
import com.mockproject.entity.*;
import com.mockproject.exception.entity.EntityNotFoundException;
import com.mockproject.mapper.ClassScheduleMapper;
import com.mockproject.mapper.TrainingClassAdminMapper;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.mapper.TrainingClassUnitInformationMapper;
import com.mockproject.repository.*;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService {

    private final TrainingClassRepository trainingClassRepository;
    private final LocationRepository locationRepository;
    private final TrainingProgramRepository trainingProgramRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;
    private final TrainingClassAdminRepository trainingClassAdminRepository;


    @Override
    public Long create(TrainingClassDTO trainingClassDTO) {
        trainingClassDTO.setClassCode(generateClassCode(trainingClassDTO));
        trainingClassDTO.setPeriod(getPeriod(trainingClassDTO.getStartTime(), trainingClassDTO.getEndTime()));
        TrainingClass entity = TrainingClassMapper.INSTANCE.toEntity(trainingClassDTO);
        TrainingClass trainingClass = trainingClassRepository.save(entity);
        if (trainingClass != null) {
            return trainingClass.getId();
        }
        return null;
    }

    @Override
    public boolean deleteTrainingClass(Long id) {
        TrainingClass trainingClass = trainingClassRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Don't find any class"));
        trainingClass.setStatus(false);
        return trainingClassRepository.save(trainingClass) != null;
    }

    @Override
    public boolean duplicateClass(Long id) {
        TrainingClass trainingClass = trainingClassRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Don't find any class"));
        TrainingClassDTO dto = TrainingClassMapper.INSTANCE.toDTO(trainingClass);
        dto.setId(null);
        dto.setClassCode(generateClassCode(dto));
        TrainingClass newTrainingClass = trainingClassRepository.save(TrainingClassMapper.INSTANCE.toEntity(dto));
        if (newTrainingClass != null) {
            Long newId = newTrainingClass.getId();
            List<TrainingClassAdmin> trainingClassAdminList = trainingClass.getListTrainingClassAdmins().stream().map(
                    p -> TrainingClassAdminMapper.INSTANCE.toEntity(new TrainingClassAdminDTO(null, true, p.getAdmin().getId(), newId))
            ).toList();
            trainingClassAdminRepository.saveAll(trainingClassAdminList);

            List<ClassSchedule> classScheduleList = trainingClass.getListClassSchedules().stream().map(p ->
                    ClassScheduleMapper.INSTANCE.toEntity(new ClassScheduleDTO(null, p.getDate(), true, newId))
            ).toList();
            classScheduleRepository.saveAll(classScheduleList);

            List<TrainingClassUnitInformation> trainingClassUnitInformationList = trainingClass.getListTrainingClassUnitInformations().stream().map(p ->
                    TrainingClassUnitInformationMapper.INSTANCE.toEntity(
                            new TrainingClassUnitInformationDTO(null, true, p.getTrainer().getId(), p.getUnit().getId(), newId, p.getTower().getId())
                            )).toList();
            trainingClassUnitInformationRepository.saveAll(trainingClassUnitInformationList);

            return true;
        }

        return false;
    }

    public String generateClassCode(TrainingClassDTO trainingClassDTO) {

        final Map<Long, String> attendeeCode = new HashMap<>();
        attendeeCode.put(1L, "FR");
        attendeeCode.put(2L, "FR.F.ON");
        attendeeCode.put(3L, "FR.F.OFF");
        attendeeCode.put(4L, "IN");

        String locationName = locationRepository.findById(trainingClassDTO.getLocationId()).orElseThrow().getLocationName();
        String locationCode = locationName.chars()
                .filter(Character::isUpperCase)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
        String programName = trainingProgramRepository.findById(trainingClassDTO.getTrainingProgramId()).orElseThrow().getName();
        String programCode = programName.split(" ", 2)[0];
        Year yearCode = Year.now().minusYears(2000);
        StringBuilder builder = new StringBuilder();
        List<TrainingClass> listExisting = trainingClassRepository.findByClassNameContaining(trainingClassDTO.getClassName());
        String versionCode = String.format("%02d", listExisting.size());

        builder.append(locationCode)
                .append(yearCode)
                .append("_")
                .append(attendeeCode.get(trainingClassDTO.getAttendeeId()))
                .append("_")
                .append(programCode)
                .append("_")
                .append(versionCode);

        return builder.toString();
    }

    public int getPeriod(Time startTime, Time endTime) {
        if (startTime.before(Time.valueOf("12:00:00"))) {
            return 0;
        }
        if (startTime.after(Time.valueOf("17:00:00"))) {
            return 2;
        }
        return 1;
    }
}
