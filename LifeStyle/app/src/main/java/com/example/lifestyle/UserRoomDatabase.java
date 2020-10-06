package com.example.lifestyle;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserTable.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase{
    private static volatile UserRoomDatabase mInstance;
    public abstract UserDao userDao();

    static synchronized UserRoomDatabase getDatabase(final Context context){
        if(mInstance==null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    UserRoomDatabase.class, "user.db").addCallback(sRoomDatabaseCallback).build();
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
        private final UserDao mDao;

        PopulateDbAsync(UserRoomDatabase db){
            mDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            UserTable weatherTable = new UserTable("123",null, null, null, null,
                    null, null, null, null);
            mDao.insert(weatherTable);
            return null;
        }
    }
}
