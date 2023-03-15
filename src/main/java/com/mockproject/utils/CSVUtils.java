package com.mockproject.utils;

import com.mockproject.dto.UserDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CSVUtils {
    public static ByteArrayInputStream importUserExampleCSVFile() {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            List<String> data1 = Arrays.asList("example@gmail.com", "Nguyen Van A", "ImageLink", "1", "1999-12-01", "0123456789", "male", "enable", "Class Admin", "AA" ,"Fresher", "passwordexample1");
            List<String> data2 = Arrays.asList("example2@gmail.com", "Nguyen Van B", "ImageLink2", "1", "1999-12-02", "0123456789", "female", "disable", "Trainer", "CA" ,"Intern", "passwordexample2");
            csvPrinter.printRecord(data1);
            csvPrinter.printRecord(data2);
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }


}
