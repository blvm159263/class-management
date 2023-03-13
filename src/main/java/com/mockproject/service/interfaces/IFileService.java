package com.mockproject.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    void readFileCsv(MultipartFile file) throws IOException;
}
