package com.mockproject.service;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.SessionMapper;
import com.mockproject.repository.SessionRepository;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.ISessionService;
import com.mockproject.service.interfaces.IUnitService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;
    private final IUnitService unitService;
    private final SyllabusRepository syllabusRepository;
    private final UnitRepository unitRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, UnitService unitService, SyllabusRepository syllabusRepository, UnitRepository unitRepository) {
        this.sessionRepository = sessionRepository;
        this.unitService = unitService;
        this.syllabusRepository = syllabusRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public List<SessionDTO> getAllSessionBySyllabusId(long syllabusId, boolean status) {
        Optional<List<Session>> listSession = sessionRepository.findBySyllabusIdAndStatus(syllabusId, status);
        ListUtils.checkList(listSession);
        List<SessionDTO> sessionDTOList = new ArrayList<>();
        for (Session s: listSession.get()) {
            sessionDTOList.add(SessionMapper.INSTANCE.toDTO(s));
        }
        for (SessionDTO s: sessionDTOList){
            List<UnitDTO> unitDTOList = unitService.getAllUnitBySessionId(s.getId(), true);
            s.setUnitDTOList(unitDTOList);
        }
        return sessionDTOList;
    }

    @Override
    public boolean createSession(long syllabusId, List<SessionDTO> listSession, User user){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStateAndStatus(syllabusId, true,true);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        syllabus.get().setDay(listSession.size());
        syllabusRepository.save(syllabus.get());
        listSession.forEach((i) ->
        {
            createSession(syllabusId, i, user);
        });
        return true;
    }

    @Override
    public boolean createSession(long syllabusId, SessionDTO sessionDTO, User user){
        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStateAndStatus(syllabusId, true,true);
        syllabus.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));

        sessionDTO.setSyllabusId(syllabusId);
        Session session = sessionRepository.save(SessionMapper.INSTANCE.toEntity(sessionDTO));
        unitService.createUnit(session.getId(), sessionDTO.getUnitDTOList(), user);

        return true;
    }

    @Override
    public Session editSession(SessionDTO sessionDTO, boolean status) throws IOException{
        Optional<Session> session = sessionRepository.findByIdAndStatus(sessionDTO.getId(), status);
        session.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        sessionDTO.setSyllabusId(session.get().getSyllabus().getId());

        Optional<Syllabus> syllabus = syllabusRepository.findByIdAndStatus(session.get().getSyllabus().getId(), true);

        BigDecimal duration = syllabus.get().getHour();

        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (sessionDTO.isStatus() == true){
            for(UnitDTO u : sessionDTO.getUnitDTOList()){
                if(u.getId() == null){
                    unitService.createUnit(sessionDTO.getId(), u, user.getUser());
                } else {
                    unitService.editUnit(u, true);
                    if(u.isStatus() == true) {
                        Optional<Unit> unit = unitRepository.findByIdAndStatus(u.getId(), true);
                        duration = duration.add(unit.get().getDuration());
                        syllabus.get().setHour(duration);
                        syllabusRepository.save(syllabus.get());
                    }
                }

            }
        }else{
            unitService.deleteUnits(sessionDTO.getId(), true);
        }

        Session updateSession = sessionRepository.save(SessionMapper.INSTANCE.toEntity(sessionDTO));

        return updateSession;
    }

    @Override
    public boolean deleteSession(long sessionId, boolean status){
        Optional<Session> session = sessionRepository.findByIdAndStatus(sessionId, status);
        session.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        session.get().setStatus(false);
        System.out.println("Sessioln: "+sessionId);
        unitService.deleteUnits(sessionId, status);
        sessionRepository.save(session.get());
        return true;
    }

    @Override
    public boolean deleteSessions(long syllabusId, boolean status){
        Optional<List<Session>> sessions = sessionRepository.findBySyllabusIdAndStatus(syllabusId, status);
        ListUtils.checkList(sessions);
        sessions.get().forEach((i) -> deleteSession(i.getId(), status));
        return true;
    }

    @Override
    public List<Session> getSessionListBySyllabusId(long idSyllabus){
        return sessionRepository.getSessionListBySyllabusId(idSyllabus);
    }

    @Override
    public List<SessionDTO> listBySyllabus(Long sid) {
        Syllabus syllabus = new Syllabus();
        syllabus.setId(sid);
        return sessionRepository.findBySyllabus(syllabus).stream().map(SessionMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
