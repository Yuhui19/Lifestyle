package com.example.lifestyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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

import java.util.Locale;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (isTablet()) {
            fTrans.replace(R.id.module_info_fragment_tablet, new ProfileFragment(),"frag_profile");
            fTrans.commit();
        }

        //Get the intent that created this activity.
        Intent receivedIntent = getIntent();

        //Get the string data and change the profile textView if data is not null
        String mEditReceived = receivedIntent.getStringExtra("EDIT");
        if (mEditReceived != null && mEditReceived.equals("true")) {
            String mNameReceived = receivedIntent.getStringExtra("ET_NAME");
            if (mNameReceived != null && !mNameReceived.matches("")) {
                mTvName = (TextView) findViewById(R.id.tv_username);
                mTvName.setText(mNameReceived);
            }

            String mGenderReceived = receivedIntent.getStringExtra("ET_GENDER");
            if (mGenderReceived != null && !mGenderReceived.matches("")) {
                mTvGender = (TextView) findViewById(R.id.tv_gender);
                mTvGender.setText(mGenderReceived);
            }

            String mAgeReceived = receivedIntent.getStringExtra("ET_AGE");
            if (mAgeReceived != null && !mAgeReceived.matches("")) {
                mTvAge = (TextView) findViewById(R.id.tv_age);
                mTvAge.setText(mAgeReceived);
            }

            String mHeightReceived = receivedIntent.getStringExtra("ET_HEIGHT");
            if (mHeightReceived != null && !mHeightReceived.matches("")) {
                mTvHeight = (TextView) findViewById(R.id.tv_height);
                mTvHeight.setText(mHeightReceived);
            }

            String mWeightReceived = receivedIntent.getStringExtra("ET_WEIGHT");
            if (mWeightReceived != null && !mWeightReceived.matches("")) {
                mTvWeight = (TextView) findViewById(R.id.tv_weight);
                mTvWeight.setText(mWeightReceived);
            }

            String mCountryReceived = receivedIntent.getStringExtra("ET_COUNTRY");
            String mCityReceived = receivedIntent.getStringExtra("ET_CITY");
            if (mCountryReceived != null && !mCountryReceived.matches("")) {
                mTvCountry = (TextView) findViewById(R.id.tv_country);
                mTvCountry.setText(mCountryReceived);
            }
            if (mCityReceived != null && !mCityReceived.matches("")) {
                mTvCity = (TextView) findViewById(R.id.tv_city);
                mTvCity.setText(mCityReceived);
            }


            String mImagePathReceived = receivedIntent.getStringExtra("IMAGE_PATH");
            if (mImagePathReceived != null && !mImagePathReceived.matches("")) {
                Bitmap thumbnailImage = BitmapFactory.decodeFile(mImagePathReceived);
                mIvThumbnail = (ImageView) findViewById(R.id.iv_user_profile);
                if (thumbnailImage != null){
                    mIvThumbnail.setImageBitmap(thumbnailImage);
                }
            }
        }

