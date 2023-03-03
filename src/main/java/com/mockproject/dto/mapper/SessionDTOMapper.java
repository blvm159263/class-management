package com.mockproject.dto.mapper;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.Syllabus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class SessionDTOMapper implements Function<Session, SessionDTO> {

    @Override
    public SessionDTO apply(Session session) {
        return new SessionDTO(
                session.getId(),
                session.getSessionNumber(),
                session.isStatus(),
                session.getSyllabus().getId(),
                session.getListUnit()
        );
    }

    public List<SessionDTO> toDTOs(List<Session> list){
        List<SessionDTO> sessionDTO = new ArrayList<>();

        list.forEach((i) -> sessionDTO.add(apply(i)));

        return sessionDTO;
    }
}
