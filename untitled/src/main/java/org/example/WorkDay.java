package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkDay {
    private static boolean workDayRunning = true; // Флаг для управления рабочим днем

    public static boolean isWorkDayRunning() {
        return workDayRunning;
    }

    private List<Employee> employees;
    private List<Thread> threads;

    public WorkDay() {
        this.employees = new ArrayList<>();
        this.threads = new ArrayList<>();
    }

    public void loadEmployees(String filePath) throws IOException {
        ExcelHandler excelHandler = new ExcelHandler();
        employees = excelHandler.loadEmployees(filePath);
    }

    public void startWorkDay() {
        for (Employee employee : employees) {
            Thread thread = new Thread(employee);
            threads.add(thread);
            thread.start();
        }

        // Ожидаем завершения работы всех сотрудников
        for (Thread thread : threads) {
            try {
                thread.join(); // Ожидаем завершения каждого потока сотрудника
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        endWorkDay(); // Завершаем рабочий день
    }

    public void endWorkDay() {
        workDayRunning = false; // Устанавливаем флаг завершения
    }

    public void saveEmployeesData(String filePath) throws IOException {
        ExcelHandler excelHandler = new ExcelHandler();
        excelHandler.saveEmployeesData(employees, filePath);
    }

    public void generateStatistics() {
        for (Employee employee : employees) {
            System.out.println(employee.getStatistics());
        }
    }
}