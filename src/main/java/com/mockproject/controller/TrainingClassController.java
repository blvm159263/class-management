package com.mockproject.controller;

import com.mockproject.service.interfaces.ITrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/class")
public class TrainingClassController {

    private final ITrainingClassService trainingClassService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAll(@PathVariable("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAllDetails(id));
    }

    @GetMapping("/trainers")
    public ResponseEntity<?> getAllTrainers(@Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAllTrainers(id));
    }

    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmins(@Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAllAdmins(id));
    }

    @GetMapping("/creator")
    public ResponseEntity<?> getCreator(@Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getCreator(id));
    }

    @GetMapping("/towers")
    public ResponseEntity<?> getAllTowers(@Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAllTowers(id));
    }

    @GetMapping("/attendee")
    public ResponseEntity<?> getAttendeeName(@Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getAttendee(id));
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> getClassSchedule(@Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getClassSchedule(id));
    }

    @GetMapping("/fsu")
    public ResponseEntity<?> getClassFsu(@Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getFsu(id));
    }

    @GetMapping("/contact")
    public ResponseEntity<?> getClassContact(@Param("id") long id) {
        return ResponseEntity.ok(trainingClassService.getContact(id));
    }

    // Test get days before
    @GetMapping("/days-count")
    public ResponseEntity<?> getDaysCount(@Param("id") long id, @Param("date") LocalDate date) {
        return ResponseEntity.ok(trainingClassService.getDaysCount(id, date));
    }

    @GetMapping("/units-for-a-date")
    public ResponseEntity<?> getAllUnitsForADate(@Param("id") long id, @Param("date") LocalDate date) {
        return ResponseEntity.ok(trainingClassService.getAllUnitsForADate(id, date));
    }

    @GetMapping("/trainers-for-a-date")
    public ResponseEntity<?> getAllTrainersForADate(@Param("id") long id, @Param("date") LocalDate date) {
        return ResponseEntity.ok(trainingClassService.getAllTrainersForADate(id, date));
    }

    @GetMapping("/deliveryTypes-for-a-date")
    public ResponseEntity<?> getAllDeliveryTypesForADate(@Param("id") long id, @Param("date") LocalDate date) {
        return ResponseEntity.ok(trainingClassService.getAllDeliveryTypesForADate(id, date));
    }

    @GetMapping("/towers-for-a-date")
    public ResponseEntity<?> getAllTowersForADate(@Param("id") long id, @Param("date") LocalDate date) {
        return ResponseEntity.ok(trainingClassService.getAllTowersForADate(id, date));
    }
}
