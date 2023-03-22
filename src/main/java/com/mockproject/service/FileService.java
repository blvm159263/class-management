package com.mockproject.service;

import com.mockproject.exception.FileException;
import com.mockproject.service.interfaces.IFileService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
@Service
public class FileService implements IFileService {
    private final String UTF_8 = "utf-8";
    private final String UTF_16 = "utf-16";
    private final String ISO_8859_1 = "iso_8859_1";
    private final String US_ASCII = "us_ascii";

    @Override
    public CSVParser readFile(MultipartFile file, String encodingType) {
        encodingType = encodingType.trim().toLowerCase();
        InputStreamReader inputStreamReader = null;
        BufferedReader reader;
        CSVParser parser;
        try {
            switch (encodingType) {
                case UTF_8:
                    inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
                    break;
                case UTF_16:
                    inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_16);
                    break;
                case ISO_8859_1:
                    inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.ISO_8859_1);
                    break;
                case US_ASCII:
                    inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.US_ASCII);
                    break;
                default:
                    inputStreamReader = new InputStreamReader(file.getInputStream());
            }
        } catch (IOException e) {
            throw new FileException("Encoding type is wrong ", HttpStatus.BAD_REQUEST.value());
        }
        try {
            System.out.println(inputStreamReader.getEncoding());
            reader = new BufferedReader(inputStreamReader);
            parser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).build());
        } catch (IOException e) {
            throw new FileException("Content is wrong",HttpStatus.BAD_REQUEST.value());
        }
        return parser;
    }


}
