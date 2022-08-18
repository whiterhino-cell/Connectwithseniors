package com.example.navigation_drawer.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation_drawer.R;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder>{
    private static final String TAG = "StudentAdapter started";

    private Context mContext;
    private ArrayList<HashMap<String,String>> arrayListHM;
    private String campus,student;

    public StudentAdapter(Context mContext, ArrayList<HashMap<String, String>> arrayListHM, String campus, String student) {
        this.mContext = mContext;
        this.arrayListHM = arrayListHM;
        this.campus = campus;
        this.student = student;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_item_student,parent,false);

        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (position%2==0)
            holder.linearLayout.setBackgroundColor(R.color.white);
        else
            holder.linearLayout.setBackgroundColor(R.color.colorAccent);

        holder.slNoAd.setText((position+1)+"");
        HashMap<String,String> hashMap=arrayListHM.get(position);
        holder.userNameAd.setText(hashMap.get("Username"));
        holder.departmentAd.setText(hashMap.get("Department"));

        holder.connectAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link=hashMap.get("Connect");

                Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
                Log.d(TAG, "onClick: connect : "+link);
            }
        });

    }

    @Override
    public int getItemCount() {
        try {
            Log.d(TAG, "getItemCount: "+arrayListHM.size());
            return arrayListHM.size();

        }catch (Exception e){
            Log.d(TAG, "getItemCount: erroe : "+e.toString());
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView slNoAd,userNameAd,departmentAd;
        Button connectAd;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            slNoAd=itemView.findViewById(R.id.slNoAd);
            userNameAd=itemView.findViewById(R.id.userNameAd);
            departmentAd=itemView.findViewById(R.id.departmentAd);
            connectAd=itemView.findViewById(R.id.connectAd);

            linearLayout=itemView.findViewById(R.id.lLayoutAdapter);
        }
    }
}
