package com.example.lifestyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.security.NetworkSecurityPolicy;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mbuttonEdit;
    private TextView mTvName;
    private TextView mTvCountry;
    private TextView mTvCity;
    private TextView mTvGender;
    private TextView mTvAge;
    private TextView mTvHeight;
    private TextView mTvWeight;
    private ImageView mIvThumbnail;
    private ImageButton mIbMap;
    private ImageButton mIbWeather;
    private ImageButton mIbBMI;
    private ImageButton mIbGoal;

    Map<String, String> userInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent receivedIntent = getIntent();
        Bundle userBundle = receivedIntent.getExtras();
        if (userBundle != null)
            onSaveUserInfo(userBundle);
        setContentView(R.layout.activity_main);

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (isTablet()) {
            fTrans.replace(R.id.module_info_fragment_tablet, new ProfileFragment(),"frag_profile");
            fTrans.commit();
        }

        mIbMap = (ImageButton) findViewById(R.id.ib_map);
        mIbMap.setOnClickListener(this);
        mIbWeather = (ImageButton) findViewById(R.id.ib_weather);
        mIbWeather.setOnClickListener(this);
        mIbBMI = (ImageButton) findViewById(R.id.ib_bmi);
        mIbBMI.setOnClickListener(this);
        mIbGoal = (ImageButton) findViewById(R.id.ib_goal);
        mIbGoal.setOnClickListener(this);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        if (userInfo.containsKey("name")) {
            savedInstanceState.putString("TV_NAME", userInfo.get("name"));
        }
        if (userInfo.containsKey("gender")) {
            savedInstanceState.putString("TV_GENDER", userInfo.get("gender"));
        }
        if (userInfo.containsKey("age")) {
            savedInstanceState.putString("TV_AGE", userInfo.get("age"));
        }
        if (userInfo.containsKey("height")) {
            savedInstanceState.putString("TV_HEIGHT", userInfo.get("height"));
        }
        if (userInfo.containsKey("weight")) {
            savedInstanceState.putString("TV_WEIGHT", userInfo.get("weight"));
        }
        if (userInfo.containsKey("country")) {
            savedInstanceState.putString("TV_COUNTRY", userInfo.get("country"));
        }
        if (userInfo.containsKey("city")) {
            savedInstanceState.putString("TV_CITY", userInfo.get("city"));
        }
        if (userInfo.containsKey("imagePath")) {
            savedInstanceState.putString("IV_IMAGE_PATH", userInfo.get("imagePath"));
        }

        // Always call the superclass so it can save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        Bundle bundle = new Bundle();
        bundle.putString("ET_NAME", savedInstanceState.getString("TV_NAME"));
        bundle.putString("ET_GENDER", savedInstanceState.getString("TV_GENDER"));
        bundle.putString("ET_AGE", savedInstanceState.getString("TV_AGE"));
        bundle.putString("ET_HEIGHT", savedInstanceState.getString("TV_HEIGHT"));
        bundle.putString("ET_WEIGHT", savedInstanceState.getString("TV_WEIGHT"));
        bundle.putString("ET_COUNTRY", savedInstanceState.getString("TV_COUNTRY"));
        bundle.putString("ET_CITY", savedInstanceState.getString("TV_CITY"));
        bundle.putString("IMAGE_PATH", savedInstanceState.getString("IV_IMAGE_PATH"));
        onSaveUserInfo(bundle);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.ib_map: {
                location(mIbMap);
                break;
            }
            case R.id.ib_weather: {
//                Toast.makeText(this, "test weather", Toast.LENGTH_SHORT).show();
//                weather();
                if (isTablet()) {
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.module_info_fragment_tablet, new WeatherFragment(),"frag_weather");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
                else {
                    mTvCountry = (TextView) findViewById(R.id.tv_country);
                    mTvCity = (TextView) findViewById(R.id.tv_city);
                    Intent intent = new Intent(this, WeatherActivity.class);
                    intent.putExtra("COUNTRY", mTvCountry.getText());
                    intent.putExtra("CITY", mTvCity.getText());
                    this.startActivity(intent);
                }
                break;
            }
            case R.id.ib_bmi: {
                if (isTablet()) {
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.module_info_fragment_tablet, new BMIFragment(),"frag_bmi");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
                else {
                    mTvHeight = (TextView) findViewById(R.id.tv_height);
                    mTvWeight = (TextView) findViewById(R.id.tv_weight);
                    Intent intent = new Intent(this, BMIActivity.class);
                    intent.putExtra("WEIGHT", mTvWeight.getText().toString());
                    intent.putExtra("HEIGHT", mTvHeight.getText().toString());
                    this.startActivity(intent);
                }
                break;
            }
            case R.id.ib_goal: {
                if (isTablet()) {
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.module_info_fragment_tablet, new GoalFragment(),"frag_goal");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
                else {
                    mTvGender = (TextView) findViewById(R.id.tv_gender);
                    mTvWeight = (TextView) findViewById(R.id.tv_weight);
                    mTvHeight = (TextView) findViewById(R.id.tv_height);
                    mTvAge = (TextView) findViewById(R.id.tv_age);
                    Intent intent = new Intent(this, GoalActivity.class);
                    intent.putExtra("GENDER", mTvGender.getText().toString());
                    if (mTvWeight != null)
                        intent.putExtra("WEIGHT", mTvWeight.getText().toString());
                    if (mTvHeight != null)
                        intent.putExtra("HEIGHT", mTvHeight.getText().toString());
                    if (mTvAge != null)
                        intent.putExtra("AGE", mTvAge.getText().toString());
                    this.startActivity(intent);
                }
                break;
            }
        }
    }

    public void onSaveUserInfo(Bundle bundle) {
        userInfo.clear();
        if (bundle.getString("ET_NAME") != null) userInfo.put("name", bundle.getString("ET_NAME"));
        if (bundle.getString("ET_GENDER") != null) userInfo.put("gender", bundle.getString("ET_GENDER"));
        if (bundle.getString("ET_AGE") != null) userInfo.put("age", bundle.getString("ET_AGE"));
        if (bundle.getString("ET_HEIGHT") != null) userInfo.put("height", bundle.getString("ET_HEIGHT"));
        if (bundle.getString("ET_WEIGHT") != null) userInfo.put("weight", bundle.getString("ET_WEIGHT"));
        if (bundle.getString("ET_COUNTRY") != null) userInfo.put("country", bundle.getString("ET_COUNTRY"));
        if (bundle.getString("ET_CITY") != null) userInfo.put("city", bundle.getString("ET_CITY"));
        if (bundle.getString("IMAGE_PATH") != null) userInfo.put("imagePath", bundle.getString("IMAGE_PATH"));

    }

    public Map<String, String> getUserInfo() {
        return userInfo;
    }

    public void location(View view) {
        Uri uri = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode("hikes"));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}