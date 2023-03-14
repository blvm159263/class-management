package com.mockproject.service.interfaces;

import com.mockproject.dto.SessionDTO;

import java.util.List;

public interface ISessionService {

    List<SessionDTO> listBySyllabus(Long sid);
}
