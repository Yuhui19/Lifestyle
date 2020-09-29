package com.example.lifestyle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private Button mbuttonEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mbuttonEdit = (Button) view.findViewById(R.id.button_edit_profile);
        mbuttonEdit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Event handling for view objects can go here

   }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_edit_profile: {
                FragmentTransaction fTrans = getActivity().getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.module_info_fragment_tablet, new EditFragment(),"frag_edit");
                fTrans.addToBackStack(null);
                fTrans.commit();
            }
        }
    }
}
