package com.example.arjun.easy2buy.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.arjun.easy2buy.PrefManager;
import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.admin.AdminDashboardActivity;
import com.example.arjun.easy2buy.login.SignInActivity;
import com.example.arjun.easy2buy.map.MapsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.squareup.picasso.Picasso;

public class UserHomeActivity extends AppCompatActivity {
    BottomBar bottomBar;
    TextView textView;
    EditText editText;
    String uid;
    ImageView profile_image;
    TextView txtname;
    double latitude,longitude;

    private FusedLocationProviderClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = LocationServices.getFusedLocationProviderClient(this);



        uid= getIntent().getStringExtra("uid");

        profile_image =findViewById(R.id.profile_image);
        txtname = findViewById(R.id.txtname);



        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("user");
        mRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = (String) dataSnapshot.child("username").getValue();
                String eMail = (String) dataSnapshot.child("email").getValue();
                String image = (String) dataSnapshot.child("profileImage").getValue();
                String name = "Hi, "+userName;
                txtname.setText(name);

                Picasso.with(UserHomeActivity.this).load(image).into(profile_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

                bottomBar = findViewById(R.id.bottomBar);


        for (int i = 0; i < bottomBar.getTabCount(); i++) {
            bottomBar.getTabAtPosition(i).setGravity(Gravity.CENTER_VERTICAL);
        }

        bottomBar.getTabAtPosition(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        bottomBar.getTabAtPosition(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this,UserDashboardActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        bottomBar.getTabAtPosition(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this,UserProfileActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        editText =findViewById(R.id.edittextSearch);



        textView = findViewById(R.id.txtSearch);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String text= editText.getText().toString();

                 getLoc();
                 if(latitude==0 && longitude==0){
                     getLoc();
                 }
                 else {

                     if (!TextUtils.isEmpty(text)) {


                         Intent intent = new Intent(UserHomeActivity.this, UserSearchActivity.class);
                         intent.putExtra("uid", uid);
                         intent.putExtra("text", text);
                         Bundle b = new Bundle();
                         b.putDouble("latitude", latitude);
                         b.putDouble("longitude", longitude);

                         intent.putExtras(b);
                         startActivity(intent);

                     } else {
                         Toast.makeText(UserHomeActivity.this, "Please type something to search", Toast.LENGTH_SHORT).show();
                         editText.findFocus();

                     }
                 }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            new PrefManager(UserHomeActivity.this).logout();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Intent intent = new Intent(UserHomeActivity.this,SignInActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_dashboard, menu);
        return true;
    }
    private void getLoc() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(UserHomeActivity.this,"permission not allowed",Toast.LENGTH_SHORT).show();

            return;
        }
        client.getLastLocation().addOnSuccessListener(UserHomeActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

            }
        });
    }

}
