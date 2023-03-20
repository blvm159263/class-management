package com.mockproject.service;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.repository.LocationRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.TrainingProgramRepository;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TrainingClassService implements ITrainingClassService {

    private final TrainingClassRepository classRepo;

    private final LocationRepository locationRepository;

    private final TrainingProgramRepository trainingProgramRepository;

    private final TrainingClassUnitInformationRepository classUnitRepo;

    private static final int RESULTS_PER_PAGE = 10;


    @Override
    public List<TrainingClass> findAllByListClassSchedulesDate(LocalDate date) {
        return classRepo.findAllByListClassSchedulesDate(date);
    }

    @Override
    public List<TrainingClass> findAllBySpecification(Specification specification) {
        return classRepo.findAll((Sort) specification);
    }

    @Override
    public List<TrainingClass> findAllBySearchTextAndDate(List<String> searchText, LocalDate date) {
        return classRepo.findAllBySearchTextAndListClassSchedulesDate(searchText,date);
    }

    @Override
    public List<TrainingClass> findAllBySearchTextAndWeek(List<String> searchText, LocalDate startDate, LocalDate endDate) {
        return classRepo.findAllBySearchTextAndListClassSchedulesWeek(searchText,startDate,endDate);
    }

    @Override
    public TrainingClassDTO getAllDetails(Long id) {
        TrainingClass details = classRepo.findByIdAndStatus(id, true).orElseThrow();
        return TrainingClassMapper.INSTANCE.toDTO(details);
    }


    @Override
    public Long create(TrainingClassDTO trainingClassDTO) {
        trainingClassDTO.setClassCode(generateClassCode(trainingClassDTO));
        trainingClassDTO.setPeriod(getPeriod(trainingClassDTO.getStartTime(),trainingClassDTO.getEndTime()));
        TrainingClass entity = TrainingClassMapper.INSTANCE.toEntity(trainingClassDTO);
        TrainingClass trainingClass = classRepo.save(entity);
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
        List<TrainingClass> listExisting = classRepo.findByClassNameContaining(trainingClassDTO.getClassName());
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
        else if(startTime.after(Time.valueOf("17:00:00"))){
            return 2;
        }
        return 1;
    }


    @Override
    public Page<TrainingClassDTO> getListClass(boolean status,
                                               List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                               List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                               Long fsu, Long trainerId,  List<String> search, String[] sort, Optional<Integer> page)
    {
        List<Sort.Order> order = new ArrayList<>();
        int skipCount = page.orElse(0) * RESULTS_PER_PAGE;
        Set<String> sourceFieldList = getAllFields(new TrainingClass().getClass());
        if(sort[0].contains(",")){
            for (String sortItem: sort) {
                String[] subSort = sortItem.split(",");
                if(ifPropertpresent(sourceFieldList, sort[0])) {
                    order.add(new Sort.Order(getSortDirection(subSort[1]), transferProperty(subSort[0])));
                } else {
                    throw new NotFoundException(subSort[0] + " is not a propertied of Training CLass!");
                }
            }
        } else {
            if(sort.length == 1){
                throw new ArrayIndexOutOfBoundsException("Sort direction(asc/desc) not found!");
            }
            if(ifPropertpresent(sourceFieldList, sort[0])) {
                order.add(new Sort.Order(getSortDirection(sort[1]), transferProperty(sort[0])));
            } else {
                throw new NotFoundException(sort[0] + " is not a propertied of Training CLass!");
            }
        }
        List<Long> classId = new ArrayList<>();
        if(trainerId!=0){
            classId = classUnitRepo
                    .findByStatusAndTrainerId( true, trainerId)
                    .stream().map(t -> t.getTrainingClass().getId())
                    .collect(Collectors.toList());
            classId.add(-1L);
        }
        List<TrainingClass> pages = classRepo.getListClass(status, locationId, fromDate, toDate, period,
                isOnline, state, attendeeId, fsu, classId, search.size() > 0 ? search.get(0) : "", Sort.by(order));
        if (search.size() > 1){
            for (int i = 1; i < search.size(); i++) {
                String subSearch = search.get(i).toUpperCase();
                pages = pages.stream().filter(c
                                -> c.getClassName().toUpperCase().contains(subSearch) ||
                                c.getClassCode().toUpperCase().contains(subSearch) ||
                                c.getCreator().getFullName().toUpperCase().contains(subSearch))
                        .collect(Collectors.toList());
            }
        }
        if(pages.size() > 0){
            return new PageImpl<>(
                    pages.stream().skip(skipCount).limit(RESULTS_PER_PAGE).map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                    PageRequest.of(page.orElse(0), RESULTS_PER_PAGE, Sort.by(order)),
                    pages.size());
        }else {
            throw new NotFoundException("Training Class not found!");
        }
    }

    private static String transferProperty(String property){
        switch (property) {
            case "creator":
                return "creator.fullName";
            case "attendee":
                return "attendee.attendeeName";
            case "location":
                return "location.locationName";
            case "fsu":
                return "fsu.fsuName";
            default:
                return property;
        }
    }

    private static Set<String> getAllFields(final Class<?> type) {
        Set<String> fields = new HashSet<>();
        //loop the fields using Java Reflections
        for (Field field : type.getDeclaredFields()) {
            fields.add(field.getName());
        }
        //recursive call to getAllFields
        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
    }

    private static boolean ifPropertpresent(final Set<String> properties, final String propertyName) {
        if (properties.contains(propertyName)) {
            return true;
        }
        return false;
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
        return classRepo.findAllByStatus(true).stream().map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
