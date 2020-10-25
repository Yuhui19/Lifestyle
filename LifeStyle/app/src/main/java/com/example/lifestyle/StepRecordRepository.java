package com.example.lifestyle;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class StepRecordRepository {
    private final MutableLiveData<List<StepRecordData>> stepRecordsData = new MutableLiveData<>();
    private StepRecordDao mStepRecordDao;

    StepRecordRepository(Application application){
        StepRecordRoomDatabase db = StepRecordRoomDatabase.getDatabase(application);
        mStepRecordDao = db.stepRecordDao();
        String username = UserSession.getInstance().getSessionId();
        new getStepRecordsAsyncTask(this, mStepRecordDao).execute(username);
    }

//    private void insert(){
//        UserTable userTable = new UserTable("123", null, null, null, null,
//                null, null, null, null);
//        new insertAsyncTask(mWeatherDao).execute(weatherTable);
//    }

    public void setStepRecordsData(StepRecordData stepRecordData){
        // update user info in database
        String username = UserSession.getInstance().getSessionId();
        String startTime = stepRecordData.getStartTime();
        String endTime = stepRecordData.getEndTime();
        int stepCount = stepRecordData.getStepCount();

        StepRecordTable stepRecordTable = new StepRecordTable(username, startTime, endTime, stepCount);

        new insertStepRecordAsyncTask(mStepRecordDao).execute(stepRecordTable);


        // update userData
        WeakReference<StepRecordRepository> mStepRecordRepositoryReference = new WeakReference<>(this);
        StepRecordRepository ref = mStepRecordRepositoryReference.get();
        List<StepRecordData> prev_step_records = ref.stepRecordsData.getValue();
        prev_step_records.add(stepRecordData);
        ref.stepRecordsData.setValue(prev_step_records);
//        ref.stepRecordsData.getValue().add(stepRecordData);
    }



    public MutableLiveData<List<StepRecordData>> getData() {
//        String username = UserSession.getInstance().getSessionId();
//        new getStepRecordsAsyncTask(this, mStepRecordDao).execute(username);
        return stepRecordsData;
    }


//    private void loadData(String id){
//        WeakReference<UserRepository> mUserRepositoryReference = new WeakReference<>(this);
//        UserRepository ref = mUserRepositoryReference.get();
//
//        // todo: get user data from the database according to id
//
//
//        // todo: construct a user instance from database
//        UserData userData = new UserData();
//
//
//        ref.userData.setValue(userData);
//    }

    private static class insertStepRecordAsyncTask extends AsyncTask<StepRecordTable,Void,Void> {
        private StepRecordDao mAsyncTaskDao;

        insertStepRecordAsyncTask(StepRecordDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(StepRecordTable... StepRecordTables) {
            mAsyncTaskDao.insertStepRecord(StepRecordTables[0]);
            return null;
        }
    }


    private static class getStepRecordsAsyncTask extends AsyncTask<String,Void,List<StepRecordTable>> {
        private WeakReference<StepRecordRepository> mRepoWReference;
        private StepRecordDao mAsyncTaskDao;

        getStepRecordsAsyncTask(StepRecordRepository repo, StepRecordDao dao) {
            mRepoWReference = new WeakReference<StepRecordRepository>(repo);
            mAsyncTaskDao = dao;
        }


        @Override
        protected List<StepRecordTable> doInBackground(String... strings) {
            List<StepRecordTable> stepRecordTable = mAsyncTaskDao.getByUsername(strings[0]);
            return stepRecordTable;
        }

        @Override
        protected void onPostExecute(List<StepRecordTable> stepRecordTable) {
            StepRecordRepository ref = mRepoWReference.get();


            List<StepRecordData> stepRecordsList = new ArrayList<>();

            for (StepRecordTable table : stepRecordTable) {
                StepRecordData stepRecordData = new StepRecordData();
                stepRecordData.setUsername(table.getUsername());
                stepRecordData.setStartTime(table.getStartTime());
                stepRecordData.setEndTime(table.getEndTime());
                stepRecordData.setStepCount(table.getStepCount());
                stepRecordsList.add(stepRecordData);
            }
            ref.stepRecordsData.setValue(stepRecordsList);

        }
    }
}
