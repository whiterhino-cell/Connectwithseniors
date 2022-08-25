package com.example.navigation_drawer.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation_drawer.Adapter.StudentAdapter;
import com.example.navigation_drawer.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentJoinFragment extends Fragment {
    private static final String TAG = "SJoinFragment started";

    private ArrayList<HashMap<String,String>> mapArrayList;

    private DatabaseReference mRef;
    private StudentAdapter adapter;
    private ImageView addImg;
    private String student,campus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_join, container, false);
//        Log.d(TAG, "onCreateView: ");
        try {
            init(view);
            down(view);
            click(view);
        }catch (Exception e){
            Log.d(TAG, "onCreateView: error : "+e.getMessage());
        }
        return view;
    }

    private void click(View view) {
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadStudentFragment fragment=new UploadStudentFragment();
                Bundle bundle=new Bundle();
                bundle.putString("student",student);
                bundle.putString("campus",campus);
                fragment.setArguments(bundle);
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame2data,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void down(View view) {
        ChildEventListener mChildListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                try {
                    HashMap<String,String> hashMap= (HashMap<String, String>) snapshot.getValue();
                    mapArrayList.add(hashMap);
//                    Log.d(TAG, "onChildAdded: hashMap : "+hashMap);
//                    Log.d(TAG, "onChildAdded: hashMap : "+mapArrayList);
                }catch (Exception e){
                    Log.d(TAG, "onChildAdded: error x : "+e.getMessage());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    HashMap<String,String> hashMap= (HashMap<String, String>) snapshot.getValue();
                    mapArrayList.add(hashMap);
                    Log.d(TAG, "onChildAdded: hashMap : "+hashMap);

                }catch (Exception e){
                    Log.d(TAG, "onChildAdded: error x : "+e.getMessage());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                ProductOg productOg = snapshot.getValue(ProductOg.class);
//                String pdtUrl=productOg.getPdtUrl();
//
//                compAL.remove(pdtUrl);
//                mDataListHM.remove(pdtUrl);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mRef.addChildEventListener(mChildListener);
    }

    private void init(View view) {
        Bundle bundle=getArguments();
        student = bundle.getString("student");
        campus = bundle.getString("campus");

        addImg=view.findViewById(R.id.addPersonData);
        mapArrayList=new ArrayList<HashMap<String,String>>();

        mRef= FirebaseDatabase.getInstance().getReference().child("Connectwithseniors").child("data").child("college")
                .child("bit").child("2022").child(campus).child(student).child("details");

        adapter=new StudentAdapter(getActivity(),mapArrayList,campus,student);
        RecyclerView mRecyclerView=view.findViewById(R.id.homeFullRecyclerView3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            mRecyclerView.setAdapter(adapter);
        }catch (Exception e){
            Log.d(TAG, "init: errorr :"+e.getMessage());;
        }
//        mRecyclerView.setAdapter(adapter);
        Log.d(TAG, "init: end");
    }
}