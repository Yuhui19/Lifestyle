package com.example.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {

    private TextView mTvBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        setTitle("My BMI");
        //Get the intent that created this activity.
        Intent receivedIntent = getIntent();

        //Get the string data and change the profile textView if data is not null
        String mHeightReceived = receivedIntent.getStringExtra("HEIGHT");
        String mWeightReceived = receivedIntent.getStringExtra("WEIGHT");

        // calculate the BMI value
        Double BMI = 0.0;
        if (!mHeightReceived.equals("-------") && !mWeightReceived.equals("-------")) {
            int height = Integer.parseInt(mHeightReceived);
            int weight = Integer.parseInt(mWeightReceived);
            BMI = (double) 703 * ((double) weight / (double) (144 * height * height));
        }


        // set the component's text to be the BMI value
        mTvBMI = (TextView) findViewById(R.id.tv_BMI_value);
        String strBMI = String.format("%.2f", BMI);
        mTvBMI.setText(strBMI);
    }
}