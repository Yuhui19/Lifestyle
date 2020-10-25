package com.example.lifestyle;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class StepRecordViewModel extends AndroidViewModel {
    private MutableLiveData<List<StepRecordData>> stepRecordsData;
    private StepRecordRepository mStepRecordRepository;

    public StepRecordViewModel(Application application){
        super(application);
        mStepRecordRepository = new StepRecordRepository(application);
        stepRecordsData = mStepRecordRepository.getData();
    }

    // todo
    public void setStepRecordsData(StepRecordData stepRecordData){
        mStepRecordRepository.setStepRecordsData(stepRecordData);
    }

    public LiveData<List<StepRecordData>> getData(){
        return stepRecordsData;
    }
}
