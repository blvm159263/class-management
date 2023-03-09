package com.mockproject.service.interfaces;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.entity.Attendee;

import java.util.List;

public interface IAttendeeService {

    List<AttendeeDTO> getAllAttendee();

    Attendee save(AttendeeDTO dto);
}
