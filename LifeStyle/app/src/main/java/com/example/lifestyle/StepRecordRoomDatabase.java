package com.example.lifestyle;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {StepRecordTable.class}, version = 1, exportSchema = false)
public abstract class StepRecordRoomDatabase extends RoomDatabase{
    private static volatile StepRecordRoomDatabase mInstance;
    public abstract StepRecordDao stepRecordDao();

    static synchronized StepRecordRoomDatabase getDatabase(final Context context){
        if(mInstance==null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    StepRecordRoomDatabase.class, "step_records.db").addCallback(sRoomDatabaseCallback).build();
        }
        return mInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(mInstance).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
        private final StepRecordDao mDao;

        PopulateDbAsync(StepRecordRoomDatabase db){
            mDao = db.stepRecordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            StepRecordTable stepRecordTable = new StepRecordTable("123","2020/08/01 11:20:22", "2020/08/01 11:30:22", 10000);
            mDao.insertStepRecord(stepRecordTable);
            return null;
        }
    }
}