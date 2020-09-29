package com.example.lifestyle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ModuleFragment extends Fragment {

    private LinearLayout modulesLayout;

    private ImageButton mIbMap;
    private ImageButton mIbWeather;
    private ImageButton mIbBMI;
    private ImageButton mIbGoal;

    private TextView mTvCountry;
    private TextView mTvCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Event handling for view objects can go here

    }



}
