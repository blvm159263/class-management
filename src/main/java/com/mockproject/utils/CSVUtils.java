package com.mockproject.utils;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.User;
import com.mockproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVUtils {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }
}
