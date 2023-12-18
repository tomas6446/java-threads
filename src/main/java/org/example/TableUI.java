package org.example;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TableUI {
    public void promptUser(List<Record> sharedList) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Show Records");
            System.out.println("2. Filter by Birth Date");
            System.out.println("3. Sort Alphabetically");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    AsciiTablePrinter.printTable(sharedList);
                    break;
                case 2:
                    filterByBirthDate(sharedList, scanner);
                    break;
                case 3:
                    sortAlphabetically(sharedList);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void filterByBirthDate(List<Record> sharedList, Scanner scanner) {
        System.out.print("Enter start date (yyyy-mm-dd): ");
        String startDateStr = scanner.next();
        System.out.print("Enter end date (yyyy-mm-dd): ");
        String endDateStr = scanner.next();

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        List<Record> filteredList = sharedList.stream()
                .filter(record -> !record.getBirthDate().isBefore(startDate) && !record.getBirthDate().isAfter(endDate))
                .collect(Collectors.toList());

        AsciiTablePrinter.printTable(filteredList);
    }

    private void sortAlphabetically(List<Record> sharedList) {
        List<Record> sortedList = sharedList.stream()
                .sorted(Comparator.comparing(Record::getLastName).thenComparing(Record::getFirstName))
                .collect(Collectors.toList());

        AsciiTablePrinter.printTable(sortedList);
    }
}
