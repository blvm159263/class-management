package com.mockproject.service.interfaces;

import com.mockproject.dto.LocationDTO;

import java.util.List;

public interface ILocationService {

    List<LocationDTO> getAllLocation(boolean status);

    LocationDTO getLocationById(boolean status, long id);
}
