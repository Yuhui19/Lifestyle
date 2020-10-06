package com.example.lifestyle;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<UserData> userData;
    private UserRepository mUserRepository;

    public UserViewModel(Application application, String id){
        super(application);
        mUserRepository = new UserRepository(application, id);
        userData = mUserRepository.getData();
    }

    // todo
    public void setLocation(String location, String id){
        mUserRepository.setLocation(location, id);
    }

    public LiveData<UserData> getData(){
        return userData;
    }
}
