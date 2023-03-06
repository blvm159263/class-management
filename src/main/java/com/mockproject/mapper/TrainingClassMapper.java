package com.mockproject.mapper;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainingClassMapper {

    TrainingClassMapper INSTANCE = Mappers.getMapper(TrainingClassMapper.class);

    @Mapping(target = "trainingProgramId", source = "trainingProgram.id")
    @Mapping(target = "reviewerId", source = "reviewer.id")
    @Mapping(target = "lastModifierId", source = "lastModifier.id")
    @Mapping(target = "fsuId", source = "fsu.id")
    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "attendeeId", source = "attendee.id")
    @Mapping(target = "approverId", source = "approver.id")
    TrainingClassDTO toDTO(TrainingClass trainingClass);

    @Mapping(target = "trainingProgram", source = "trainingProgramId", qualifiedByName = "mapTrainingProgram")
    @Mapping(target = "reviewer", source = "reviewerId", qualifiedByName = "mapReviewer")
    @Mapping(target = "lastModifier", source = "lastModifierId", qualifiedByName = "mapModifier")
    @Mapping(target = "fsu", source = "fsuId", qualifiedByName = "mapFsu")
    @Mapping(target = "creator", source = "creatorId", qualifiedByName = "mapCreator")
    @Mapping(target = "contact", source = "contactId", qualifiedByName = "mapContact")
    @Mapping(target = "attendee", source = "attendeeId", qualifiedByName = "mapAttendee")
    @Mapping(target = "approver", source = "approverId", qualifiedByName = "mapApprover")
    TrainingClass toEntity(TrainingClassDTO dto);

    @Named("mapTrainingProgram")
    default TrainingProgram mapTrainingProgram(long id) {
        TrainingProgram trainingProgram = new TrainingProgram();
        trainingProgram.setId(id);
        return trainingProgram;
    }

    @Named("mapReviewer")
    default User mapReviewer(long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapModifier")
    default User mapModifier(long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapContact")
    default Contact mapContact(long id) {
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }

    @Named("mapCreator")
    default User mapCreator(long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapFsu")
    default Fsu mapFsu(long id) {
        Fsu fsu = new Fsu();
        fsu.setId(id);
        return fsu;
    }

    @Named("mapApprover")
    default User mapApprover(long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapAttendee")
    default Attendee mapAttendee(long id) {
        Attendee attendee = new Attendee();
        attendee.setId(id);
        return attendee;
    }


}