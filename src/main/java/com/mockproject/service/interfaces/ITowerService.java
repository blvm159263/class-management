package com.mockproject.service.interfaces;

import com.mockproject.dto.TowerDTO;

import java.util.List;

public interface ITowerService {

    List<TowerDTO> listByTowerIdTrue(Long id);
}
