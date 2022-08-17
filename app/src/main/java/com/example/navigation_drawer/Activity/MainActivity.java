package com.example.navigation_drawer.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigation_drawer.Fragments.HomeFragment;
import com.example.navigation_drawer.Fragments.OffCampusFragment;
import com.example.navigation_drawer.Fragments.OnCampusFragment;
import com.example.navigation_drawer.Fragments.StudentJoinFragment;
import com.example.navigation_drawer.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity started";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    ImageView google_img;
    private Menu menu;

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            init();

        }catch (Exception e){
            Log.d(TAG, "onCreate1: "+e.getMessage());
        }

        try {
            frag();
        }catch (Exception e){
            Log.d(TAG, "onCreate2: "+e.getMessage());
        }
        try {
//            click();
        }catch (Exception e){
            Log.d(TAG, "onCreate3x: "+e.getMessage());
        }

    }

    private void click() {
        google_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                Log.d(TAG, "click: ");
//                google_img.setVisibility(View.GONE);
            }
        });
    }

    private void frag() {
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
            setTitle("Connectwithseniors");


        }
        final Fragment[] fragment = {new OnCampusFragment()};
//        loadFragment(fragment[0]);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG, "onNavigationItemSelected: item : "+item);
                FirebaseUser user =mAuth.getCurrentUser();
//                if (user==null)
//                    Log.d(TAG, "onNavigationItemSelected: name : null");
//                else
//                    Log.d(TAG, "onNavigationItemSelected: name : "+mAuth.getCurrentUser().getDisplayName());
                switch (item.getItemId()){
                    case R.id.homeNV:
//                        loadFragment(new HomeFragment());
//                        if (user==null){
//                            signIn();
//                        }else {
                            getSupportFragmentManager().beginTransaction().remove(fragment[0]).commit();
                            drawerLayout.closeDrawer(GravityCompat.START);

//                        }

                        break;
                    case R.id.onCampusNV:
//                        if (user==null){
//                            signIn();
//                        }else {
                            fragment[0] =new OnCampusFragment();
                            loadFragment(fragment[0]);
//                        }

                        break;
                    case R.id.offCampusNV:
//                        if (user==null){
//                            signIn();
//                        }else {
                            fragment[0] =new OffCampusFragment();
                            loadFragment(fragment[0]);
//                        }

                        break;
                    case R.id.logInNV:
                        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                        Log.d(TAG, "onNavigationItemSelected: item : "+item);
                        try {
                            if (item.toString().equals("Log in/Sign Up")){
                                signIn();
                                item.setTitle("Log Out");
                            }else {
                                item.setTitle("Log in/Sign Up");
                                mAuth.signOut();
                                if (mAuth.getCurrentUser()==null){
                                    google_img.setVisibility(View.VISIBLE);
                                }
                            }
//                            item.setTitle("hi");
                        }catch (Exception e){
                            Log.d(TAG, "onNavigationItemSelected: error : "+e.getMessage());
                        }
                        break;
                }
                return false;
            }
        });


    }

    private void signIn() {
        Intent singInIntent=gsc.getSignInIntent();
        startActivityForResult(singInIntent,100);
    }

    private void init() {
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        frameLayout=findViewById(R.id.frame);
        navigationView=findViewById(R.id.nav_drawer);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestIdToken(getString(R.string.default_web_client_id)).build();
        gsc= GoogleSignIn.getClient(this,gso);
//        Menu menu=findViewById(R.id.selectItem);
//        Menu menu = toolbar.getMenu();
//        MenuItem menuItem = menu.findItem(R.id.logInNV);
//        MenuItem menuItem = ((Toolbar)findViewById(R.id.toolbar)).getMenu().findItem(R.id.logInNV);

//        Log.d(TAG, "init: menuItem : "+menu);
//        Log.d(TAG, "init: menuItem : "+menuItem);
//        menuItem.setTitle("New title");

//        LayoutInflater inflater=(LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view=inflater.inflate(R.layout.header,null);
        google_img=findViewById(R.id.google_login);
//        TextView textView=view.findViewById(R.id ew.getText());
//        google_img.setVisibility(View.INVISIBLE);

//        if (mAuth.getCurrentUser()!=null){
//            google_img.setVisibility(View.INVISIBLE);
//        }
        google_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                Log.d(TAG, "click: ");
            }
        });

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "user logged in", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onComplete: user logged in");
                    FirebaseUser user=mAuth.getCurrentUser();
                    if (mAuth.getCurrentUser()!=null){
                        google_img.setVisibility(View.INVISIBLE);
                    }
                }else {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
//                HomeActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "handleGoogleSignIn: Error status message: "
                        + GoogleSignInStatusCodes.getStatusCodeString(e.getStatusCode()));
            }
        }
    }



}
