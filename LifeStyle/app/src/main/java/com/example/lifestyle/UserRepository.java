package com.example.lifestyle;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.lang.ref.WeakReference;

public class UserRepository {
    private final MutableLiveData<UserData> userData =
            new MutableLiveData<UserData>();
    private UserDao mUserDao;

    UserRepository(Application application, String id){
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        loadData(id);
    }

    public void setLocation(String location, String id){
        // todo: update user info in database

        loadData(id);
    }



    public MutableLiveData<UserData> getData() {
        return userData;
    }


    private void loadData(String id){
        WeakReference<UserRepository> mUserRepositoryReference = new WeakReference<>(this);
        UserRepository ref = mUserRepositoryReference.get();

        // todo: get user data and construct a user instance from database
        UserData userData = new UserData();


        ref.userData.setValue(userData);
    }
}
