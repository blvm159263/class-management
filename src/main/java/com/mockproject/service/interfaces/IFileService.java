package com.mockproject.service.interfaces;

import com.mockproject.dto.FileClassResponseDTO;
import org.apache.commons.csv.CSVParser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    FileClassResponseDTO readFileCsv(MultipartFile file) throws IOException;

    public CSVParser readFile(MultipartFile file, String encodingType);

}
