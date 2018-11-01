package com.example.arjun.easy2buy.vendor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arjun.easy2buy.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import model.AddProduct;


public class AddproductActivity extends AppCompatActivity {


    private FusedLocationProviderClient client;

    String path;
    Button uploadButton;
    EditText editTextProductName;
    EditText editTextProductPrice;
    EditText editTextproductDesc;
    EditText editTextProductOffer;
    Spinner spinner;

    TextView textViewSelectImage;

    ImageView imageView;
    UploadTask.TaskSnapshot imageUrl;

    double productLat,productLong;

    StorageReference storageReference;


    String productName;
    String productCatog;
    String productPrice;
    String productDesc;
    String productImage= null;
    String productOffer;
    String vender;

    String userId;

    private static final int Gallery_intent=3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        //client
        client = LocationServices.getFusedLocationProviderClient(this);

        userId = (String) getIntent().getExtras().get("uid");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vender = (String)dataSnapshot.child("username").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editTextProductName = findViewById(R.id.editTextProductname);
        editTextProductPrice= findViewById(R.id.editTextPrice);
        editTextproductDesc = findViewById(R.id.editTextDescription);
        editTextProductOffer = findViewById(R.id.editTextOffer);
        imageView = findViewById(R.id.imageViewProduct);










//Spinner
        spinner = findViewById(R.id.spinner);


        // Initializing a String Array
        String[] category = new String[]{
                "Select an item...",
                "Fruits",
                "vegitable",
                "Foods",
                "Carrot weed"
        };

        final List<String> categoryList = new ArrayList<>(Arrays.asList(category));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,categoryList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productCatog = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + productCatog, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

                //geting location
                getLoc();

                if(productLong==0 && productLat== 0)
                {
                    getLoc();
                }else {

                    if(productImage == null) {

                        setImage();
                    }
                    else {


                        //setting Values
                        productName = editTextProductName.getText().toString();
                        productPrice = editTextProductPrice.getText().toString();
                        productDesc = editTextproductDesc.getText().toString();
                        productOffer = editTextProductOffer.getText().toString();

                        if(TextUtils.isEmpty(productName) || TextUtils.isEmpty(productDesc) ||TextUtils.isEmpty(productPrice) || TextUtils.isEmpty(productOffer) ){
                            Toast.makeText(AddproductActivity.this,"please fill the blank field and then upload ",Toast.LENGTH_SHORT).show();

                        }
                        else {


                            //FirebaseUser user = mAuth.getCurrentUser();

                            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Product");

                            // Creating new user node, which returns the unique key value
                            // new user node would be /users/$userid/
                            //String userId = mRef.push().getKey();
                            // assert user != null;
                            // String userId = user.getUid();
                            String id = mRef.push().getKey();


                            // creating user object
                            AddProduct addProduct = new AddProduct(productName, productCatog, productPrice, productDesc, productImage, productOffer, userId, productLat, productLong, vender, id);

                            // pushing user to 'users' node using the userId
                            mRef.child(Objects.requireNonNull(id)).setValue(addProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AddproductActivity.this, "uploaded", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddproductActivity.this,"some Failure",Toast.LENGTH_SHORT).show();

                                }
                            });


                            editTextProductOffer.setText(" ");
                            editTextproductDesc.setText(" ");
                            editTextProductName.setText(" ");
                            editTextProductPrice.setText(" ");
                        }



                    }

                }



            }
        });

//Upload Button end here


    }

    private void getLoc() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            client.getLastLocation().addOnSuccessListener(AddproductActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        productLat = location.getLatitude();
                        productLong = location.getLongitude();
                    }

                }
            });
        }


//    @Override
//
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // On selecting a spinner item
//
//        productCatog = parent.getItemAtPosition(position).toString();
//
//
//
//        // Showing selected spinner item
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

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
                    Toast.makeText(AddproductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AddproductActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

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
                productImage=url.toString();
                Toast.makeText(AddproductActivity.this,productImage,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
