package com.mockproject.dto.mapper;


import com.mockproject.dto.LocationDTO;
import com.mockproject.entity.Location;

import java.util.function.Function;

public class LocationDTOMapper implements Function<Location, LocationDTO> {

    @Override
    public LocationDTO apply(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getLocationName(),
                location.getAddress(),
                location.isStatus()
        );
    }
}
