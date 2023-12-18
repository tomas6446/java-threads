package org.example;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

import java.util.List;

public class AsciiTablePrinter {

    public static void printTable(List<Record> records) {
        AsciiTable at = new AsciiTable();
        at.getRenderer().setCWC(new CWC_LongestLine()); // Configure column width strategy

        // Add table header
        at.addRule();
        at.addRow("ID", "First Name", "Last Name", "Email", "Gender", "Country", "Domain", "Birth Date");
        at.addRule();

        // Add rows from records
        for (Record record : records) {
            at.addRow(record.getId(), record.getFirstName(), record.getLastName(), record.getEmail(),
                    record.getGender(), record.getCountry(), record.getDomainName(), record.getBirthDate());
            at.addRule();
        }

        // Print table
        System.out.println(at.render());
    }
}
