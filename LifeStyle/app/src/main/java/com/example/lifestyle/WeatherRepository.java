package com.example.lifestyle;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherRepository {
    private final MutableLiveData<WeatherData> jsonData =
            new MutableLiveData<WeatherData>();
    private String mLocation;

    WeatherRepository(Application application){
        loadData();
    }

    public void setLocation(String location){
        mLocation = location;
        loadData();
    }

    public MutableLiveData<WeatherData> getData() {
        return jsonData;
    }

    private void loadData(){
        new LoadTask(this).execute(mLocation);
    }

    private static class LoadTask extends AsyncTask<String,Void,String> {
        private WeakReference<WeatherRepository> mWeatherRepositoryReference;

        LoadTask(WeatherRepository context)
        {
            mWeatherRepositoryReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            String location = strings[0];
            URL weatherDataURL = null;
            String retrievedJsonData = null;
            if(location!=null) {
                try {
                    weatherDataURL = NetworkUtils.buildURLFromString(location);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    retrievedJsonData = NetworkUtils.getDataFromURL(weatherDataURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return retrievedJsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null) {
                try {
                    WeatherRepository ref = mWeatherRepositoryReference.get();
                    ref.jsonData.setValue(JSONWeatherUtils.getWeatherData(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };
}
