package com.example.lifestyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GoalActivity extends AppCompatActivity implements View.OnClickListener{
    EditText mEtWeight;
    EditText mEtHeight;
    String mHeightReceived;
    String mWeightReceived;
    Button mButtonSendGoal;
    LinearLayout mOutputCaloriesBlock;
    LinearLayout mWarningMessageBlock;

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
            }
        }
    }
}