package com.example.arjun.easy2buy.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arjun.easy2buy.vendor.AddproductActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import com.example.arjun.easy2buy.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity{
    ImageView imageView;
    Button button;
    String path;

    public static final int Gallery_intent=3;
    Uri imageUrl;
    String userImg;
    UploadTask.TaskSnapshot newImageUri;
    TextView tvSuccess;
    TextView tvComplete;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_demo);



        imageView = findViewById(R.id.imageProfile);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent,Gallery_intent);


            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==Gallery_intent && data !=null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            Uri uri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri u= data.getData();
            File file= new File(u.getPath());
            path =file.getName();


            StorageReference storageReference = FirebaseStorage.getInstance().getReference("UserProfile").child(path);
            assert uri != null;
            storageReference.putFile(uri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");



                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                  progressDialog.dismiss();
                                  Toast.makeText(UserProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                  imageUrl = taskSnapshot.getUploadSessionUri();
                                  Task<Uri> img = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();

                                  tvSuccess.setText(img.toString());
                                  setImage();



                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                       newImageUri = task.getResult();
                        assert newImageUri != null;
                        tvComplete.setText(newImageUri.toString());

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                                    Toast.makeText(UserProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


                 //   storageReference.putFile(uri).
//                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
//                                            .getTotalByteCount());
//                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                                }
//                            });

        }
    }

    private void setImage() {
        Log.e("path",path);

        StorageReference storageRefere = FirebaseStorage.getInstance().getReference("Product").child(path);

        storageRefere.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uri url=uri;
                userImg=url.toString();
                Toast.makeText(UserProfileActivity.this,userImg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
