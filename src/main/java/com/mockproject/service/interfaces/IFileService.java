package com.mockproject.service.interfaces;

import com.mockproject.exception.FileException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface   IFileService {
    public CSVParser readFile(MultipartFile file, String encodingType,String separator);

}
