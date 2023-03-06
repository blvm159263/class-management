package com.mockproject.service;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.entity.Attendee;
import com.mockproject.mapper.AttendeeMapper;
import com.mockproject.repository.AttendeeRepository;
import com.mockproject.service.interfaces.IAttendeeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AttendeeService implements IAttendeeService {

    private final AttendeeRepository repository;

    @Override
    public List<AttendeeDTO> listAll() {
        return repository.findAll().stream().map(AttendeeMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public Attendee save(AttendeeDTO dto) {
        return repository.save(AttendeeMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public List<AttendeeDTO> listAllTrue() {
        return repository.findByStatus(true).stream().map(AttendeeMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
