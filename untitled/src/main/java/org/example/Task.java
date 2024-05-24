package org.example;

public class Task {
    private int id;
    private String description;
    private int duration;
    private int remainingTime;

    public Task(int id, String description, int duration) {
        this.id = id;
        this.description = description;
        this.duration = duration;
        this.remainingTime = duration;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void updateRemainingTime(int hours) {
        remainingTime -= hours;
    }
}
