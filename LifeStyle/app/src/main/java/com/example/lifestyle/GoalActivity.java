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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_goal);
        setTitle("My Goal");
    }

}