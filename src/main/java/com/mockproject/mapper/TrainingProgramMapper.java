package com.mockproject.mapper;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainingProgramMapper {

    TrainingProgramMapper INSTANCE = Mappers.getMapper(TrainingProgramMapper.class);

    @Mapping(target = "lastModifierId", source = "lastModifier.id")
    @Mapping(target = "creatorName", source = "creator.fullName")
//    @Mapping(target = "name", source = "name")
    TrainingProgramDTO toDTO(TrainingProgram trainingProgram);

    @Mapping(target = "lastModifier", source = "lastModifierId", qualifiedByName = "mapModifier")
    @Mapping(target = "creator", source = "creatorName", qualifiedByName = "mapCreator")
    TrainingProgram toEntity(TrainingProgramDTO dto);

    @Named("mapCreator")
    default User mapCreator(String name) {
        User user = new User();
        user.setFullName(name);
        return user;
    }

    @Named("mapModifier")
    default User mapModifier(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
