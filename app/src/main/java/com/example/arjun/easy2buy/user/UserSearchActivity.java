package com.example.arjun.easy2buy.user;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.lang.ref.Reference;
import java.util.ArrayList;

import adapter.UsersearchAdapter;
import adapter.TabUsersearchAdapter;
import fragment.Nearby;
import model.UsersearchModel;

import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.login.SignUp;
import com.example.arjun.easy2buy.login.SignUpActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class UserSearchActivity extends AppCompatActivity {

    String itemVendor;


    ViewPager viewPager1;
    TabLayout tabLayout1;
    TabUsersearchAdapter adapter;
    EditText editTextSearch;
    ImageView imgSearch;
    ImageView imgBack;
    double latitude, longitude;
    public static final int REQUEST_LOCATION_CODE = 99;

    String searchedItem;


    //creating client
    private FusedLocationProviderClient client;


    private UsersearchAdapter usersearchAdapter;
    private RecyclerView recyclerview;
    private ArrayList<UsersearchModel> usersearchModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        // assign id to toolbar contents
        editTextSearch = findViewById(R.id.editTextSearch);

        imgBack = findViewById(R.id.imgBack);

        imgSearch = findViewById(R.id.imgSearch);


        //setting client

        client = LocationServices.getFusedLocationProviderClient(this);


        //onclick
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextSearch.clearFocus();



                getLoc();


                if(latitude == 0 && longitude ==0)
                {
                    getLoc();
                }
                else
                {




                //creating String array for product name






                searchedItem = editTextSearch.getText().toString();
                Toast.makeText(UserSearchActivity.this, "edittext" + searchedItem, Toast.LENGTH_SHORT).show();


                //checking in database


                final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Product");

                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                       // Log.v("tmz",""+ ds.getKey()); //displays the key for the node
//                      typename = (String) dataSnapshot.child("userType").getValue();
//
//                    }
                        ArrayList<String> productName = new ArrayList<>();
                        ArrayList<String> productPrice = new ArrayList<>();
                        ArrayList<String> productDist = new ArrayList<>();
                        ArrayList<String> productVendor = new ArrayList<>();
                        ArrayList<String> productUri = new ArrayList<>();


                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            final String uid = ds.getKey();
                            assert uid != null;
                            String itemName = (String) dataSnapshot.child(uid).child("productName").getValue();
                           // Toast.makeText(UserSearchActivity.this, "firebase" + itemName, Toast.LENGTH_SHORT).show();

                            assert itemName != null;
                            if (itemName.equals(searchedItem)) {
//                            Query query = mRef.child(uid).orderByChild("productName").equalTo(searchedItem);
//                            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
//                                    if(dataSnapshot1.exists()) {
                                double newLat = (double) dataSnapshot.child(uid).child("productLat").getValue();
                                double newLong = (double) dataSnapshot.child(uid).child("productLong").getValue();

                                float[] distance = new float[1];
                                Location.distanceBetween(newLat, newLong, latitude, longitude, distance);
                               // Toast.makeText(UserSearchActivity.this, "Distance is " + distance[0], Toast.LENGTH_SHORT).show();
                                // distance[0] is now the distance between these lat/lons in meters
                                if (distance[0] > 9.0) {
//                                    Toast.makeText(UserSearchActivity.this, "product find", Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(UserSearchActivity.this, "current Latlog :" + latitude + "," + longitude + " " + "product Latlog :" + newLat + "," + newLong, Toast.LENGTH_LONG).show();
                                    String itemPrice = (String) dataSnapshot.child(uid).child("productPrice").getValue();
                                    int dist= (int)distance[0];
                                    String itemDist = String.valueOf(dist) ;
                                    String itemUri =(String) dataSnapshot.child(uid).child("productImage").getValue();
                                    String pathId = (String) dataSnapshot.child(uid).child("vendorId").getValue();

                                    //fetching vendor details

                                    DatabaseReference vendorRef = FirebaseDatabase.getInstance().getReference("user");
                                    assert pathId != null;
                                    vendorRef.child(pathId).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                          itemVendor =(String) dataSnapshot.child("username").getValue();
                                          Toast.makeText(UserSearchActivity.this,"vendor name is"+itemVendor,Toast.LENGTH_SHORT).show();


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                    //String itemOwner = (String) dataSnapshot.child(uid).child("productName").getValue();
                                   // int itemReviews = (int) dataSnapshot.child(uid).child("productName").getValue();

                                    productName.add(itemName);
                                    productPrice.add(itemPrice);
                                    productDist.add(itemDist);
                                    productUri.add(itemUri);
                                    productVendor.add(itemVendor);

                                    Toast.makeText(UserSearchActivity.this,"vendor outside"+itemVendor,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(UserSearchActivity.this,"vendor name is"+productVendor,Toast.LENGTH_SHORT).show();


                                    //select tab








                                } else {
                                    Toast.makeText(UserSearchActivity.this, "product not find", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UserSearchActivity.this, "not match", Toast.LENGTH_SHORT).show();
                            }
//
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
                        }
                        viewPager1 = findViewById(R.id.viewpager1);

                        tabLayout1 = findViewById(R.id.tablayout1);

                        tabLayout1.addTab(tabLayout1.newTab().setText("Nearby"));
                        tabLayout1.addTab(tabLayout1.newTab().setText("Popular"));
                        //tabLayout1.addTab(tabLayout1.newTab().setText("Top review"));
                        //tabLayout1.addTab(tabLayout1.newTab().setText("Recommended"));

                        adapter = new TabUsersearchAdapter(getSupportFragmentManager(), tabLayout1.getTabCount(),productUri,productName,productPrice,productDist,productVendor);
                        viewPager1.setAdapter(adapter);
                        viewPager1.setOffscreenPageLimit(2);
                        viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));
                        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                viewPager1.setCurrentItem(tab.getPosition());

                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });







                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });


                }    //if end here


            }
        });



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
        client.getLastLocation().addOnSuccessListener(UserSearchActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

            }
        });
    }

    public void onClick(View view)
    {
           }


}





