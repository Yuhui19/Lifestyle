package com.example.lifestyle;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "step_record_table", primaryKeys = {"username","start_time"})
public class StepRecordTable {
    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @NonNull
    @ColumnInfo(name = "start_time")
    private String startTime;

    @NonNull
    @ColumnInfo(name = "end_time")
    private String endTime;


    @NonNull
    @ColumnInfo(name = "step_count")
    private int stepCount;



    public StepRecordTable(@NonNull String username, @NonNull String startTime,
                           @NonNull String endTime, @NonNull int stepCount){
        this.username = username;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stepCount = stepCount;
    }

    // =================================   SETTERS

    public void setUsername(String username){
        this.username = username;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setStepCount(int stepCount){
        this.stepCount = stepCount;
    }



    // =================================   GETTERS
    public String getUsername(){
        return username;
    }
    public String getStartTime() { return startTime; }
    public String getEndTime(){
        return endTime;
    }
    public int getStepCount(){
        return stepCount;
    }



}
