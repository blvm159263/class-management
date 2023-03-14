package com.mockproject.service;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.entity.Attendee;
import com.mockproject.mapper.AttendeeMapper;
import com.mockproject.repository.AttendeeRepository;
import com.mockproject.service.interfaces.IAttendeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendeeService implements IAttendeeService {

    private final AttendeeRepository attendeeRepo;

    @Override
    public List<AttendeeDTO> getAllAttendee(boolean status) {
        return attendeeRepo.findAllByStatusIs(status).stream().map(AttendeeMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public Attendee save(AttendeeDTO dto) {
        return attendeeRepo.save(AttendeeMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public AttendeeDTO getAttendeeById(boolean status, long id) {
        Attendee attendee = attendeeRepo.findByStatusAndId(status, id).orElseThrow(() -> new NotFoundException("Attendee not found with id: " + id));
        return AttendeeMapper.INSTANCE.toDTO(attendee);
    }

    @Override
    public List<AttendeeDTO> listAllTrue() {
        return attendeeRepo.findByStatus(true).stream().map(AttendeeMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
