package com.mockproject.service;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.repository.LocationRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingProgramRepository;
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

    @Override
    public Long create(TrainingClassDTO trainingClassDTO) {
        trainingClassDTO.setClassCode(generateClassCode(trainingClassDTO));
        trainingClassDTO.setPeriod(getPeriod(trainingClassDTO.getStartTime(),trainingClassDTO.getEndTime()));
        TrainingClass entity = TrainingClassMapper.INSTANCE.toEntity(trainingClassDTO);
        TrainingClass trainingClass = trainingClassRepository.save(entity);
        if (trainingClass != null) {
            return trainingClass.getId();
        }
        return null;
    }

    public String generateClassCode(TrainingClassDTO trainingClassDTO) {

        final Map<Long, String> attendeeCode = new HashMap<>();
        attendeeCode.put(1L,"FR");
        attendeeCode.put(2L,"FR.F.ON");
        attendeeCode.put(3L,"FR.F.OFF");
        attendeeCode.put(4L,"IN");

        String locationName = locationRepository.findById(trainingClassDTO.getLocationId()).orElseThrow().getLocationName();
        String locationCode = locationName.chars()
                .filter(Character::isUpperCase)
                .mapToObj(c -> String.valueOf((char)c))
                .collect(Collectors.joining());
        String programName = trainingProgramRepository.findById(trainingClassDTO.getTrainingProgramId()).orElseThrow().getName();
        String programCode = programName.split(" ", 2)[0];
        Year yearCode = Year.now().minusYears(2000);
        StringBuilder builder = new StringBuilder();
        List<TrainingClass> listExisting = trainingClassRepository.findByClassNameContaining(trainingClassDTO.getClassName());
        String versionCode = String.valueOf(listExisting.size() + 1);

        builder.append(locationCode)
                .append(yearCode.toString())
                .append("_")
                .append(attendeeCode.get(trainingClassDTO.getAttendeeId()))
                .append("_")
                .append(programCode)
                .append("_")
                .append(versionCode);

        return builder.toString();
    }

    public int getPeriod(Time startTime, Time endTime){
        if(startTime.before(Time.valueOf("12:00:00"))){
            return 0;
        }
        if(startTime.after(Time.valueOf("17:00:00"))){
            return 2;
        }
        return 1;
    }
}
