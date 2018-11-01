package com.example.arjun.easy2buy.vendor;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arjun.easy2buy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class UpdateProduct extends AppCompatActivity {

    String path;

    String itemName;
    String itemPrice;
    String itemImage;
    String itemOffer;
    String itemCatog;
    String itemDesc;


    EditText productName;
    EditText productPrice;
    EditText productOffer;
    EditText productDesc;
    ImageView productImage;
    Spinner spinner;

    Button button;

    StorageReference storageReference;


    private static final int Gallery_intent=3;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        productName = findViewById(R.id.editTextProductname);
        productPrice = findViewById(R.id.editTextPrice);
        productOffer = findViewById(R.id.editTextOffer);
        productDesc = findViewById(R.id.editTextDescription);
        productImage = findViewById(R.id.imageViewProduct);
        spinner = findViewById(R.id.spinner);

        button = findViewById(R.id.uploadButton);
        final String productid = getIntent().getStringExtra("uid");
        Toast.makeText(UpdateProduct.this,productid,Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemName = productName.getText().toString();
                itemOffer= productOffer.getText().toString();
                itemPrice = productPrice.getText().toString();
                itemDesc = productDesc.getText().toString();
                itemImage = productName.getText().toString();



                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Product").child(productid);
                mRef.child("productName").setValue(itemImage);
                mRef.child("productPrice").setValue(itemPrice);
                mRef.child("productImage").setValue(itemImage);
                mRef.child("productOffer").setValue(itemOffer);
                mRef.child("productCatog").setValue(itemCatog);
                mRef.child("productOffer").setValue(itemDesc);









            }
        });







        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Product");
        mRef.child(productid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 itemName = (String) dataSnapshot.child("productName").getValue();
                 itemPrice = (String) dataSnapshot.child("productPrice").getValue();
                 itemImage = (String) dataSnapshot.child("productImage").getValue();
                 itemOffer = (String) dataSnapshot.child("productOffer").getValue();
                 itemCatog = (String) dataSnapshot.child("productCatog").getValue();
                 itemDesc = (String) dataSnapshot.child("productOffer").getValue();

                 productName.setText(itemName);
                 productDesc.setText(itemDesc);
                 productOffer.setText(itemOffer);
                 productPrice.setText(itemPrice);
                Picasso.with(UpdateProduct.this).load(itemImage).into(productImage);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==Gallery_intent && data !=null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            Uri uri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                productImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri u= data.getData();
            File file= new File(u.getPath());
            path =file.getName();
            storageReference = FirebaseStorage.getInstance().getReference("Product").child(path);
            assert uri != null;
            storageReference.putFile(uri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");


                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProduct.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    //imageUrl = taskSnapshot.getUploadSessionUri();
//                    Task<Uri> img = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
//                      try{
//                          Uri url=taskSnapshot.getMetadata().
//                      }catch (Exception e){}
                    //tvSuccess.setText(img.toString());
                    // productImage = img.toString();

                    setImage();


                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    // if(task.isSuccessful()) {
//                        imageUrl = task.getResult();
//                        assert imageUrl != null;
//                        productImage= imageUrl.toString();

                    //}
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProduct.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

//
        }

    }

    private void setImage() {
        Log.e("path",path);

        StorageReference storageRefere = FirebaseStorage.getInstance().getReference("Product").child(path);

        storageRefere.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uri url=uri;
                itemImage=url.toString();
                Toast.makeText(UpdateProduct.this,itemImage,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
