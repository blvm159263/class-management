package com.mockproject.service;

import com.mockproject.entity.User;
import com.mockproject.repository.RoleRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    @Autowired
    private final UserRepository repository;

    @Autowired
    RoleRepository roleRepository;



    @Override
    public String readCSVFile(File file) {
        try {
            if (!file.exists()) {
                return "File not Found!";
            } else {
                List<User> userList = new ArrayList<>();

                // create a reader for the CSV file
                CSVReader reader = new CSVReader(new FileReader(file));

                // read the header row
                String[] headerRow = reader.readNext();

                // read the data rows and map them to Product objects
                String[] rowData;
                while ((rowData = reader.readNext()) != null) {
                    User user = new User();
                    user.setEmail(rowData[0]);
                    user.setPassword("123");
                    user.setFullName(rowData[1]);
                    user.setDob(LocalDate.parse(rowData[2], DateTimeFormatter.BASIC_ISO_DATE));
                    if (rowData[3].equals("Nam")) {
                        user.setGender(true);
                    } else {
                        user.setGender(false);
                    }
                    user.setPhone(rowData[4]);
                    user.setStatus(true);
                    user.setRole(roleRepository.findById(1L).get());
                    userList.add(user);
                    repository.save(user);

                }
                reader.close();
            }

        } catch (Exception e) {
            return String.valueOf(e);
        }

        return "Done";
    }
}
