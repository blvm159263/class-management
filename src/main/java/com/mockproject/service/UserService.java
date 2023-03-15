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
        List<User> userList = new ArrayList<>();
        try {
            if (!file.exists()) {
                return "File not Found!";
            } else {
                // create a reader for the CSV file
                CSVReader reader = new CSVReader(new FileReader(file));

                // read the header row
                String[] headerRow = reader.readNext();

                // read the data rows and map them to Product objects
                String[] rowData;
                while ((rowData = reader.readNext()) != null) {
                    System.out.println(rowData[0]);
                    System.out.println(rowData[1]);
                    System.out.println(rowData[2]);
                    System.out.println(rowData[3]);
                    System.out.println(rowData[4]);
                    System.out.println(rowData[5]);



                    User user = new User();
                    user.setEmail(rowData[0]);
                    user.setPassword("123");
                    user.setFullName(rowData[1]);
                    if (rowData[2].equals("Nam")) {
                        user.setGender(true);
                    } else {
                        user.setGender(false);
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    user.setDob(LocalDate.parse(rowData[3],formatter));
                    user.setPhone(rowData[4]);
                    user.setStatus(true);
                    userList.add(user);
                    repository.save(user);
                }
                reader.close();
            }

        } catch (Exception e) {
            return String.valueOf(e);
        }
        return userList.toString();

    }
}
