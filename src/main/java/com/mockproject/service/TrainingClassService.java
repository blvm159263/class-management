package com.mockproject.service;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.TrainingClassMapper;

import com.mockproject.repository.LocationRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.sql.Time;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService {

    private final TrainingClassRepository trainingClassRepository;
    private final LocationRepository locationRepository;
    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainingClassUnitInformationRepository classUnitRepo;

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
                .append(yearCode)
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
    @Override
    public Page<TrainingClassDTO> getListClass(boolean status,
                                               List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                               List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                               long fsu, long trainerId, String search, String[] sort, Optional<Integer> page)
    {
        List<Sort.Order> order = new ArrayList<>();
        if(sort[0].contains(",")){
            for (String sortItem: sort) {
                String[] subSort = sortItem.split(",");
                order.add(new Sort.Order(getSortDirection(subSort[1]),subSort[0]));
            }
        }else{
            order.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
        }
        List<Long> classId = new ArrayList<>();
        if(trainerId!=0){
            classId = classUnitRepo
                    .findByStatusAndTrainerId( true, trainerId)
                    .stream().map(t -> t.getTrainingClass().getId())
                    .collect(Collectors.toList());
            classId.add(-1L);
        }
        Pageable pageable = PageRequest.of(page.orElse(0), 10, Sort.by(order));
        Page<TrainingClass> pages = trainingClassRepository.getListClass(status, locationId, fromDate, toDate, period,
                isOnline, state, attendeeId, fsu, classId, search, pageable);
        if(pages.getContent().size() > 0){
            return new PageImpl<>(
                    pages.stream().map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                    pages.getPageable(),
                    pages.getTotalElements());
        }else {
            throw new NotFoundException("Training Class not found!");
        }
    }

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @Override
    public List<TrainingClassDTO> getAllClass() {
        return trainingClassRepository.findAllByStatus(true).stream().map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
