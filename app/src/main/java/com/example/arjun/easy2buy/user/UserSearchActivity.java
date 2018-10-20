package com.example.arjun.easy2buy.user;

import android.location.Location;
import android.location.LocationListener;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.ArrayList;

import adapter.UsersearchAdapter;
import adapter.TabUsersearchAdapter;
import model.UsersearchModel;

import com.example.arjun.easy2buy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserSearchActivity extends AppCompatActivity implements LocationListener{

    ViewPager viewPager1;
    TabLayout tabLayout1;
    TabUsersearchAdapter adapter;
    EditText editTextSearch;
    ImageView imgSearch;
    ImageView imgBack;
    double latitude,longitude;
    public static final int REQUEST_LOCATION_CODE = 99;

    String searchedItem;



    private UsersearchAdapter usersearchAdapter;
    private RecyclerView recyclerview;
    private ArrayList<UsersearchModel> usersearchModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        // assign id to toolbar contents
        editTextSearch= findViewById(R.id.editTextSearch);

        imgBack =findViewById(R.id.imgBack);

        imgSearch = findViewById(R.id.imgSearch);

        //onclick
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                searchedItem = editTextSearch.getText().toString();
                Toast.makeText(UserSearchActivity.this,"edittext"+searchedItem,Toast.LENGTH_SHORT).show();



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
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            final String uid = ds.getKey();
                            assert uid != null;
                            String item = (String) dataSnapshot.child(uid).child("productName").getValue();
                            Toast.makeText(UserSearchActivity.this,"firebase"+item,Toast.LENGTH_SHORT).show();

                            assert item != null;
                            if(item.equals(searchedItem)){
//                            Query query = mRef.child(uid).orderByChild("productName").equalTo(searchedItem);
//                            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
//                                    if(dataSnapshot1.exists()) {
                                        double newLat = (double) dataSnapshot.child(uid).child("productLat").getValue();
                                        double newLong = (double) dataSnapshot.child(uid).child("productLong").getValue();
                                        float[] distance = new float[1];
                                        Location.distanceBetween(newLat, newLong, latitude, longitude, distance);
                                Toast.makeText(UserSearchActivity.this, "Distance is "+distance[0], Toast.LENGTH_SHORT).show();
                                        // distance[0] is now the distance between these lat/lons in meters
                                        if (distance[0] > 9.0) {
                                            Toast.makeText(UserSearchActivity.this, "product find", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(UserSearchActivity.this, "product not find", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(UserSearchActivity.this,"not match",Toast.LENGTH_SHORT).show();
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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });


            }
        });



        viewPager1 = findViewById(R.id.viewpager1);

        tabLayout1 = findViewById(R.id.tablayout1);

        tabLayout1.addTab(tabLayout1.newTab().setText("Nearby"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Popular"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Top review"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Recommended"));

        adapter = new TabUsersearchAdapter(getSupportFragmentManager(),tabLayout1.getTabCount());
        viewPager1.setAdapter(adapter);
        viewPager1.setOffscreenPageLimit(4);
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
    public void onClick(View view)
    {
           }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}





