package com.mockproject.service.interfaces;

import com.mockproject.dto.SessionDTO;
import com.mockproject.entity.Session;
import com.mockproject.entity.User;

import java.io.IOException;
import java.util.List;

import com.mockproject.dto.SessionDTO;

import java.util.List;

public interface ISessionService {

    List<SessionDTO> listBySyllabus(Long sid);

    List<SessionDTO> getAllSessionBySyllabusId(long syllabusId, boolean status);

    boolean createSession(long syllabusId, List<SessionDTO> listSession, User user);

    boolean createSession(long syllabusId, SessionDTO sessionDTO, User user);

    Session editSession(SessionDTO sessionDTO, boolean status) throws IOException;

    boolean deleteSession(long sessionId, boolean status);

    boolean deleteSessions(long syllabusId, boolean status);

}
