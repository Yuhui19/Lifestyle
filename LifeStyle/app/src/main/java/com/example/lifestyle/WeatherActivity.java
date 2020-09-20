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
        mTvCurrentCity = (TextView) findViewById(R.id.tv_current_city);
        mTvCurrentCountry = (TextView) findViewById(R.id.tv_current_country);
        String location = null;

        //Get the intent that created this activity.
        Intent receivedIntent = getIntent();

        //Get the string data and change the profile textView if data is not null
        if (receivedIntent.getStringExtra("CITY") != null) {
            String receivedCity = receivedIntent.getStringExtra("CITY");
            receivedCity.toLowerCase();
            receivedCity.replaceAll(" ", "&");
            location = receivedCity;
            mTvCurrentCity.setText(receivedIntent.getStringExtra("CITY"));
        }

        if (receivedIntent.getStringExtra("COUNTRY") != null) {
            String receivedCountry = receivedIntent.getStringExtra("COUNTRY");
            receivedCountry.replaceAll(" ", "&");
            receivedCountry.toLowerCase();
            location += "," + receivedCountry;
            mTvCurrentCountry.setText(receivedIntent.getStringExtra("COUNTRY"));
        }



        if (location != null) {
            WeatherTask weatherTask = new WeatherTask();
            weatherTask.execute(location);
        }

    }




    private class WeatherTask extends AsyncTask<String, Integer, WeatherData> {

        @Override
        protected WeatherData doInBackground(String... locations) {
            URL weatherDataURL = null;
            try {
                weatherDataURL = NetworkUtils.buildURLFromString(locations[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String jsonWeatherData;
            WeatherData weatherData;

            try {
                jsonWeatherData = NetworkUtils.getDataFromURL(weatherDataURL);
                if (jsonWeatherData == null) {
                    return new WeatherData();
                } else {
                    weatherData = JSONWeatherUtils.getWeatherData(jsonWeatherData);
                    return weatherData;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }


        @Override
        protected void onPostExecute(WeatherData weatherData) {
            mTvWeekDay = (TextView) findViewById(R.id.tv_week_day);
            mTvCurrentCity = (TextView) findViewById(R.id.tv_current_city);
            mTvCurrentCountry = (TextView) findViewById(R.id.tv_current_country);
            mTvCurrentTemp = (TextView) findViewById(R.id.tv_current_temp);
            mTvCurrentWeather = (TextView) findViewById(R.id.tv_current_weather);
            mTvHumidityValue = (TextView) findViewById(R.id.humidity_value);
            mTvWindValue = (TextView) findViewById(R.id.wind_value);
            mIvCurrentWeather = (ImageView) findViewById(R.id.iv_current_weather);

            // set TextView and ImageView Value
            if (weatherData != null) {
                Calendar calendar = Calendar.getInstance();
                mTvWeekDay.setText(calendar.getTime().toString());
//                mTvCurrentCity.setText(weatherData.getLocationData().getCity());
//                mTvCurrentCountry.setText(weatherData.getLocationData().getCountry());
                String strDouble = String.format("%.1f", weatherData.getTemperature().getTemp());
                mTvCurrentTemp.setText(strDouble + "°C");
                mTvCurrentWeather.setText(weatherData.getCurrentCondition().getCondition());
                mTvHumidityValue.setText(Double.toString(weatherData.getCurrentCondition().getHumidity()) + "%");
                mTvWindValue.setText(Double.toString(weatherData.getWind().getSpeed()) + "mph");

                String condition = weatherData.getCurrentCondition().getCondition();

                if (condition.equals("Rain")) {
                    mIvCurrentWeather.setImageResource(R.drawable.rainy);
                }
                else if (condition.equals("Snow")) {
                    mIvCurrentWeather.setImageResource(R.drawable.snow);
                }
                else if (condition.equals("Clouds")) {
                    mIvCurrentWeather.setImageResource(R.drawable.cloudy);
                }
                else if (condition.equals("Clear")) {
                    mIvCurrentWeather.setImageResource(R.drawable.sunny);
                }
                else {
                    mIvCurrentWeather.setImageResource(R.drawable.windy);
                }

            }
            else {
                System.out.println("weather data is null!!!");
            }

        }
    }
}