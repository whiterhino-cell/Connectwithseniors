package com.example.navigation_drawer.Adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation_drawer.Activity.MainActivity;
import com.example.navigation_drawer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class CampusAdapter extends RecyclerView.Adapter<CampusAdapter.MyViewHolder>{
    private static final String TAG = "CampusAdapter started";

    private Context mContext;
    private ArrayList<HashMap<String,String>> arrayListHM;
    private String campus;

    public CampusAdapter(Context mContext, ArrayList<HashMap<String,String>> arrayListHM,String campus) {
        this.mContext = mContext;
        this.arrayListHM = arrayListHM;
        this.campus= campus;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String link=arrayListHM.get(position).get("imageURL");

//        Log.d(TAG, "onBindViewHolder: link : "+link);
        Picasso.get().load(link).into(holder.imageView);
        click(holder,position);
    }

    private void click(MyViewHolder holder, int position) {
        String name=arrayListHM.get(position).get("name");
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+name);
                Intent intent=new Intent(mContext, MainActivity.class);
                intent.putExtra("student",name);
                intent.putExtra("campus", campus);
//                intent.putExtra("comp", campus);
//                intent.putExtra("getPrice", productOg.getPrice());
                mContext.startActivity(intent);

//                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount: "+arrayListHM.size());
        return arrayListHM.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imagePdt);
        }
    }
}
