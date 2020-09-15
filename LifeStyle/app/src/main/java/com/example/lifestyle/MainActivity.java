package com.example.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mbuttonEdit;
    TextView mTvName;
    TextView mTvLocation;
    TextView mTvGender;
    TextView mTvAge;
    TextView mTvHeight;
    TextView mTvWeight;
    ImageView mIvThumbnail;
    ImageButton mIbMap;
    ImageButton mIbWeather;
    ImageButton mIbBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the intent that created this activity.
        Intent receivedIntent = getIntent();

        //Get the string data and change the profile textView if data is not null
        String mNameReceived = receivedIntent.getStringExtra("ET_NAME");
        if (mNameReceived != null && !mNameReceived.matches("")) {
            mTvName = (TextView) findViewById(R.id.tv_username);
            mTvName.setText(mNameReceived);
        }

//        String mGenderReceived = receivedIntent.getStringExtra("ET_GENDER");
//        if (mGenderReceived != null && !mGenderReceived.matches("")) {
//            mTvGender = (TextView) findViewById(R.id.tv_gender);
//            mTvGender.setText(mGenderReceived);
//        }

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
        if ((mCountryReceived != null && !mCountryReceived.matches("")) ||
                (mCityReceived != null && !mCityReceived.matches(""))) {
            String location = "";
            if (mCountryReceived != null && !mCountryReceived.matches("")) {
                location += mCountryReceived;
            }
            if ((mCountryReceived != null && !mCountryReceived.matches("")) &&
                    (mCityReceived != null && !mCityReceived.matches(""))) {
                location += ",\n";
            }
            if (mCityReceived != null && !mCityReceived.matches("")) {
                location += mCityReceived;
            }
            mTvLocation = (TextView) findViewById(R.id.tv_location);
            mTvLocation.setText(location);
        }


        String mImagePathReceived = receivedIntent.getStringExtra("IMAGE_PATH");
        if (mImagePathReceived != null && !mImagePathReceived.matches("")) {
            Bitmap thumbnailImage = BitmapFactory.decodeFile(mImagePathReceived);
            mIvThumbnail = (ImageView) findViewById(R.id.iv_user_profile);
            if (thumbnailImage != null){
                mIvThumbnail.setImageBitmap(thumbnailImage);
            }
        }

        mbuttonEdit = (Button) findViewById(R.id.button_edit_profile);
        mbuttonEdit.setOnClickListener(this);
        mIbMap = (ImageButton) findViewById(R.id.ib_map);
        mIbMap.setOnClickListener(this);
        mIbWeather = (ImageButton) findViewById(R.id.ib_weather);
        mIbWeather.setOnClickListener(this);
        mIbBMI = (ImageButton) findViewById(R.id.ib_bmi);
        mIbBMI.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current state
        mTvName = (TextView) findViewById(R.id.tv_username);
//        mTvGender = (TextView) findViewById(R.id.tv_gender);
        mTvAge = (TextView) findViewById(R.id.tv_age);
        mTvHeight = (TextView) findViewById(R.id.tv_height);
        mTvWeight = (TextView) findViewById(R.id.tv_weight);
        mTvLocation = (TextView) findViewById(R.id.tv_location);

        // savedInstanceState.putInt(KEY, VALUE);
        savedInstanceState.putString("TV_NAME", mTvName.getText().toString());
//        savedInstanceState.putString("TV_GENDER", mTvGender.getText().toString());
        savedInstanceState.putString("TV_AGE", mTvAge.getText().toString());
        savedInstanceState.putString("TV_HEIGHT", mTvHeight.getText().toString());
        savedInstanceState.putString("TV_WEIGHT", mTvWeight.getText().toString());
        savedInstanceState.putString("TV_LOCATION", mTvLocation.getText().toString());

        // Always call the superclass so it can save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mTvName = (TextView) findViewById(R.id.tv_username);
//        mTvGender = (TextView) findViewById(R.id.tv_gender);
        mTvAge = (TextView) findViewById(R.id.tv_age);
        mTvHeight = (TextView) findViewById(R.id.tv_height);
        mTvWeight = (TextView) findViewById(R.id.tv_weight);
        mTvLocation = (TextView) findViewById(R.id.tv_location);

        mTvName.setText(savedInstanceState.getString("TV_NAME"));
//        mTvGender.setText(savedInstanceState.getString("TV_GENDER"));
        mTvAge.setText(savedInstanceState.getString("TV_AGE"));
        mTvHeight.setText(savedInstanceState.getString("TV_HEIGHT"));
        mTvWeight.setText(savedInstanceState.getString("TV_WEIGHT"));
        mTvLocation.setText(savedInstanceState.getString("TV_LOCATION"));
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.button_edit_profile: {
                Intent messageIntent = new Intent(this, EditActivity.class);
                this.startActivity(messageIntent);
                break;
            }
            case R.id.ib_map: {
                location(mIbMap);
                break;
            }
            case R.id.ib_weather: {
//                Toast.makeText(this, "test weather", Toast.LENGTH_SHORT).show();
                weather();
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
}