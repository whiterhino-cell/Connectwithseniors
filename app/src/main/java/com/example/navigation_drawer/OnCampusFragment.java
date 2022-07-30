package com.example.navigation_drawer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OnCampusFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_campus, container, false);

        view.findViewById(R.id.addPersonOnCampus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadNewCompFragment fragment = new UploadNewCompFragment ();
                Bundle args = new Bundle();
                args.putString("campus", "on_campus");
                fragment.setArguments(args);

                getFragmentManager().beginTransaction().add(R.id.frame, fragment).commit();
            }
        });

        return view;
    }
}