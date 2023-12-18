package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


public class DataReader implements Runnable {
    private final String filePath;
    private final List<Record> sharedList;

    public DataReader(String filePath, List<Record> sharedList) {
        this.filePath = filePath;
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVFormat csvFormat = CSVFormat.EXCEL
                    .withHeader("id", "first_name", "last_name", "email", "gender", "country", "domain", "bird_date")
                    .withSkipHeaderRecord(true);
            CSVParser csvParser = new CSVParser(reader, csvFormat);

            for (CSVRecord csvRecord : csvParser) {
                Record record = parseRecord(csvRecord);
                synchronized (sharedList) {
                    sharedList.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Record parseRecord(CSVRecord data) {
        Record record = new Record();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            int id = Integer.parseInt(data.get("id"));

            record.setId(id);
            record.setFirstName(data.get("first_name"));
            record.setLastName(data.get("last_name"));
            record.setEmail(data.get("email"));
            record.setGender(data.get("gender"));
            record.setCountry(data.get("country"));
            record.setDomainName(data.get("domain"));
            record.setBirthDate(LocalDate.parse(data.get("bird_date"), formatter));
        } catch (NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
        }
        return record;
    }
}
