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


public class StepCounterFragment extends Fragment implements View.OnClickListener{

    private SensorManager mSensorManager;
    private TextView mTvStep;
    private Sensor mStepCounter;
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

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            stepCount = String.valueOf(event.values[0]);
            mTvStep.setText("" + String.valueOf(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        if(mStepCounter!=null) {
            mSensorManager.registerListener(mListener,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }
        LocalDateTime now = LocalDateTime.now();
        startTime = dtf.format(now);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onPause() {
        super.onPause();
        if(mStepCounter!=null) {

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