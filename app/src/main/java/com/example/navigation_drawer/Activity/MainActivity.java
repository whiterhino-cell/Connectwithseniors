package com.example.navigation_drawer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.navigation_drawer.Fragments.OffCampusFragment;
import com.example.navigation_drawer.Fragments.OnCampusFragment;
import com.example.navigation_drawer.Fragments.StudentJoinFragment;
import com.example.navigation_drawer.Fragments.UploadNewCompFragment;
import com.example.navigation_drawer.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity started";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            hell();
        }catch (Exception e){
            Log.d(TAG, "onCreate: "+e.getMessage());
        }

    }

    private void hell() {

        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        frameLayout=findViewById(R.id.frame);
        navigationView=findViewById(R.id.nav_drawer);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        String student=getIntent().getStringExtra("student");

//        final Fragment[] fragment = {new OnCampusFragment()};
        if (student!=null) {
            String campus=getIntent().getStringExtra("campus");
            Log.d(TAG, "hell: "+student);
            Log.d(TAG, "hell: "+campus);
            StudentJoinFragment fragment1=new StudentJoinFragment();
            Bundle bundle=new Bundle();
            bundle.putString("student",student);
            bundle.putString("campus",campus);
            fragment1.setArguments(bundle);
            loadFragment(fragment1);


        }
        final Fragment[] fragment = {new OnCampusFragment()};
//        loadFragment(fragment[0]);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeNV:
//                        loadFragment(new HomeFragment());
                        getSupportFragmentManager().beginTransaction().remove(fragment[0]).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.onCampusNV:
                        fragment[0] =new OnCampusFragment();
                        loadFragment(fragment[0]);
                        break;
                    case R.id.offCampusNV:
                        fragment[0] =new OffCampusFragment();
                        loadFragment(fragment[0]);
                        break;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        setTitle("Connectwithseniors");
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}