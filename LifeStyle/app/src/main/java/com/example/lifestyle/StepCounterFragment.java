package com.example.lifestyle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class StepCounterFragment extends Fragment implements View.OnClickListener{

    private SensorManager mSensorManager;
    private TextView mTvStep;
    private Sensor mStepCounter;
    private Sensor mLinearAccelerometer;
    private final double mThreshold = 2.0;

    //Previous positions
    private double last_x, last_y, last_z;
    private double now_x, now_y,now_z;
    private boolean mStepCounterOpen = false;
    private boolean mNotFirstTime = false;


    private RecyclerView mRecyclerView;
    private MyRVAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private StepRecordViewModel mStepRecordViewModel;
    private List<StepRecordData> mInputList = new ArrayList<>();
    private String startTime;
    private String endTime;
    private String stepCount = "0";
    private DateTimeFormatter dtf;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_counter, container, false);
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        mTvStep = (TextView) view.findViewById(R.id.tv_current_step_count);

        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        PackageManager pm = getActivity().getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
            System.out.println("has step counter");
        }
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mLinearAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        //Get the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_main);

        //Tell Android that we know the size of the recyclerview
        //doesn't change
        mRecyclerView.setHasFixedSize(true);

        //Set the layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);


        mStepRecordViewModel = new ViewModelProviders().of(this).get(StepRecordViewModel.class);

        //Populate the list with data
        //Set the observer for ViewModel
        mStepRecordViewModel.getData().observe(getActivity(), nameObserver);;
//        mInputList = stepRecordLiveData.getValue();

//        List<String> inputList = new ArrayList<>();
//        for (int i=1;i<=100;i++){
//            inputList.add("Item " + i);
//        }

        //Set the adapter
        mAdapter = new MyRVAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mTvStep.setOnClickListener(this);

        return view;
    }

    private SensorEventListener mStepSensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            stepCount = String.valueOf(event.values[0]);
            mTvStep.setText("" + String.valueOf(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };



    private SensorEventListener mShakeSensorListener = new SensorEventListener() {


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            //Get the acceleration rates along the y and z axes
            now_x = sensorEvent.values[0];
            now_y = sensorEvent.values[1];
            now_z = sensorEvent.values[2];

            if(mNotFirstTime){
                double dx = Math.abs(last_x - now_x);
                double dy = Math.abs(last_y - now_y);
                double dz = Math.abs(last_z - now_z);

                //Check if the values of acceleration have changed on any pair of axes
                if( (dx > mThreshold && dy > mThreshold) ||
                        (dx > mThreshold && dz > mThreshold)||
                        (dy > mThreshold && dz > mThreshold)){

                    if (mStepCounterOpen) {
                        // record the endTime
                        LocalDateTime now = LocalDateTime.now();
                        endTime = dtf.format(now);

                        // close the step counter sensor
                        mSensorManager.unregisterListener(mStepSensorListener);

                        // write step record to database
                        StepRecordData stepRecordData = new StepRecordData();
                        stepRecordData.setStartTime(startTime);
                        stepRecordData.setEndTime(endTime);
                        stepRecordData.setStepCount((int) Float.parseFloat(stepCount));
                        stepRecordData.setUsername(UserSession.getInstance().getSessionId());
                        mStepRecordViewModel.setStepRecordsData(stepRecordData);

                        // set step text view
                        mTvStep.setText("" + String.valueOf(0));
                        mStepCounterOpen = false;

                    }
                    else {
                        // record the startTime
                        LocalDateTime now = LocalDateTime.now();
                        startTime = dtf.format(now);

                        //open the step counter sensor
                        mSensorManager.registerListener(mStepSensorListener,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);

                        mStepCounterOpen = true;
                    }
                }
            }
            last_x = now_x;
            last_y = now_y;
            last_z = now_z;
            mNotFirstTime = true;
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        if(mLinearAccelerometer!=null) {
            mSensorManager.registerListener(mShakeSensorListener,mLinearAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onPause() {
        super.onPause();
        if(mLinearAccelerometer!=null) {
            mSensorManager.unregisterListener(mShakeSensorListener);
        }

//        LocalDateTime now = LocalDateTime.now();
//        endTime = dtf.format(now);
//        StepRecordData stepRecordData = new StepRecordData();
//        stepRecordData.setStartTime(startTime);
//        stepRecordData.setEndTime(endTime);
//        stepRecordData.setStepCount((int) Float.parseFloat(stepCount));
//        stepRecordData.setUsername(UserSession.getInstance().getSessionId());
//        mStepRecordViewModel.setStepRecordsData(stepRecordData);
//        mSensorManager.unregisterListener(mListener);
    }

    //create an observer that watches the LiveData<UserData> object
    final Observer<List<StepRecordData>> nameObserver  = new Observer<List<StepRecordData>>() {
        @Override
        public void onChanged(@Nullable final List<StepRecordData> stepRecordData) {
            // Update the UI if this data variable changes
            mAdapter.setData(stepRecordData);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_current_step_count: {
                //The button press should open a camera
                LocalDateTime now = LocalDateTime.now();
                startTime = dtf.format(now);
                endTime = dtf.format(now);
                StepRecordData stepRecordData = new StepRecordData();
                stepRecordData.setStartTime(startTime);
                stepRecordData.setEndTime(endTime);
                stepRecordData.setStepCount((int) Float.parseFloat(stepCount));
                stepRecordData.setUsername(UserSession.getInstance().getSessionId());
                mStepRecordViewModel.setStepRecordsData(stepRecordData);
                System.out.println("one click !!!!!!!!!!!!!");
            }
        }
    }
}