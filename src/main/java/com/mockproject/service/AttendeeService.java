package com.mockproject.service;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.entity.Attendee;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.AttendeeMapper;
import com.mockproject.repository.AttendeeRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.IAttendeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendeeService implements IAttendeeService {

    private final AttendeeRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    @Override
    public List<AttendeeDTO> listAll() {
        return repository.findAll().stream().map(AttendeeMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public Attendee save(AttendeeDTO dto) {
        return repository.save(AttendeeMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public AttendeeDTO getAttendeeByClassCode(String code) {
        TrainingClass tc =  trainingClassRepository.findByClassCode(code).get(0);
        return AttendeeMapper.INSTANCE.toDTO(tc.getAttendee());
    }


}
