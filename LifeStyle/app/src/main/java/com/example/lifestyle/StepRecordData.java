package com.example.lifestyle;

public class StepRecordData {
    private String username;
    private String startTime;
    private String endTime;
    private int stepCount;


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public int getStepCount() {
        return this.stepCount;
    }

    public String getUsername() {
        return this.username;
    }
}
