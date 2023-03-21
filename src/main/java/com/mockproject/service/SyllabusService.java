package com.mockproject.service;

import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.TrainingProgramSyllabus;
import com.mockproject.entity.User;
import com.mockproject.mapper.SyllabusMapper;
import com.mockproject.repository.SyllabusRepository;
import com.mockproject.repository.TrainingProgramSyllabusRepository;
import com.mockproject.service.interfaces.ISyllabusService;
import com.mockproject.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SyllabusService implements ISyllabusService {

    private final SyllabusRepository repository;
    private final SyllabusMapper mapper;
    private final SyllabusRepository syllabusRepository;

    private final TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;

    private static final String TEMPLATE_FILE_PATH = "file-format\\syllabus-template.csv";
    @Override
    public List<SyllabusDTO> listByTrainingProgramIdTrue(Long trainingProgramId) {
        TrainingProgram tp = new TrainingProgram();
        tp.setId(trainingProgramId);
        List<TrainingProgramSyllabus> listTPS = trainingProgramSyllabusRepository.findByTrainingProgramAndStatus(tp, true);
        List<Syllabus> listSyllabus = new ArrayList<>();
//        listTPS.forEach(p -> listSyllabus.add(syllabusRepository.findById(p.getSyllabus())));
//        if(listTPS.isEmpty()){
//            return null;
//        }
        listTPS.forEach(p -> listSyllabus.add(p.getSyllabus()));
        return listSyllabus.stream().map(SyllabusMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SyllabusDTO> readFileCsv(MultipartFile file, User user) throws IOException {
        final String[] HEADERS = {"Syllabus Name","Syllabus Code","Syllabus Version","Syllabus Level","Duration Hour","Duration Day","Attendee Amount","Technical Requirements","Course Objectives","Date Created","Last Date Modified","Quiz","Assignment","Final","Final Theory","Final Practice","GPA","Training Description","Retest Description","Marking Description","Waiver Criteria Description","Other Description","State","Status"};
        CSVParser parser = CSVParser.parse(file.getInputStream(), Charset.defaultCharset(), CSVFormat.DEFAULT.builder().setHeader(HEADERS).setSkipHeaderRecord(true).build());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyyy");
        List<CSVRecord> records = parser.getRecords();
        List<SyllabusDTO> syllabusDTOS = new ArrayList<>();
        for(CSVRecord record: records) {
            String name = record.get(0);
            String code = record.get(1);
            String version = record.get(2);
            String level = record.get(3);
            int attendee = Integer.parseInt(record.get(6));
            BigDecimal hour = new BigDecimal(record.get(4));
            int day = Integer.parseInt(record.get(5));
            String technicalRequirements = record.get(7);
            String courseObjectives = record.get(8);
            LocalDate dateCreated = LocalDate.parse(record.get(9), formatter);
            LocalDate lastDateModified = LocalDate.parse(record.get(10), formatter);
            BigDecimal quiz = new BigDecimal(Double.parseDouble(record.get(11)));
            BigDecimal assignment = new BigDecimal(Double.valueOf(record.get(12)));
            BigDecimal finalExam = new BigDecimal(Double.valueOf(record.get(13)));
            BigDecimal finalTheory = new BigDecimal(Double.valueOf(record.get(14)));
            BigDecimal finalPractice = new BigDecimal(Double.valueOf(record.get(15)));
            BigDecimal gpa = new BigDecimal(Double.parseDouble(record.get(16)));
            String trainingDes = record.get(17);
            String reTestDes = record.get(18);
            String markingDes = record.get(19);
            String waiverCriteriaDes = record.get(20);
            String otherDes = record.get(21);
            boolean status = true;
            boolean state = true;
            Long creatorId = user.getId();
            Long lastModifierId = user.getId();
            Syllabus syllabus = Syllabus.builder()
                    .name(name)
                    .code(code)
                    .version(version)
                    .level(level)
                    .attendee(attendee)
                    .hour(hour)
                    .day(day)
                    .technicalRequirements(technicalRequirements)
                    .courseObjectives(courseObjectives)
                    .dateCreated(dateCreated)
                    .lastDateModified(lastDateModified)
                    .quiz(quiz)
                    .assignment(assignment)
                    .finalExam(finalExam)
                    .finalTheory(finalTheory)
                    .finalPractice(finalPractice)
                    .gpa(gpa)
                    .trainingDes(trainingDes)
                    .reTestDes(reTestDes)
                    .markingDes(markingDes)
                    .waiverCriteriaDes(waiverCriteriaDes)
                    .otherDes(otherDes)
                    .state(state)
                    .status(status)
                    .creator(user)
                    .lastModifier(user)
                    .build();
            syllabusDTOS.add(mapper.toDTO(repository.save(syllabus)));
        }
        return syllabusDTOS;
    }

    public byte[] getTemplateCsvFile() throws IOException {
        byte[] bytes = FileUtils.getFileBytes(TEMPLATE_FILE_PATH);

        return bytes;
    }
}
