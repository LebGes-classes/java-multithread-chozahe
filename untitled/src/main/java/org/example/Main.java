package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            WorkDay workDay = new WorkDay();
            String inputFilePath = "employeeTasks.xlsx";
            String outputFilePath = "output.xlsx";

            workDay.loadEmployees(inputFilePath);
            workDay.startWorkDay();
            workDay.saveEmployeesData(outputFilePath);
            workDay.generateStatistics();

        } catch (IOException e) {
            e.printStackTrace();
        }
}
}