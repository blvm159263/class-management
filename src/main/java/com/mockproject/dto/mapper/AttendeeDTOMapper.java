package com.mockproject.dto.mapper;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.entity.Attendee;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AttendeeDTOMapper implements Function<Attendee, AttendeeDTO> {


    @Override
    public AttendeeDTO apply(Attendee attendee) {

        return new AttendeeDTO(
                attendee.getId(),
                attendee.getAttendeeName(),
                attendee.getDescription(),
                attendee.isStatus()
        );
    }
}
