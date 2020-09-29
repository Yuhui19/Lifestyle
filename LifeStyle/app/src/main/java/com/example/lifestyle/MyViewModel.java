package com.example.lifestyle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MyViewModel extends ViewModel {
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> gender = new MutableLiveData<>();
    private MutableLiveData<String> age = new MutableLiveData<>();
    private MutableLiveData<String> height = new MutableLiveData<>();
    private MutableLiveData<String> weight = new MutableLiveData<>();
    private MutableLiveData<String> country = new MutableLiveData<>();
    private MutableLiveData<String> city = new MutableLiveData<>();

    public void setName(String _name) {
        name.setValue(_name);
    }

    public void setGender(String _gender) {
        gender.setValue(_gender);
    }

    public void setAge(String _age) {
        age.setValue(_age);
    }

    public void setHeight(String _height) {
        height.setValue(_height);
    }

    public void setWeight(String _weight) {
        weight.setValue(_weight);
    }

    public void setCountry(String _country) {
        country.setValue(_country);
    }

    public void setCity(String _city) {
        city.setValue(_city);
    }


    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getGender() {
        return gender;
    }

    public LiveData<String> getAge() {
        return age;
    }

    public LiveData<String> getHeight() {
        return height;
    }
    public LiveData<String> getWeight() {
        return weight;
    }

    public LiveData<String> getCountry() {
        return country;
    }

    public LiveData<String> getCity() {
        return city;
    }



}
