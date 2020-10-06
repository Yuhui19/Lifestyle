package com.example.lifestyle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GoalFragment extends Fragment implements View.OnClickListener{

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

    private Map<String, String> userInfo;
    private UserViewModel mUserViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        mEtWeight = (EditText) view.findViewById(R.id.et_input_current_weight);
        mEtHeight = (EditText) view.findViewById(R.id.et_input_current_height);
        mUserViewModel = new ViewModelProviders().of(this).get(UserViewModel.class);
        mButtonSendGoal = (Button) view.findViewById(R.id.button_send_goal);

        //Set the observer for ViewModel
        mUserViewModel.getData().observe(getActivity(), nameObserver);

//        if (isTablet()) {
//            MainActivity mainActivity = (MainActivity) getActivity();
//            userInfo = mainActivity.getUserInfo();
//        }
//        else {
//            GoalActivity goalActivity = (GoalActivity) getActivity();
//            userInfo = goalActivity.getUserInfo();
//        }

        //Get the string data and change the profile textView if data is not null
//        if (userInfo.get("height") != null) {
//            mHeightReceived = userInfo.get("height");
//        }
//        if (userInfo.get("weight") != null) {
//            mWeightReceived = userInfo.get("weight");
//        }
//        if (userInfo.get("gender") != null) {
//            mGenderReceived = userInfo.get("gender");
//        }
//        if (userInfo.get("age") != null) {
//            mAgeReceived = userInfo.get("age");
//        }
//        mEtWeight.setText(mWeightReceived);
//        mEtHeight.setText(mHeightReceived);


        mButtonSendGoal.setOnClickListener(this);
        mOutputCaloriesBlock = (LinearLayout) view.findViewById(R.id.output_calories_block);
        mWarningMessageBlock = (LinearLayout) view.findViewById(R.id.warning_message_block);
        mBMRValue = (TextView) view.findViewById(R.id.tv_BMR_value);
        mSuggestedCalories = (TextView) view.findViewById(R.id.tv_suggested_calories);
        mEtGoalWeight = (EditText) view.findViewById(R.id.et_input_goal_weight);
        mRadioGroupGoal = (RadioGroup) view.findViewById(R.id.radio_group_goal);
        mRadioGroupStatus = (RadioGroup) view.findViewById(R.id.radio_group_status);
        mWarningMessage = (TextView) view.findViewById(R.id.warning_message);

        return view;
    }



    //create an observer that watches the LiveData<UserData> object
    final Observer<UserData> nameObserver  = new Observer<UserData>() {
        @Override
        public void onChanged(@Nullable final UserData userData) {
            // Update the UI if this data variable changes
            if(userData!=null) {
                //Get the string data and change the profile textView if data is not null
                if (userData.getHeight() != null) {
                    mHeightReceived = userData.getHeight();
                }
                if (userData.getWeight() != null) {
                    mWeightReceived = userData.getWeight();
                }
                if (userData.getGender() != null) {
                    mGenderReceived = userData.getGender();
                }
                if (userData.getAge() != null) {
                    mAgeReceived = userData.getAge();
                }
                mEtWeight.setText(mWeightReceived);
                mEtHeight.setText(mHeightReceived);
            }
        }
    };



    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_send_goal: {

                mOutputCaloriesBlock.setVisibility(View.VISIBLE);
                mWarningMessageBlock.setVisibility(View.VISIBLE);

                boolean valid = true;

                //check height input
                double height = 0;
                String inputHeight = mEtHeight.getText().toString();
                if (!inputHeight.matches("")) {
                    try {
                        height = Double.parseDouble(inputHeight);
                        if (0 >= height || height > 10) {
                            valid = false;
                            Toast.makeText(getActivity(), "Enter a valid height.", Toast.LENGTH_SHORT).show();
                        } else
                            mEtHeight.setBackgroundColor(500000);
                    } catch (Exception e) {
                        valid = false;
                        Toast.makeText(getActivity(), "Enter a valid height.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mEtHeight.setBackgroundColor(getResources().getColor(R.color.colorWarningLight));
                    mEtHeight.setHint("Your height is required");
                    valid = false;
                }

                //check input weight
                double weight = 0;
                String inputWeight = mEtWeight.getText().toString();
                if (!inputWeight.matches("")) {
                    try {
                        weight = Double.parseDouble(inputWeight);
                        if (0 >= weight || weight > 1800) {
                            valid = false;
                            Toast.makeText(getActivity(), "Enter a valid weight.", Toast.LENGTH_SHORT).show();
                        } else
                            mEtWeight.setBackgroundColor(500000);
                    } catch (Exception e) {
                        valid = false;
                        Toast.makeText(getActivity(), "Enter a valid weight.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mEtWeight.setBackgroundColor(getResources().getColor(R.color.colorWarningLight));
                    mEtWeight.setHint("Your weight is required");
                    valid = false;
                }

                //check age
//                int age = Integer.parseInt(mAgeReceived);
                int age = 0;
                if (mAgeReceived != null && !mAgeReceived.matches("")) {
                    try {
                        age = Integer.parseInt(mAgeReceived);
                        if (age <= 0 || 200 < age) {
                            valid = false;
                            Toast.makeText(getActivity(), "Enter a valid age", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        valid = false;
                        Toast.makeText(getActivity(), "Enter a valid age", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    valid = false;
                    Toast.makeText(getActivity(), "Your age is required", Toast.LENGTH_SHORT).show();
                }

                double BMRValue = 0;
                int status = -1;
                double loseOrGainWeight = 0;
                double dailyInputCalories = 10000;
                String defaultSuggestion = "Please ensure that you have entered your weight, height, " +
                        "age and gender, otherwise we can't make suggestions for you";
                String defaultCaution = "You have set your goal, keep going for it!";



                // get checked user status
                int selectedStatusId = mRadioGroupStatus.getCheckedRadioButtonId();
//                mRadioButtonStatus = findViewById(selectedStatusId);
                if (selectedStatusId == R.id.status_active) {
                    status = 1;
                }
                else if (selectedStatusId == R.id.status_sedentary) {
                    status = -1;
                }



                // get user's goal - whether to lose, gain or maintain weight
                // 0 represents maintain weight, 1 represents gain weight, -1 represents lose weight
                int selectedGoalId = mRadioGroupGoal.getCheckedRadioButtonId();
                if (selectedGoalId == R.id.goal_lose_weight) {
                    loseOrGainWeight = -1;
                }
                else if (selectedGoalId == R.id.goal_gain_weight) {
                    loseOrGainWeight = 1;
                }
                else {
                    loseOrGainWeight = 0;
                }



                // get user's goal weight to lose or gain
                double goalWeightToLoseOrGain = 0;
//                if (!mEtGoalWeight.getText().toString().equals("")) {
//                    goalWeightToLoseOrGain = Double.parseDouble(mEtGoalWeight.getText().toString());
//                }
                //check goal weight input
                String inputGoal = mEtGoalWeight.getText().toString();
                if (!inputGoal.matches("")) {
                    try {
                        double goalValue = Double.parseDouble(inputGoal);
                        if (0 <= goalValue && goalValue <= weight)
                            goalWeightToLoseOrGain = goalValue;
                        else {
                            valid = false;
                            Toast.makeText(getActivity(), "Enter a valid goal.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        valid = false;
                        Toast.makeText(getActivity(), "Enter a valid goal.", Toast.LENGTH_SHORT).show();
                    }
                }


                // calculate user's BMR base on the age, weight, height and gender
                if (mGenderReceived != null) {
                    if (mGenderReceived.equals("Male")) {
                        BMRValue = 66.47 + (6.24 * weight) + (12.7 * 12 * height) - 6.755 * age;
                    }
                    else {
                        BMRValue = 655.1 + (4.37 * weight) + (4.7 * 12 * height) - 4.7 * age;
                    }
//                    mBMRValue.setText(Double.toString(BMRValue));
                    mBMRValue.setText(new DecimalFormat("#.00").format(BMRValue));
                }
                else mBMRValue.setText("----");



                // give users suggestions based on the BMR and their weight goals
                if (BMRValue == 0) {
                    mSuggestedCalories.setText(defaultSuggestion);
                }
                else {
                    dailyInputCalories = Math.max(0, BMRValue + 500 * loseOrGainWeight * goalWeightToLoseOrGain);
                    String caloriesWithTwoDigits = new DecimalFormat("#.00").format(dailyInputCalories);
                    String realSuggestion = "You need to eat " + caloriesWithTwoDigits +" calories per day";
                    if (loseOrGainWeight == 0) {
                        realSuggestion += " to maintain your weight";
                    }
                    else if (loseOrGainWeight == -1) {
                        realSuggestion += " to lose " + goalWeightToLoseOrGain + " lbs a week";
                    }
                    else if (loseOrGainWeight == 1){
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
                    defaultCaution = "Warning: You may eat too few calories per day! Try to change your goal.";
                    mWarningMessage.setBackgroundColor(getResources().getColor(R.color.colorWarningLight));
                } else
                    mWarningMessage.setBackgroundColor(500000);
                mWarningMessage.setText(defaultCaution);

                if (!valid)
                    setNull();
            }
        }
    }

    private void setNull() {
        mOutputCaloriesBlock.setVisibility(View.INVISIBLE);
        mWarningMessageBlock.setVisibility(View.INVISIBLE);
    }


    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
