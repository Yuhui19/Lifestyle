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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
    private WeatherViewModel mWeatherViewModel;
    private UserViewModel mUserViewModel;

    // HashMap used to store user's info in memory
    String location;

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
        //Create the view model
        mWeatherViewModel = new ViewModelProviders().of(getActivity()).get(WeatherViewModel.class);
        mUserViewModel = new ViewModelProviders().of(this).get(UserViewModel.class);

        System.out.println("We are creating the weather fragment!!!");

        //Set the observer for ViewModel
        mUserViewModel.getData().observe(getActivity(), userObserver);

        return view;
    }

    //create an observer that watches the LiveData<WeatherData> object
    final Observer<UserData> userObserver  = new Observer<UserData>() {
        @Override
        public void onChanged(@Nullable final UserData userData) {
            // Update the UI if this data variable changes
            if(userData!=null) {
                if (userData.getCity() != null && !userData.getCity().matches("-------")) {
                    String receivedCity = userData.getCity();
                    receivedCity.toLowerCase();
                    receivedCity.replaceAll(" ", "&");
                    location = receivedCity;
                    mTvCurrentCity.setText(userData.getCity());

                    if (userData.getCountry() != null && !userData.getCountry().matches("-------")) {
                        String receivedCountry = userData.getCountry();
                        receivedCountry.replaceAll(" ", "&");
                        receivedCountry.toLowerCase();
                        location += "," + receivedCountry;
                        mTvCurrentCountry.setText(userData.getCountry());
                    }

                    if (location == null) {
                        location = "salt&lake&city,us";
                        mTvCurrentCity.setText("Salt Lake City");
                        mTvCurrentCountry.setText("US");
                    }

                    // set the location for ViewModel
                    mWeatherViewModel.setLocation(location);

                    //Set the observer for ViewModel
                    mWeatherViewModel.getData().observe(getActivity(),nameObserver);
                }
            }
        }
    };



    //create an observer that watches the LiveData<WeatherData> object
    final Observer<WeatherData> nameObserver  = new Observer<WeatherData>() {
        @Override
        public void onChanged(@Nullable final WeatherData weatherData) {
            // Update the UI if this data variable changes
            if(weatherData!=null) {
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
        }
    };



    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
