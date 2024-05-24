package org.example;

import java.util.ArrayList;
import java.util.List;

public class Employee implements Runnable{
    private int id;
    private String name;
    private String position;
    private List<Task> tasks;
    private Task currentTask;
    private int workedHours;
    private int idleHours;
    private int tasksCompleted;
    private double efficiency;

    public Employee(int id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.workedHours = 0;
        this.idleHours = 0;
        this.tasksCompleted = 0;
        this.efficiency = 0.0;
        this.tasks = new ArrayList<>();
    }

    public void startTask(Task task) {
        tasks.add(task);
        if (currentTask == null) {
            currentTask = tasks.get(0);
        }
    }

    public void workOnTask(int hours) {
        if (currentTask != null) {
            int timeSpent = Math.min(hours, currentTask.getRemainingTime());
            currentTask.updateRemainingTime(timeSpent);
            workedHours += timeSpent;
            System.out.printf("Working on task. Time spent: %d, Remaining time: %d%n", timeSpent, currentTask.getRemainingTime());
            if (currentTask.getRemainingTime() == 0) {
                finishTask();

                // Переходим к следующей задаче, если есть
                if (tasks.size() > 1) {
                    tasks.remove(0); // Удаляем выполненную задачу из списка
                    currentTask = tasks.get(0); // Присваиваем следующую задачу
                } else {
                    currentTask = null; // Если больше нет задач, сбрасываем currentTask
                }
            }
        } else {
            idleHours += hours;
            System.out.printf("Idle for %d hours%n", hours);
        }
    }

    public void finishTask() {
        tasksCompleted++;
        currentTask = null;
    }

    public void calculateEfficiency() {
        int totalHours = workedHours + idleHours;
        if (totalHours > 0) {
            efficiency = (double) workedHours / totalHours;
        }
    }

    public String getStatistics() {
        calculateEfficiency();
        return String.format("ID: %d, Name: %s, Position: %s, Worked Hours: %d, Idle Hours: %d, Tasks Completed: %d, Efficiency: %.2f%%",
                id, name, position, workedHours, idleHours, tasksCompleted, efficiency * 100);
    }

    @Override
    public void run() {
        int currentHour = 0;
        while (currentHour < 8 && WorkDay.isWorkDayRunning()) { // Рабочий день 8 часов
            if (currentTask != null) {
                workOnTask(1); // Работаем один час
            } else {
                // Увеличиваем idleHours только если текущей задачи нет
                idleHours += 1;
            }
            currentHour++;
            try {
                Thread.sleep(1000); // Имитация одной секунды работы
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
