package com.mockproject.service;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.dto.mapper.AttendeeDTOMapper;
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

    private final AttendeeDTOMapper mapper;

    @Override
    public List<AttendeeDTO> listAll() {
        return repository.findAll().stream().map(mapper).collect(Collectors.toList());
    }
}
