package com.example.navigation_drawer.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.navigation_drawer.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UploadNewCompActivity extends AppCompatActivity {
    private static final String TAG = "UpNewCoActivity started";


    private ImageView productPic,productPicUploadImg;
    private ProgressBar progressBar;
    private TextInputLayout productNameUL;
    private Button productSubmitBtn;
    private String imageURL,key;

    //image upload
    private boolean mGranted;
    private static final int PICK_IMAGE_REQUEST = 1001;
    private static final int STORAGE_PERMISSION_CODE = 100;

    private DatabaseReference mRef;
    private StorageReference mRefStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_new_comp);

        String campus=getIntent().getStringExtra("campus").toString();
        Log.d(TAG, "onCreate: campus : "+campus);
        TextView textView=(TextView)(findViewById(R.id.new_comp_upTV2));
        textView.setText(campus);
        try {
            init();
            click();
        }catch (Exception e){
            Log.d(TAG, "onCreateView: error :"+e.getMessage());
        }


    }


    private void click() {
        productPicUploadImg.setOnClickListener(this::dpUpload);
        productSubmitBtn.setOnClickListener(this::uploadProduct);

    }

    private void dpUpload(View view) {
        getPermission();
        progressBar.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (!mGranted){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                    return;
                }

            }
        }

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"),PICK_IMAGE_REQUEST);
    }


    private void uploadProduct(View view) {

        try {
            String name = productNameUL.getEditText().getText().toString();/*****/

            Log.d(TAG, "uploadProduct: imageUrl:" + imageURL);
            Log.d(TAG, "uploadProduct: pdtUrl  : " + key);

            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("name",name);
            hashMap.put("imageURL",imageURL);



            mRef.child(name).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: Data inserted...");
//                    Intent intent =new Intent(NewProductActivity.this,MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
                    productNameUL.getEditText().setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(NewProductActivity.this, " upload failed : "+e.toString(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: upload failed : "+e.toString());
                }
            });

        }catch (Exception e){
            Log.d(TAG, "uploadProduct: error : "+e.getMessage());
        }


    }


    private void init() {


        productPic=findViewById(R.id.prodctPicUpload);
        productPicUploadImg=findViewById(R.id.productPicUploadImg);
        progressBar=findViewById(R.id.productBarUploadImg);

        productNameUL=findViewById(R.id.productNameUploadLayout);
        productSubmitBtn=findViewById(R.id.productTotalSubmitBtn);

        String campus=getIntent().getStringExtra("campus");

        mRef= FirebaseDatabase.getInstance().getReference().child("Connectwithseniors").child("data").child("college").child("bit").child("2022").child(campus);
        mRefStorage= FirebaseStorage.getInstance().getReference("Connectwithseniors/").child("compLogo");
    }


    //for uploading image
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //for uploading image

        if(requestCode==PICK_IMAGE_REQUEST&&data!=null){
            Uri imageUri=data.getData();
            String pictureName=imageUri.getLastPathSegment().toString();
            Log.d(TAG, "onActivityResult: PICK_IMAGE_REQUEST "+PICK_IMAGE_REQUEST);

            UploadTask uploadTask=mRefStorage.child("images/"+imageUri.getLastPathSegment())
                    .putFile(imageUri);

            StorageReference imageName=mRefStorage.child("images/"+imageUri.getLastPathSegment());


            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress =(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    Log.d(TAG, "onProgress: file "+progress+" % uploaded");

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ProductUploadActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess: Image uploaded");
                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageURL =uri.toString();
                            Picasso.get().load(imageURL).into(productPic);
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "onSuccess: url : "+uri.toString());
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ProductUploadActivity.this, "ERROR : "+e.toString(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "onFailure: error : "+e.toString());
                }
            });
        }
    }

    private void getPermission() {
        String externalReadPermission = Manifest.permission.READ_EXTERNAL_STORAGE.toString();
        String externalWritePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE.toString();

        if (ContextCompat.checkSelfPermission(this,externalReadPermission)!= PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(this,externalWritePermission)!=PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{externalReadPermission,externalWritePermission}, STORAGE_PERMISSION_CODE);
            }
        }
    }
}