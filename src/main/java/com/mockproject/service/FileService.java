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
    private final String COMMA = "comma";
    private final String PIPE = "pipe";
    private final String SPACE = " ";
    private final String SEMICOLON = "semicolon";

    @Override
    public CSVParser readFile(MultipartFile file, String encodingType,String separator) {
        encodingType = encodingType.trim().toLowerCase();
        separator= separator.trim().toLowerCase();
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

            switch (separator) {
                case COMMA:
                    separator=",";
                    break;
                case SEMICOLON:
                    separator=";";
                    break;
                case PIPE:
                    separator="|";
                    break;
                case SPACE:
                    separator=" ";
                    break;
                default:
                    throw new FileException("separator type is wrong ", HttpStatus.BAD_REQUEST.value());
            }

        try {
            reader = new BufferedReader(inputStreamReader);
            parser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).build().withDelimiter(separator.charAt(0)));
        } catch (IOException e) {
            throw new FileException("Content is wrong",HttpStatus.BAD_REQUEST.value());
        }
        return parser;
    }


}
