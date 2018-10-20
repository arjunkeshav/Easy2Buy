package com.example.arjun.easy2buy.vendor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arjun.easy2buy.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.AddProduct;


public class AddproductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    Button uploadButton;
    EditText editTextProductName;
    EditText editTextProductPrice;
    EditText editTextproductDesc;
    EditText editTextProductOffer;
    Spinner spinner;

    TextView textViewSelectImage;

    ImageView imageView;
    Uri imageUrl;


    String productName;
    String productCatog;
    String productPrice;
    String productDesc;
    String productImage;
    String productOffer;
    String userId;

    private static final int Gallery_intent=3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        userId = (String) getIntent().getExtras().get("uid");

        editTextProductName = findViewById(R.id.editTextProductname);
        editTextProductPrice= findViewById(R.id.editTextPrice);
        editTextproductDesc = findViewById(R.id.editTextDescription);
        editTextProductOffer = findViewById(R.id.editTextOffer);
        imageView = findViewById(R.id.imageViewProduct);










//Spinner
        spinner = findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(AddproductActivity.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("select type");
        categories.add("vegetable");
        categories.add("foods");
        categories.add("fruits");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

//spinner end


// textView Button Start
        textViewSelectImage = findViewById(R.id.textViewSelectImage);


        textViewSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent,Gallery_intent);


            }
        });

//textView button end here


//upLoad Button Start here


        uploadButton= findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting Values
                productName = editTextProductName.getText().toString();
                productPrice = editTextProductPrice.getText().toString();
                productDesc = editTextproductDesc.getText().toString();
                productOffer= editTextProductOffer.getText().toString();


                //FirebaseUser user = mAuth.getCurrentUser();

                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Product");

                // Creating new user node, which returns the unique key value
                // new user node would be /users/$userid/
                //String userId = mRef.push().getKey();
               // assert user != null;
               // String userId = user.getUid();
                String id = mRef.push().getKey();




                // creating user object
                AddProduct addProduct = new AddProduct(productName,productCatog,productPrice,productDesc,productImage,productOffer,userId);

                // pushing user to 'users' node using the userId
                mRef.child(Objects.requireNonNull(id)).setValue(addProduct);



            }
        });

//Upload Button end here


    }
    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

        productCatog = parent.getItemAtPosition(position).toString();



        // Showing selected spinner item
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Product");
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
                    Toast.makeText(AddproductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    imageUrl = taskSnapshot.getUploadSessionUri();
                    Task<Uri> img = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();

                    //tvSuccess.setText(img.toString());
                    productImage = img.toString();


                }
//            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        newImageUri = task.getResult();
//                        assert newImageUri != null;
//                        tvComplete.setText(newImageUri.toString());
//
//                    }
//                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddproductActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
