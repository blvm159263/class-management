package com.mockproject.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CSVUtils {
    public static ByteArrayInputStream getCSVUserFileExample() {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            List<String> header = Arrays.asList("Email","Gender","Date of birth","Image link","Password","Phone","State","Attendee","Level","Role");
            List<String> data1 = Arrays.asList("example1@gmail.com","Male","2001-06-21","imagelink1","password1","01123456789","In class","Fresher","AA","Trainer");
            List<String> data2 = Arrays.asList("example2@gmail.com","Female","2001-06-21","imagelink2","password2","01123456789","In class","Fresher","AA","Trainer");
            csvPrinter.printRecord(header);
            csvPrinter.printRecord(data1);
            csvPrinter.printRecord(data2);
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
