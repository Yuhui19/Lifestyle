package com.example.lifestyle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Map;

public class BMIFragment extends Fragment {
    private TextView mTvBMI;
    private UserViewModel mUserViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);
        mTvBMI = (TextView) view.findViewById(R.id.tv_BMI_value);


        System.out.println("We are creating the BMI fragment!!!");

        mUserViewModel = new ViewModelProviders().of(this).get(UserViewModel.class);

        //Set the observer for ViewModel
        mUserViewModel.getData().observe(getActivity(), nameObserver);


        return view;
    }


    //create an observer that watches the LiveData<UserData> object
    final Observer<UserData> nameObserver  = new Observer<UserData>() {
        @Override
        public void onChanged(@Nullable final UserData userData) {
            // Update the UI if this data variable changes
            if(userData != null) {
                //Get the string data and change the profile textView if data is not null
                String mHeightReceived = userData.getHeight();
                String mWeightReceived = userData.getWeight();

                // calculate the BMI value
                Double BMI = 0.0;
                if (mHeightReceived == null || mWeightReceived == null) {
                    Toast.makeText(getActivity(), "Need height and weight value", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mHeightReceived.equals("-------") && !mWeightReceived.equals("-------")) {
                        double height = Double.parseDouble(mHeightReceived);
                        System.out.println(height);
                        double weight = Double.parseDouble(mWeightReceived);
                        System.out.println(weight);
                        BMI = 703 * (weight / (144 * height * height));
                    } else {
                        Toast.makeText(getActivity(), "Need height and weight value", Toast.LENGTH_SHORT).show();
                    }
                }

                // set the component's text to be the BMI value
                String strBMI = String.format("%.2f", BMI);
                mTvBMI.setText(strBMI);
            }
        }
    };

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
