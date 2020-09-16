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

public class GoalActivity extends AppCompatActivity implements View.OnClickListener{
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

        //Get the intent that created this activity.
        Intent receivedIntent = getIntent();

        //Get the string data and change the profile textView if data is not null
        if (receivedIntent.getStringExtra("HEIGHT") != null) {
            mHeightReceived = receivedIntent.getStringExtra("HEIGHT");
        }
        if (receivedIntent.getStringExtra("WEIGHT") != null) {
            mWeightReceived = receivedIntent.getStringExtra("WEIGHT");
        }
        if (receivedIntent.getStringExtra("GENDER") != null) {
            mGenderReceived = receivedIntent.getStringExtra("GENDER");
        }
        if (receivedIntent.getStringExtra("AGE") != null) {
            mAgeReceived = receivedIntent.getStringExtra("AGE");
        }

        mEtWeight = (EditText) findViewById(R.id.et_input_current_weight);
        mEtWeight.setText(mWeightReceived);

        mEtHeight = (EditText) findViewById(R.id.et_input_current_height);
        mEtHeight.setText(mHeightReceived);

        mButtonSendGoal = (Button) findViewById(R.id.button_send_goal);
        mButtonSendGoal.setOnClickListener(this);

        mOutputCaloriesBlock = (LinearLayout) findViewById(R.id.output_calories_block);
        mWarningMessageBlock = (LinearLayout) findViewById(R.id.warning_message_block);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_send_goal: {

                mOutputCaloriesBlock.setVisibility(View.VISIBLE);
                mWarningMessageBlock.setVisibility(View.VISIBLE);
                mBMRValue = (TextView) findViewById(R.id.tv_BMR_value);
                mSuggestedCalories = (TextView) findViewById(R.id.tv_suggested_calories);
                mEtGoalWeight = (EditText) findViewById(R.id.et_input_goal_weight);
                mRadioGroupGoal = (RadioGroup) findViewById(R.id.radio_group_goal);
                mRadioGroupStatus = (RadioGroup) findViewById(R.id.radio_group_status);
                mWarningMessage = (TextView) findViewById(R.id.warning_message);

                int height = Integer.parseInt(mEtHeight.getText().toString());
                int weight = Integer.parseInt(mEtWeight.getText().toString());
                int age = Integer.parseInt(mAgeReceived);
                int BMRValue = 0;
                int status = -1;
                int loseOrGainWeight = 0;
                int dailyInputCalories = 10000;
                String defaultSuggestion = "Please ensure that you have entered your weight, height, " +
                        "age and gender, otherwise we can't make suggestions for you";
                String defaultCaution = "You have set your goal, keep going for it!";



                // get checked user status
                int selectedStatusId = mRadioGroupStatus.getCheckedRadioButtonId();
                mRadioButtonStatus = findViewById(selectedStatusId);
                if (mRadioButtonStatus.getText().equals("status_active")) {
                    status = 1;
                }
                else if (mRadioButtonStatus.getText().equals("status_sedentary")) {
                    status = -1;
                }



                // get user's goal - whether to lose, gain or maintain weight
                // 0 represents maintain weight, 1 represents gain weight, -1 represents lose weight
                int selectedGoalId = mRadioGroupGoal.getCheckedRadioButtonId();
                mRadioButtonGoal = findViewById(selectedGoalId);
                if (mRadioButtonGoal.getText().equals("lose")) {
                    loseOrGainWeight = -1;
                }
                else if (mRadioButtonGoal.getText().equals("gain")) {
                    loseOrGainWeight = 1;
                }
                else {
                    loseOrGainWeight = 0;
                }



                // get user's goal weight to lose or gain
                int goalWeightToLoseOrGain = 0;
                if (!mEtGoalWeight.getText().toString().equals("")) {
                    goalWeightToLoseOrGain = Integer.parseInt(mEtGoalWeight.getText().toString());
                }


                // calculate user's BMR base on the age, weight, height and gender
                if (mGenderReceived != null) {
                    if (mGenderReceived.equals("Male")) {
                        BMRValue = (int) (66.47 + (6.24 * weight) + (12.7 * 12 * height) - 6.755 * age);
                    }
                    else {
                        BMRValue = (int) (655.1 + (4.37 * weight) + (4.7 * 12 * height) - 4.7 * age);
                    }
                    mBMRValue.setText(Integer.toString(BMRValue));
                }
                else mBMRValue.setText("----");



                // give users suggestions based on the BMR and their weight goals
                if (BMRValue == 0) {
                    mSuggestedCalories.setText(defaultSuggestion);
                }
                else {
                    dailyInputCalories = Math.max(0, BMRValue + 500 * loseOrGainWeight * goalWeightToLoseOrGain);
                    String realSuggestion = "You need to eat " + dailyInputCalories +" calories per day";
                    if (loseOrGainWeight == 0) {
                        realSuggestion += " to maintain your weight";
                    }
                    else if (loseOrGainWeight == -1) {
                        realSuggestion += " to lose " + goalWeightToLoseOrGain + " lbs a week";
                    }
                    else {
                        realSuggestion += " to gain " + goalWeightToLoseOrGain + " lbs a week";
                    }
                    mSuggestedCalories.setText(realSuggestion);
                }

                if (goalWeightToLoseOrGain > 2) {
                    defaultCaution = "You may get overzealous. Gaining or losing more than 2 lbs a week may not be good to your health";
                }
                mWarningMessage.setText(defaultCaution);
                
                // give users cautions if their suggested daily input calories are too low
                if ((mGenderReceived.equals("Male") && dailyInputCalories < 1200) ||
                        (mGenderReceived.equals("Female") && dailyInputCalories < 1000)) {
                    defaultCaution = "You may eat too few calories per day! Try to change your goal.";
                }
                mWarningMessage.setText(defaultCaution);

            }
        }
    }
}