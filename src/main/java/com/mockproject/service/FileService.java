package com.mockproject.service;

import com.mockproject.service.interfaces.IFileService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class FileService implements IFileService {

    @Override
    public void readFileCsv(MultipartFile file) throws IOException {
        String[] HEADERS = {"header 1", "header 2", "header 3", "header 4", "header 5"};
        CSVParser parser = CSVParser.parse(file.getInputStream(),
                                            Charset.defaultCharset(),
                                            CSVFormat.DEFAULT.builder().setHeader(HEADERS)
                                                        .setSkipHeaderRecord(true).build());
        for (CSVRecord record: parser){
//            record.stream().toList()
//
//            System.out.println(c);
            record.forEach(System.out::println);
//            System.out.println(record.get(2));
        }
    }

}
