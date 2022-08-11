package com.example.navigation_drawer.Fragments;

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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.navigation_drawer.Activity.MainActivity;
import com.example.navigation_drawer.Activity.UploadNewCompActivity;
import com.example.navigation_drawer.Adapter.CampusAdapter;
import com.example.navigation_drawer.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class OnCampusFragment extends Fragment {
    private static final String TAG = "OnCampFragment started";

    private ArrayList<String> compAL;
    private ArrayList<HashMap<String,String>> compHMcompHM;

    private ImageView addProductImg;
    private RecyclerView mRecyclerView;
    private CampusAdapter adapter;

    private DatabaseReference mRef;
    private ChildEventListener mChildListener;
    private String cat;
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_on_campus, container, false);

        try {
            init(view);
            downProduct();
            click(view);
//            up();
        }catch (Exception e){
            Log.d(TAG, "onCreateView: error : "+e.getMessage());
        }


//        up();

        return view;
    }

    private void up() {
        String key = UUID.randomUUID().toString();;
        String comp="genpact";

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("Username","Shubham Sourabh");
        hashMap.put("Department","CSE");
        hashMap.put("Connect","https://www.linkedin.com/in/shubhamsourabh14/");
        mRef.child(comp).child("details").child(key).setValue(hashMap);

        key = UUID.randomUUID().toString();;
        hashMap=new HashMap<>();
        hashMap.put("Username","Aasif Razaa");
        hashMap.put("Department","IT");
        hashMap.put("Connect","https://www.linkedin.com/in/aasif-razaa/");
        mRef.child(comp).child("details").child(key).setValue(hashMap);

        key = UUID.randomUUID().toString();;
        hashMap=new HashMap<>();
        hashMap.put("Username","Nidhi Jha");
        hashMap.put("Department","ECE");
        hashMap.put("Connect","https://www.linkedin.com/in/nidhi-jha-46b599197/");
        mRef.child(comp).child("details").child(key).setValue(hashMap);
    }

    private void click(View view) {
        view.findViewById(R.id.addPersonOnCampus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), UploadNewCompActivity.class);
                intent.putExtra("campus", "on_campus");
                startActivity(intent);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(getActivity(), "You Clicked "+ (position+1), Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onClick: "+name);
                String name=compHMcompHM.get(position).get("name");
                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("student",name);
                intent.putExtra("campus", "on_campus");
//                intent.putExtra("comp", campus);
//                intent.putExtra("getPrice", productOg.getPrice());
                getActivity().startActivity(intent);

            }
        });
    }

    private void downProduct() {

        mChildListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

//                sssr(snapshot);
                try {
//                    HashMap<String,String> hashMap= (HashMap<String, String>) snapshot.getValue();
//                    Log.d(TAG, "onChildAdded: hashMap : "+hashMap);
////                    hashMap.remove("details");
////                    Log.d(TAG, "onChildAdded: hashMap : "+hashMap);
////                    Log.d(TAG, "onChildAdded: hashMap : "+((HashMap<?, ?>) snapshot.getValue()).get("details")+"\n\n.");
//                    if (hashMap.get("details")!=null){
//                        HashMap<String,HashMap<String ,String >> hashMapHashMap= (HashMap<String, HashMap<String, String>>) ((HashMap<?, ?>)snapshot.getValue()).get("details");
//                        Log.d(TAG, "onChildAdded: "+hashMapHashMap);
//                        ArrayList<HashMap<String ,String>> arrayList=new ArrayList<>();
//                        for (String obj:hashMapHashMap.keySet()){
//                            arrayList.add(hashMapHashMap.get(obj));
//                        }
//                        Log.d(TAG, "onChildAdded: ::"+arrayList+"\n.");
//                    }
//                    compHMcompHM.add(hashMap);
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

//    private void sssr(DataSnapshot snapshot) {
//
//    }

    private void init(View view) {
        gridView=view.findViewById(R.id.grid_view_on_campus);

        compAL=new ArrayList<>();
        compHMcompHM = new ArrayList<>();

        mRef= FirebaseDatabase.getInstance().getReference().child("Connectwithseniors").child("data").child("college").child("bit").child("2022").child("on_campus");
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new CampusAdapter(getActivity(), compHMcompHM,"on_campus");
        gridView.setAdapter(adapter);
    }

}