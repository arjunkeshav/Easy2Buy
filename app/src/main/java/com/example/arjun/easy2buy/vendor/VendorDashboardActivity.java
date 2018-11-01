package com.example.arjun.easy2buy.vendor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arjun.easy2buy.PrefManager;
import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.login.SignInActivity;
import com.example.arjun.easy2buy.user.UserDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.ProductAdapt;

public class VendorDashboardActivity extends AppCompatActivity {
 ImageView viewProfile;
 ImageView logout;
 ImageView addProduct;
 FirebaseUser firebaseUser;
 FirebaseAuth mAuth;
 String userid;
 RecyclerView recyView;
 ProductAdapt adapter;



    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arjunlay);

        recyView = findViewById(R.id.recyView);
        userid = getIntent().getStringExtra("uid");


       // firebaseUser = mAuth.getCurrentUser();
        logout = findViewById(R.id.imglogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PrefManager(VendorDashboardActivity.this).logout();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(VendorDashboardActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        addProduct = findViewById(R.id.imgaddProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userid != null) {
                    //String uid = firebaseUser.getUid();
                    Intent intent = new Intent(VendorDashboardActivity.this,AddproductActivity.class);
                    intent.putExtra("uid", userid);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(VendorDashboardActivity.this,"current user is not assigned",Toast.LENGTH_SHORT).show();
                }


            }
        });
        viewProfile = findViewById(R.id.imgviewProfile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userid != null) {
                    //String uid = firebaseUser.getUid();
                    Intent intent = new Intent(VendorDashboardActivity.this,VendorProfileActivity.class);
                    intent.putExtra("uid", userid);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(VendorDashboardActivity.this,"current user is not assigned",Toast.LENGTH_SHORT).show();
                }


            }
        });

        //final String usrid = getIntent().getStringExtra("uid");


        Toast.makeText(VendorDashboardActivity.this,"id "+userid,Toast.LENGTH_SHORT).show();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Product");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> productName = new ArrayList<String>();
                ArrayList<String> productPrice = new ArrayList<String>();
                ArrayList<String> productImage = new ArrayList<String>();
                ArrayList<String> productId = new ArrayList<String>();


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    final String uid = ds.getKey();
                    //Toast.makeText(VendorDashboardActivity.this,"id "+userid,Toast.LENGTH_SHORT).show();
                    assert uid != null;
                    String userId = (String) dataSnapshot.child(uid).child("vendorId").getValue();
                   // Toast.makeText(VendorDashboardActivity.this,"itemId"+userId,Toast.LENGTH_SHORT).show();
                    if(userid.equals(userId))
                    {
                        //Toast.makeText(VendorDashboardActivity.this,"id "+uid,Toast.LENGTH_SHORT).show();
                        String itemPrice = (String) dataSnapshot.child(uid).child("productPrice").getValue();
                        String itemImage = (String) dataSnapshot.child(uid).child("productImage").getValue();
                        String itemName = (String) dataSnapshot.child(uid).child("productName").getValue();
                        String itemId =(String) dataSnapshot.child(uid).child("productId").getValue();
                       /// Toast.makeText(VendorDashboardActivity.this,"itemId"+itemId,Toast.LENGTH_SHORT).show();
                       // Toast.makeText(VendorDashboardActivity.this,"itemName"+itemName,Toast.LENGTH_SHORT).show();


                        productImage.add(itemImage);
                        productName.add(itemName);
                        productPrice.add(itemPrice);
                        productId.add(itemId);


                        }



                }
                Log.e("lengthm", String.valueOf(productId.size()));
                Log.e("length NaMe", String.valueOf(productName.size()));
                recyView.setLayoutManager(new LinearLayoutManager(VendorDashboardActivity.this));
                adapter = new ProductAdapt(VendorDashboardActivity.this,productImage,productName,productPrice,productId);
                recyView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
