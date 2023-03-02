package com.mockproject.dto.mapper;

import com.mockproject.dto.SessionDTO;
import com.mockproject.entity.Session;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SessionDTOMapper implements Function<Session, SessionDTO> {

    @Override
    public SessionDTO apply(Session session) {
        return new SessionDTO(
                session.getId(),
                session.getSessionNumber(),
                session.isStatus(),
                session.getSyllabus().getId()
        );
    }
}