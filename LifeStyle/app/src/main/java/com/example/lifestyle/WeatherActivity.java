package com.example.lifestyle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.security.NetworkSecurityPolicy;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeatherActivity extends AppCompatActivity {

    private String apiKey = "99ea8382701bd7481e5ea568772f739a";
    private String urlStr = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=" + apiKey;

    private TextView mTvWeekDay;
    private TextView mTvCurrentCity;
    private TextView mTvCurrentCountry;
    private TextView mTvCurrentTemp;
    private TextView mTvCurrentWeather;
    private TextView mTvHumidityValue;
    private TextView mTvWindValue;
    private ImageView mIvCurrentWeather;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setTitle("Weather");


    }


}