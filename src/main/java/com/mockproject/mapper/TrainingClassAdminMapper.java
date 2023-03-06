package com.mockproject.mapper;

import com.mockproject.dto.TrainingClassAdminDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassAdmin;
import com.mockproject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainingClassAdminMapper {

    TrainingClassAdminMapper INSTANCE = Mappers.getMapper(TrainingClassAdminMapper.class);

    @Mapping(target = "trainingClassId", source = "trainingClass.id")
    @Mapping(target = "adminId", source = "admin.id")
    TrainingClassAdminDTO toDTO(TrainingClassAdmin trainingClassAdmin);

    @Mapping(target = "trainingClass", source = "trainingClassId", qualifiedByName = "mapTrainingClass")
    @Mapping(target = "admin", source = "adminId", qualifiedByName = "mapAdmin")
    TrainingClassAdmin toEntity(TrainingClassAdminDTO dto);

    @Named("mapTrainingClass")
    default TrainingClass mapTrainingClass(long id) {
        TrainingClass trainingClass = new TrainingClass();
        trainingClass.setId(id);
        return trainingClass;
    }

    @Named("mapAdmin")
    default User mapAdmin(long id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
