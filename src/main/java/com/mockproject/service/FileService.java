package com.mockproject.service;

import com.mockproject.dto.FileClassResponseDTO;
import com.mockproject.exception.file.FileRequestException;
import com.mockproject.repository.*;
import com.mockproject.service.interfaces.IFileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Time;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService implements IFileService {

    private final LocationRepository locationRepository;
    private final FsuRepository fsuRepository;
    private final ContactRepository contactRepository;
    private final TrainingProgramRepository trainingProgramRepository;
    private final AttendeeRepository attendeeRepository;
    private final UserRepository userRepository;

    @Override
    public FileClassResponseDTO readFileCsv(MultipartFile file) throws IOException {
        String[] HEADERS = {"Class Name", "Start Date (yyyy-mm-dd)", "Start Time (hh:mm:ss)", "End Time (hh:mm:ss)",
                "Duration Hour", "Duration Day", "Planned amount", "Accepted amount", "Actual amount", "Location",
                "FSU", "Contact Email", "Training Program Name", "Attendee", "Admin Email (divine by / )"
        };
        CSVParser parser = CSVParser.parse(file.getInputStream(),
                                            Charset.defaultCharset(),
                                            CSVFormat.DEFAULT.builder().setHeader(HEADERS)
                                            .setSkipHeaderRecord(true).build());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        CSVRecord record = parser.getRecords().get(0);

        LocalDate startDate;
        Time startTime;
        Time endTime;
        String className;
        BigDecimal hour;
        int day;
        int planned;
        int accepted;
        int actual;
        String locationName;
        String fsuName;
        String contactEmail;
        String trainingProgramName;
        String attendeeName;

        try {
            startDate = LocalDate.parse(record.get(1), formatter);
            startTime = Time.valueOf(record.get(2));
            endTime = Time.valueOf(record.get(3));
        } catch (DateTimeException dateTimeException) {
            throw new DateTimeParseException("Date time doesn't match with format!", "yyyy/mm/dd", 0);
        }
        try {
            className = record.get(0);
            hour = new BigDecimal(record.get(4));
            day = Integer.parseInt(record.get(5));
            planned = Integer.parseInt(record.get(6));
            accepted = Integer.parseInt(record.get(7));
            actual = Integer.parseInt(record.get(8));
            locationName = record.get(9);
            fsuName = record.get(10);
            contactEmail = record.get(11);
            trainingProgramName = record.get(12);
            attendeeName = record.get(13);
        } catch (Exception e) {
            throw new FileRequestException("Data of record is not valid! Please check your input data!");
        }

        String[] listAdminEmail = record.get(14).split("/");
        Long locationId = locationRepository.findFirstByLocationNameAndStatus(locationName, true).orElseThrow().getId();
        Long fsuId = fsuRepository.findByFsuNameAndStatus(fsuName, true).orElseThrow().getId();
        Long contactId = contactRepository.findByContactEmailAndStatus(contactEmail, true).orElseThrow().getId();
        Long trainingProgramId = trainingProgramRepository.findFirstByNameAndStatus(trainingProgramName, true).orElseThrow().getId();
        Long attendeeId = attendeeRepository.findByAttendeeNameAndStatus(attendeeName, true).orElseThrow().getId();

        List<Long> listAdminId = Arrays.stream(listAdminEmail)
                .map(p ->
                        userRepository.findByEmailAndStatus(p, true)
                                .orElseThrow().getId()
                ).toList();

        return new FileClassResponseDTO(className, startDate, startTime, endTime, hour,
                day, planned, accepted, actual, locationId, fsuId,
                contactId, trainingProgramId, attendeeId, listAdminId);
    }

}
