package com.mockproject.service;

import com.mockproject.dto.TowerDTO;
import com.mockproject.entity.Location;
import com.mockproject.mapper.TowerMapper;
import com.mockproject.repository.TowerRepository;
import com.mockproject.service.interfaces.ITowerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TowerService implements ITowerService {

    private final TowerRepository repository;

    @Override
    public List<TowerDTO> listByTowerIdTrue(long id) {
        Location location = new Location();
        location.setId(id);
        return repository.findByLocationAndStatus(location,true).stream().map(TowerMapper.INSTANCE::toDTO).toList();
    }
}
