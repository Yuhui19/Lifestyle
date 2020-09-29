package com.example.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class BMIActivity extends AppCompatActivity {

    private TextView mTvBMI;
    private Map<String, String> userInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the intent that created this activity.
        Intent receivedIntent = getIntent();

        if (receivedIntent.getStringExtra("HEIGHT") != null) {
            userInfo.put("height", receivedIntent.getStringExtra("HEIGHT"));
        }
        if (receivedIntent.getStringExtra("WEIGHT") != null) {
            userInfo.put("weight", receivedIntent.getStringExtra("WEIGHT"));
        }

        setContentView(R.layout.activity_bmi);
        setTitle("My BMI");


//        // calculate the BMI value
//        Double BMI = 0.0;
//        if (!mHeightReceived.equals("-------") && !mWeightReceived.equals("-------")) {
//            double height = Double.parseDouble(mHeightReceived);
//            double weight = Double.parseDouble(mWeightReceived);
//            BMI = 703 * (weight / (144 * height * height));
//        } else {
//            Toast.makeText(this, "Need height and weight value", Toast.LENGTH_SHORT).show();
//        }
//
//
//        // set the component's text to be the BMI value
//        mTvBMI = (TextView) findViewById(R.id.tv_BMI_value);
//        String strBMI = String.format("%.2f", BMI);
//        mTvBMI.setText(strBMI);
    }

    public Map<String, String> getUserInfo() {
        return userInfo;
    }
}