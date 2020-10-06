package com.example.lifestyle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;
import java.util.Map;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private Button mbuttonEdit;
    private TextView mTvName;
    private TextView mTvCountry;
    private TextView mTvCity;
    private TextView mTvGender;
    private TextView mTvAge;
    private TextView mTvHeight;
    private TextView mTvWeight;
    private ImageView mIvThumbnail;
    private UserViewModel mUserViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mTvName = (TextView) view.findViewById(R.id.tv_username);
        mTvGender = (TextView) view.findViewById(R.id.tv_gender);
        mTvAge = (TextView) view.findViewById(R.id.tv_age);
        mTvHeight = (TextView) view.findViewById(R.id.tv_height);
        mTvWeight = (TextView) view.findViewById(R.id.tv_weight);
        mTvCountry = (TextView) view.findViewById(R.id.tv_country);
        mTvCity = (TextView) view.findViewById(R.id.tv_city);
        mIvThumbnail = (ImageView) view.findViewById(R.id.iv_user_profile);




        System.out.println("We are creating the profile fragment!!!");
        mUserViewModel = new ViewModelProviders().of(this).get(UserViewModel.class);


        //Set the observer for ViewModel
        mUserViewModel.getData().observe(getActivity(), nameObserver);


        mbuttonEdit = (Button) view.findViewById(R.id.button_edit_profile);
        mbuttonEdit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Event handling for view objects can go here
   }



    //create an observer that watches the LiveData<UserData> object
    final Observer<UserData> nameObserver  = new Observer<UserData>() {
        @Override
        public void onChanged(@Nullable final UserData userData) {
            // Update the UI if this data variable changes
            if(userData!=null) {
                if (userData.getName() != null) {
                    mTvName.setText(userData.getName());
                }
                if (userData.getGender() != null) {
                    mTvGender.setText(userData.getGender());
                }
                if (userData.getAge() != null) {
                    mTvAge.setText(userData.getAge());
                }
                if (userData.getHeight() != null) {
                    mTvHeight.setText(userData.getHeight());
                }
                if (userData.getWeight() != null) {
                    mTvWeight.setText(userData.getWeight());
                }
                if (userData.getCountry() != null) {
                    mTvCountry.setText(userData.getCountry());
                }
                if (userData.getCity() != null) {
                    mTvCity.setText(userData.getCity());
                }
                if (userData.getImagePath() != null) {
                    String mImagePathReceived = userData.getImagePath();
                    Bitmap thumbnailImage = BitmapFactory.decodeFile(mImagePathReceived);
                    if (thumbnailImage != null){
                        mIvThumbnail.setImageBitmap(thumbnailImage);
                    }
                }
            }
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_edit_profile: {
                System.out.println("the user click the edit button");
                if (isTablet()) {
                    System.out.println("the user click the edit button 1");
                    FragmentTransaction fTrans = getActivity().getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.module_info_fragment_tablet, new EditFragment(),"frag_edit");
                    fTrans.addToBackStack("frag_edit");
                    fTrans.commit();
                }
                else {
                    System.out.println("the user click the edit button 2");
                    Intent editIntent = new Intent(getActivity(), EditActivity.class);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.startActivity(editIntent);
                }
            }
        }
    }


    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
