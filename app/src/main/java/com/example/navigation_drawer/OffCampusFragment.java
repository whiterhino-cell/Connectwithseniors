package com.example.navigation_drawer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.navigation_drawer.Activity.UploadNewCompActivity;
import com.example.navigation_drawer.Adapter.CampusAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class OffCampusFragment extends Fragment {
    private static final String TAG = "OffCamFragment started";

    private ArrayList<String> compAL;
    private ArrayList<HashMap<String,String>> compHMcompHM;

    private ImageView addProductImg;
    private RecyclerView mRecyclerView;
    private CampusAdapter adapter;

    private DatabaseReference mRef;
    private ChildEventListener mChildListener;
    private String cat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_off_campus, container, false);
        try {
            init(view);
            downProduct();
            click(view);
        }catch (Exception e){
            Log.d(TAG, "onCreateView: error : "+e.getMessage());
        }

        return view;
    }
    private void click(View view) {
        view.findViewById(R.id.addPersonOffCampus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked off camp");
                Intent intent=new Intent(getActivity(), UploadNewCompActivity.class);
                intent.putExtra("campus", "off_campus");
                startActivity(intent);
            }
        });

    }

    private void downProduct() {

        mChildListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                try {
                    HashMap<String,String> hashMap= (HashMap<String, String>) snapshot.getValue();
                    Log.d(TAG, "onChildAdded: hashMap : "+hashMap);
                    compHMcompHM.add(hashMap);
                }catch (Exception e){
                    Log.d(TAG, "onChildAdded: error x : "+e.getMessage());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    HashMap<String,String> hashMap= (HashMap<String, String>) snapshot.getValue();
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
                adapter.notifyDataSetChanged();
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
        mRecyclerView=view.findViewById(R.id.homeFullRecyclerView2);

        compAL=new ArrayList<>();
        compHMcompHM = new ArrayList<>();


        mRef= FirebaseDatabase.getInstance().getReference().child("Connectwithseniors").child("data").child("college").child("bit").child("2022").child("off_campus");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new CampusAdapter(getActivity(), compHMcompHM);
        mRecyclerView.setAdapter(adapter);
    }

}