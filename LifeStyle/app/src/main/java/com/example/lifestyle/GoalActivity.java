package com.example.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.ECField;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GoalActivity extends AppCompatActivity{
    EditText mEtWeight;
    EditText mEtHeight;
    EditText mEtGoalWeight;
    String mHeightReceived;
    String mWeightReceived;
    String mGenderReceived;
    String mAgeReceived;
    Button mButtonSendGoal;
    LinearLayout mOutputCaloriesBlock;
    LinearLayout mWarningMessageBlock;
    TextView mBMRValue;
    TextView mSuggestedCalories;
    private RadioGroup mRadioGroupGoal;
    private RadioGroup mRadioGroupStatus;
    private RadioButton mRadioButtonGoal;
    private RadioButton mRadioButtonStatus;
    private TextView mWarningMessage;

    private Map<String, String> userInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the intent that created this activity.
        Intent receivedIntent = getIntent();

        //Get the string data and change the profile textView if data is not null
        if (receivedIntent.getStringExtra("HEIGHT") != null) {
            userInfo.put("height", receivedIntent.getStringExtra("HEIGHT"));
        }
        if (receivedIntent.getStringExtra("WEIGHT") != null) {
            userInfo.put("weight", receivedIntent.getStringExtra("WEIGHT"));
        }
        if (receivedIntent.getStringExtra("GENDER") != null) {
            userInfo.put("gender", receivedIntent.getStringExtra("GENDER"));
        }
        if (receivedIntent.getStringExtra("AGE") != null) {
            userInfo.put("age", receivedIntent.getStringExtra("AGE"));
        }

        setContentView(R.layout.activity_goal);
        setTitle("My Goal");
    }

    public Map<String, String> getUserInfo() {
        return userInfo;
    }
}