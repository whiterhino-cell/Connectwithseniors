package com.example.navigation_drawer.Adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class CampusAdapter extends BaseAdapter {
    private static final String TAG = "CampusAdapter started";

    private Context context;
    private ArrayList<HashMap<String,String>> compHMcompHM;
    private String campus;
    private LayoutInflater inflater;


    public CampusAdapter(Context mContext, ArrayList<HashMap<String,String>> arrayListHM,String campus) {
        this.context = mContext;
        this.compHMcompHM = arrayListHM;
        this.campus= campus;
    }
    @Override
    public int getCount() {
        Log.d(TAG, "getCount: "+compHMcompHM.size());
        return compHMcompHM.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (inflater==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view==null){
            view=inflater.inflate(R.layout.list_item,null);
        }
        ImageView imageView=view.findViewById(R.id.imagePdt);
        String link=compHMcompHM.get(position).get("imageURL");

        Picasso.get().load(link).into(imageView);

//        TextView textView=view.findViewById(R.id.textView);

//        imageView.setImageResource(numImg[position]);
//        textView.setText(numWord[position]);
        return view;
    }
}
