package com.mockproject.mapper;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "levelId", source = "level.id")
    @Mapping(target = "attendeeId", source = "attendee.id")
    UserDTO toDTO(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", source = "roleId", qualifiedByName = "mapRole")
    @Mapping(target = "level", source = "levelId", qualifiedByName = "mapLevel")
    @Mapping(target = "attendee", source = "attendeeId", qualifiedByName = "mapAttendee")
    User toEntity(UserDTO dto);

    @Named("mapRole")
    default Role mapRole(long id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }

    @Named("mapLevel")
    default Level mapLevel(long id) {
        Level level = new Level();
        level.setId(id);
        return level;
    }

    @Named("mapAttendee")
    default Attendee mapAttendee(long id) {
        Attendee attendee = new Attendee();
        attendee.setId(id);
        return attendee;
    }

}
