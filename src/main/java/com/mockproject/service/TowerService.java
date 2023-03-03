package com.mockproject.service;

import com.mockproject.dto.mapper.TowerDTOMapper;
import com.mockproject.repository.TowerRepository;
import com.mockproject.service.interfaces.ITowerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class TowerService implements ITowerService {

    private final TowerRepository repository;

    private final TowerDTOMapper mapper;
}