//        String mNameReceived = receivedIntent.getStringExtra("ET_NAME");
//        if (mNameReceived != null && !mNameReceived.matches("")) {
//            mTvName = (TextView) findViewById(R.id.tv_username);
//            mTvName.setText(mNameReceived);
//        }
//
//        String mGenderReceived = receivedIntent.getStringExtra("ET_GENDER");
//        if (mGenderReceived != null && !mGenderReceived.matches("")) {
//            mTvGender = (TextView) findViewById(R.id.tv_gender);
//            mTvGender.setText(mGenderReceived);
//        }
//
//        String mAgeReceived = receivedIntent.getStringExtra("ET_AGE");
//        if (mAgeReceived != null && !mAgeReceived.matches("")) {
//            mTvAge = (TextView) findViewById(R.id.tv_age);
//            mTvAge.setText(mAgeReceived);
//        }
//
//        String mHeightReceived = receivedIntent.getStringExtra("ET_HEIGHT");
//        if (mHeightReceived != null && !mHeightReceived.matches("")) {
//            mTvHeight = (TextView) findViewById(R.id.tv_height);
//            mTvHeight.setText(mHeightReceived);
//        }
//
//        String mWeightReceived = receivedIntent.getStringExtra("ET_WEIGHT");
//        if (mWeightReceived != null && !mWeightReceived.matches("")) {
//            mTvWeight = (TextView) findViewById(R.id.tv_weight);
//            mTvWeight.setText(mWeightReceived);
//        }
//
//        String mCountryReceived = receivedIntent.getStringExtra("ET_COUNTRY");
//        String mCityReceived = receivedIntent.getStringExtra("ET_CITY");
//        if ((mCountryReceived != null && !mCountryReceived.matches("")) ||
//                (mCityReceived != null && !mCityReceived.matches(""))) {
//            String location = "";
//            if (mCountryReceived != null && !mCountryReceived.matches("")) {
//                location += mCountryReceived;
//            }
//            if ((mCountryReceived != null && !mCountryReceived.matches("")) &&
//                    (mCityReceived != null && !mCityReceived.matches(""))) {
//                location += ",\n";
//            }
//            if (mCityReceived != null && !mCityReceived.matches("")) {
//                location += mCityReceived;
//            }
//            mTvLocation = (TextView) findViewById(R.id.tv_location);
//            mTvLocation.setText(location);
//        }
//
//
//        String mImagePathReceived = receivedIntent.getStringExtra("IMAGE_PATH");
//        if (mImagePathReceived != null && !mImagePathReceived.matches("")) {
//            Bitmap thumbnailImage = BitmapFactory.decodeFile(mImagePathReceived);
//            mIvThumbnail = (ImageView) findViewById(R.id.iv_user_profile);
//            if (thumbnailImage != null){
//                mIvThumbnail.setImageBitmap(thumbnailImage);
//            }
//        }

        if (savedInstanceState != null) {
            mTvName = (TextView) findViewById(R.id.tv_username);
            mTvGender = (TextView) findViewById(R.id.tv_gender);
            mTvAge = (TextView) findViewById(R.id.tv_age);
            mTvHeight = (TextView) findViewById(R.id.tv_height);
            mTvWeight = (TextView) findViewById(R.id.tv_weight);
            mTvCountry = (TextView) findViewById(R.id.tv_country);
            mTvCity = (TextView) findViewById(R.id.tv_city);

            mTvName.setText(savedInstanceState.getString("TV_NAME"));
            mTvGender.setText(savedInstanceState.getString("TV_GENDER"));
            mTvAge.setText(savedInstanceState.getString("TV_AGE"));
            mTvHeight.setText(savedInstanceState.getString("TV_HEIGHT"));
            mTvWeight.setText(savedInstanceState.getString("TV_WEIGHT"));
            mTvCountry.setText(savedInstanceState.getString("TV_COUNTRY"));
            mTvCity.setText(savedInstanceState.getString("TV_CITY"));
        }

        mbuttonEdit = (Button) findViewById(R.id.button_edit_profile);
        mbuttonEdit.setOnClickListener(this);
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
        // Save the user's current state
        mTvName = (TextView) findViewById(R.id.tv_username);
        mTvGender = (TextView) findViewById(R.id.tv_gender);
        mTvAge = (TextView) findViewById(R.id.tv_age);
        mTvHeight = (TextView) findViewById(R.id.tv_height);
        mTvWeight = (TextView) findViewById(R.id.tv_weight);
        mTvCountry = (TextView) findViewById(R.id.tv_country);
        mTvCity = (TextView) findViewById(R.id.tv_city);

        // savedInstanceState.putInt(KEY, VALUE);
        savedInstanceState.putString("TV_NAME", mTvName.getText().toString());
        savedInstanceState.putString("TV_GENDER", mTvGender.getText().toString());
        savedInstanceState.putString("TV_AGE", mTvAge.getText().toString());
        savedInstanceState.putString("TV_HEIGHT", mTvHeight.getText().toString());
        savedInstanceState.putString("TV_WEIGHT", mTvWeight.getText().toString());
        savedInstanceState.putString("TV_COUNTRY", mTvCountry.getText().toString());
        savedInstanceState.putString("TV_CITY", mTvCity.getText().toString());

        // Always call the superclass so it can save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mTvName = (TextView) findViewById(R.id.tv_username);
        mTvGender = (TextView) findViewById(R.id.tv_gender);
        mTvAge = (TextView) findViewById(R.id.tv_age);
        mTvHeight = (TextView) findViewById(R.id.tv_height);
        mTvWeight = (TextView) findViewById(R.id.tv_weight);
        mTvCountry = (TextView) findViewById(R.id.tv_country);
        mTvCity = (TextView) findViewById(R.id.tv_city);

        mTvName.setText(savedInstanceState.getString("TV_NAME"));
        mTvGender.setText(savedInstanceState.getString("TV_GENDER"));
        mTvAge.setText(savedInstanceState.getString("TV_AGE"));
        mTvHeight.setText(savedInstanceState.getString("TV_HEIGHT"));
        mTvWeight.setText(savedInstanceState.getString("TV_WEIGHT"));
        mTvCountry.setText(savedInstanceState.getString("TV_COUNTRY"));
        mTvCity.setText(savedInstanceState.getString("TV_CITY"));
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.button_edit_profile: {
                System.out.println("the user click the edit button");
                Intent editIntent = new Intent(this, EditActivity.class);
                mTvName = (TextView) findViewById(R.id.tv_username);
                mTvAge = (TextView) findViewById(R.id.tv_age);
                mTvHeight = (TextView) findViewById(R.id.tv_height);
                mTvWeight = (TextView) findViewById(R.id.tv_weight);
                mTvCountry = (TextView) findViewById(R.id.tv_country);
                mTvCity = (TextView) findViewById(R.id.tv_city);
                editIntent.putExtra("WEIGHT", mTvWeight.getText().toString());
                editIntent.putExtra("HEIGHT", mTvHeight.getText().toString());
//                editIntent.putExtra("GENDER", mTvGender.getText().toString());
                editIntent.putExtra("AGE", mTvAge.getText().toString());
                editIntent.putExtra("NAME", mTvName.getText().toString());
                editIntent.putExtra("COUNTRY", mTvCountry.getText().toString());
                editIntent.putExtra("CITY",  mTvCity.getText().toString());
                this.startActivity(editIntent);
                break;
            }
            case R.id.ib_map: {
                location(mIbMap);
                break;
            }
            case R.id.ib_weather: {
//                Toast.makeText(this, "test weather", Toast.LENGTH_SHORT).show();
//                weather();
                mTvCountry = (TextView) findViewById(R.id.tv_country);
                mTvCity = (TextView) findViewById(R.id.tv_city);
                Intent intent = new Intent(this, WeatherActivity.class);
                intent.putExtra("COUNTRY", mTvCountry.getText());
                intent.putExtra("CITY", mTvCity.getText());
                this.startActivity(intent);
                break;
            }
            case R.id.ib_bmi: {
                mTvHeight = (TextView) findViewById(R.id.tv_height);
                mTvWeight = (TextView) findViewById(R.id.tv_weight);
                Intent intent = new Intent(this, BMIActivity.class);
                intent.putExtra("WEIGHT", mTvWeight.getText().toString());
                intent.putExtra("HEIGHT", mTvHeight.getText().toString());
                this.startActivity(intent);
                break;
            }
            case R.id.ib_goal: {
                Intent intent = new Intent(this, GoalActivity.class);
//                if (mTvWeight != null && mTvHeight!= null && mTvAge != null) {
//                    intent.putExtra("WEIGHT", mTvWeight.getText().toString());
//                    intent.putExtra("HEIGHT", mTvHeight.getText().toString());
//                    intent.putExtra("GENDER", mTvGender.getText().toString());
//                    intent.putExtra("AGE", mTvAge.getText().toString());
//                }
                intent.putExtra("GENDER", mTvGender.getText().toString());
                if (mTvWeight != null)
                    intent.putExtra("WEIGHT", mTvWeight.getText().toString());
                if (mTvHeight != null)
                    intent.putExtra("HEIGHT", mTvHeight.getText().toString());
                if (mTvAge != null)
                    intent.putExtra("AGE", mTvAge.getText().toString());
                this.startActivity(intent);
                break;
            }
        }
    }

    public void location(View view) {
        Uri uri = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode("hikes"));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void weather() {
        ComponentName cn = new ComponentName("com.google.android.wearable.app", "com.google.android.clockwork.home.search.apps.weather.WeatherActivity");
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            intent.setComponent(cn);
            startActivity(intent);
        } catch(ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), "activity not found", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}