package com.mockproject.dto.mapper;


import com.mockproject.dto.TowerDTO;
import com.mockproject.entity.Tower;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TowerDTOMapper implements Function<Tower, TowerDTO> {

    @Override
    public TowerDTO apply(Tower tower) {
        return new TowerDTO(
                tower.getId(),
                tower.getTowerName(),
                tower.getAddress(),
                tower.isStatus(),
                tower.getLocation().getId()
        );
    }
}
