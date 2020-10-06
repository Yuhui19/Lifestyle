package com.example.lifestyle;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.lang.ref.WeakReference;

public class UserRepository {
    private final MutableLiveData<UserData> userData =
            new MutableLiveData<UserData>();
    private UserDao mUserDao;

    UserRepository(Application application){
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        new getUserAsyncTask(this, mUserDao).execute("123");
    }

//    private void insert(){
//        UserTable userTable = new UserTable("123", null, null, null, null,
//                null, null, null, null);
//        new insertAsyncTask(mWeatherDao).execute(weatherTable);
//    }

    public void setUserData(UserData userData){
        // todo: update user info in database
        String name = userData.getName();
        String gender = userData.getGender();
        String age = userData.getAge();
        String height = userData.getHeight();
        String weight = userData.getWeight();
        String country = userData.getCountry();
        String city = userData.getCity();
        String imagePath = userData.getImagePath();

        UserTable userTable = new UserTable("123", name, gender, age, weight,
                height, country, city, imagePath);

        new insertUserAsyncTask(mUserDao).execute(userTable);


        // update userData
        WeakReference<UserRepository> mUserRepositoryReference = new WeakReference<>(this);
        UserRepository ref = mUserRepositoryReference.get();
        ref.userData.setValue(userData);
    }



    public MutableLiveData<UserData> getData() {
        return userData;
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

    private static class insertUserAsyncTask extends AsyncTask<UserTable,Void,Void> {
        private UserDao mAsyncTaskDao;

        insertUserAsyncTask(UserDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(UserTable... UserTables) {
            mAsyncTaskDao.insertUser(UserTables[0]);
            return null;
        }
    }


    private static class getUserAsyncTask extends AsyncTask<String,Void,UserTable> {
        private WeakReference<UserRepository> mRepoWReference;
        private UserDao mAsyncTaskDao;

        getUserAsyncTask(UserRepository repo, UserDao dao) {
            mRepoWReference = new WeakReference<UserRepository>(repo);
            mAsyncTaskDao = dao;
        }


        @Override
        protected UserTable doInBackground(String... strings) {
            UserTable userTable = mAsyncTaskDao.getById(strings[0]);
            return userTable;
        }

        @Override
        protected void onPostExecute(UserTable userTable) {
            UserRepository ref = mRepoWReference.get();

            if (userTable != null) {
                UserData userData = new UserData();
                userData.setName(userTable.getName());
                userData.setGender(userTable.getGender());
                userData.setAge(userTable.getAge());
                userData.setWeight(userTable.getWeight());
                userData.setHeight(userTable.getHeight());
                userData.setCountry(userTable.getCountry());
                userData.setCity(userTable.getCity());
                userData.setImagePath(userTable.getImagePath());
                ref.userData.setValue(userData);
            }
        }
    }
}
