package com.example.lifestyle;


import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface StepRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStepRecord(StepRecordTable stepRecordTable);

    @Query("DELETE FROM step_record_table")
    void deleteAll();

    @Query("SELECT * from step_record_table")
    List<StepRecordTable> getAll();

    @Query("SELECT * from step_record_table where username=(:username)")
    List<StepRecordTable> getByUsername(String username);

}