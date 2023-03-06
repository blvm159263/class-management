package com.mockproject.mapper;

import com.mockproject.dto.TowerDTO;
import com.mockproject.entity.Location;
import com.mockproject.entity.Tower;
import com.mockproject.entity.TrainingClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TowerMapper {

    TowerMapper INSTANCE = Mappers.getMapper(TowerMapper.class);

    @Mapping(target = "locationId", source = "location.id")
    TowerDTO toDTO(Tower tower);

    @Mapping(target = "location", source = "locationId", qualifiedByName = "mapLocation")
    Tower toEntity(TowerDTO dto);


    @Named("mapLocation")
    default Location mapLocation(long id) {
        Location location = new Location();
        location.setId(id);
        return location;
    }
}