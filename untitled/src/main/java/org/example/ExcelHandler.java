package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler {

    public List<Employee> loadEmployees(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Пропуск заголовка

            int id = (int) row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();
            String position = row.getCell(2).getStringCellValue();

            Employee employee = new Employee(id, name, position);

            // Пропускаем задачи, которые не относятся к данному сотруднику
            if (row.getCell(3) != null && !row.getCell(3).getStringCellValue().isEmpty()) {
                String taskDescription = row.getCell(3).getStringCellValue();
                int duration = (int) row.getCell(4).getNumericCellValue();
                Task task = new Task(id, taskDescription, duration);
                employee.startTask(task); // Присваиваем задачу сотруднику
            }

            employees.add(employee);
        }

        fileInputStream.close();
        return employees;
    }

    public void saveEmployeesData(List<Employee> employees, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");

        int rowNum = 0;
        for (Employee employee : employees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getStatistics());
        }

        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }
}
