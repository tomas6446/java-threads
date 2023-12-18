package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Record> sharedList = getRecords();
        AsciiTablePrinter.printTable(sharedList);

        TableUI tableUI = new TableUI();
        tableUI.promptUser(sharedList);
    }

    private static List<Record> getRecords() {
        List<Record> sharedList = new ArrayList<>();

        // Create threads for each data file
        Thread thread1 = new Thread(new DataReader("MOCK_DATA1.csv", sharedList));
        Thread thread2 = new Thread(new DataReader("MOCK_DATA2.csv", sharedList));
        Thread thread3 = new Thread(new DataReader("MOCK_DATA3.csv", sharedList));

        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for all threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total records loaded: " + sharedList.size());
        return sharedList;
    }
}
