package com.mockproject.service.interfaces;

import com.mockproject.entity.Location;

import java.util.List;

public interface ILocationService {

    public List<Location> findLocationByTrainingClassID(Long id);
}
