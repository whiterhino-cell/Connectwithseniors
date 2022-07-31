package com.example.navigation_drawer.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.navigation_drawer.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class UploadStudentFragment extends Fragment {

    private String student,campus;
    private DatabaseReference mRef;

    TextView Username,Department,Connect;
    Button submitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_student, container, false);

        init(view);
        click(view);
        return view;
    }

    private void click(View view) {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("Username"  ,Username.getText().toString());
                hashMap.put("Department",Department.getText().toString());
                hashMap.put("Connect"   ,Connect.getText().toString());
                String key= UUID.randomUUID().toString();
                mRef.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Username.setText("");
                        Department.setText("");
                        Connect.setText("");
                    }
                });
            }
        });
    }

    private void init(View view) {
        Bundle bundle=getArguments();
        student = bundle.getString("student");
        campus = bundle.getString("campus");
        mRef= FirebaseDatabase.getInstance().getReference().child("Connectwithseniors").child("data").child("college").child("bit").child("2022").child(campus).child(student).child("details");
        Username=view.findViewById(R.id.UsernameTvUp);
        Department=view.findViewById(R.id.DepartmentTvUp);
        Connect=view.findViewById(R.id.ConnectTvUp);
        submitBtn=view.findViewById(R.id.submitTvUp);
    }
}