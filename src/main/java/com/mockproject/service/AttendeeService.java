package com.mockproject.service;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.dto.mapper.AttendeeDTOMapper;
import com.mockproject.repository.AttendeeRepository;
import com.mockproject.service.interfaces.IAttendeeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class AttendeeService implements IAttendeeService {

    private final AttendeeRepository repository;

    private final AttendeeDTOMapper mapper;
}
