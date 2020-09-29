package com.example.lifestyle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    private EditText mEtName;
    private EditText mEtAge;
    private EditText mEtHeight;
    private EditText mEtWeight;
    private EditText mEtCountry;
    private EditText mEtCity;
    private Button mButtonCamera;
    private ImageView mIvProfileImage;

    private Spinner mSpinnerGender;
    private String mGender;
    private String GENDER_MALE = "Male";
    private String GENDER_FEMALE = "Female";
    private String GENDER_OTHERS = "Others";
    private String GENDER_UNKNOWN = "Unknown";

    private String mHeightReceived;
    private String mWeightReceived;
    private String mAgeReceived;
    private String mGenderReceived;
    private String mNameReceived;
    private String mCountryReceived;
    private String mCityReceived;
    private String mImagePathReceived;


    Map<String, String> userInfo = new HashMap<>();

    int hasName = 0;


    //Define a request code for the camera
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //Define a bitmap
    Bitmap mThumbnailImage;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent receivedIntent = getIntent();

        //Get the string data and change the profile textView if data is not null
        if (receivedIntent.getStringExtra("HEIGHT") != null) {
            mHeightReceived = receivedIntent.getStringExtra("HEIGHT");
            if (!mHeightReceived.equals("-------"))
                userInfo.put("height", mHeightReceived);
        }
        if (receivedIntent.getStringExtra("WEIGHT") != null) {
            mWeightReceived = receivedIntent.getStringExtra("WEIGHT");
            if (!mWeightReceived.equals("-------"))
                userInfo.put("weight", mWeightReceived);
        }
        if (receivedIntent.getStringExtra("NAME") != null) {
            mNameReceived = receivedIntent.getStringExtra("NAME");
            if (!mNameReceived.equals("-------"))
                userInfo.put("name", mNameReceived);
        }
        if (receivedIntent.getStringExtra("AGE") != null) {
            mAgeReceived = receivedIntent.getStringExtra("AGE");
            if (!mAgeReceived.equals("-------"))
                userInfo.put("age", mAgeReceived);
        }
        if (receivedIntent.getStringExtra("COUNTRY") != null) {
            mCountryReceived = receivedIntent.getStringExtra("COUNTRY");
            if (!mCountryReceived.equals("-------")) {
                userInfo.put("country", mCountryReceived);
            }
        }
        if (receivedIntent.getStringExtra("CITY") != null) {
            mCityReceived = receivedIntent.getStringExtra("CITY");
            if (!mCityReceived.equals("-------")) {
                userInfo.put("city", mCityReceived);
            }
        }

        if (receivedIntent.getStringExtra("IMAGE_PATH") != null) {
            mImagePathReceived = receivedIntent.getStringExtra("IMAGE_PATH");
            if (!mImagePathReceived.equals("-------")) {
                userInfo.put("imagePath", mImagePathReceived);
            }
        }


        setContentView(R.layout.activity_edit);
        // set the activity label
        setTitle("Edit Profile");

    }


    public Map<String, String> getUserInfo() {
        return userInfo;
    }

}