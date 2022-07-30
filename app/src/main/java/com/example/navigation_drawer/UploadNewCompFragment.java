package com.example.navigation_drawer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UploadNewCompFragment extends Fragment {
    private static final String TAG = "UpNewCoFragment started";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_new_comp, container, false);
        String campus = getArguments().getString("campus");
        Log.d(TAG, "onCreateView: "+campus);

        TextView textView=view.findViewById(R.id.new_comp_upTV);
        textView.setText(campus);

        return view;
    }
}