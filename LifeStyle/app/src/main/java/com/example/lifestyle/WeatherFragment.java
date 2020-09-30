package com.example.lifestyle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

public class WeatherFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mTvCurrentCity = (TextView) view.findViewById(R.id.tv_current_city);
        mTvCurrentCountry = (TextView) view.findViewById(R.id.tv_current_country);
        mTvWeekDay = (TextView) view.findViewById(R.id.tv_week_day);
        mTvCurrentTemp = (TextView) view.findViewById(R.id.tv_current_temp);
        mTvCurrentWeather = (TextView) view.findViewById(R.id.tv_current_weather);
        mTvHumidityValue = (TextView) view.findViewById(R.id.humidity_value);
        mTvWindValue = (TextView) view.findViewById(R.id.wind_value);
        mIvCurrentWeather = (ImageView) view.findViewById(R.id.iv_current_weather);

        String location = null;
        Map<String, String> userInfo;

        if (isTablet()) {
            MainActivity mainActivity = (MainActivity) getActivity();
            userInfo = mainActivity.getUserInfo();
        }
        else {
            WeatherActivity weatherActivity = (WeatherActivity) getActivity();
            userInfo = weatherActivity.getUserInfo();
        }

        if (userInfo.get("city") != null) {
            String receivedCity = userInfo.get("city");
            receivedCity.toLowerCase();
            receivedCity.replaceAll(" ", "&");
            location = receivedCity;
            mTvCurrentCity.setText(userInfo.get("city"));
        }

        if (userInfo.get("country") != null) {
            String receivedCountry = userInfo.get("country");
            receivedCountry.replaceAll(" ", "&");
            receivedCountry.toLowerCase();
            location += "," + receivedCountry;
            mTvCurrentCountry.setText(userInfo.get("country"));
        }

        if (location != null) {
            WeatherFragment.WeatherTask weatherTask = new WeatherFragment.WeatherTask();
            weatherTask.execute(location);
        }

        return view;
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

            // set TextView and ImageView Value
            if (weatherData != null) {
                Calendar calendar = Calendar.getInstance();
                mTvWeekDay.setText(calendar.getTime().toString());

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
                Toast.makeText(getActivity(), "Enter country and city", Toast.LENGTH_SHORT).show();
                System.out.println("weather data is null!!!");
            }

        }
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
