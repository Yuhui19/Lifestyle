package com.example.lifestyle;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<UserData> userData;
    private UserRepository mUserRepository;

    public UserViewModel(Application application){
        super(application);
        mUserRepository = new UserRepository(application);
        userData = mUserRepository.getData();
    }

    // todo
    public void setUserData(UserData userData){
        mUserRepository.setUserData(userData);
    }

    public LiveData<UserData> getData(){
        return userData;
    }
}
