package com.example.arjun.easy2buy.newadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.arjun.easy2buy.PrefManager;
import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.admin.AdminDashboardActivity;
import com.example.arjun.easy2buy.login.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity {

    LinearLayout layoutUser;
    LinearLayout layoutVendor;
    LinearLayout layoutProfile;
    LinearLayout layoutLogout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        layoutUser = findViewById(R.id.layoutUser);
        layoutVendor =findViewById(R.id.layoutVendor);
        layoutProfile = findViewById(R.id.layoutProfile);
        layoutLogout = findViewById(R.id.layoutLogout);

        final String uid = getIntent().getStringExtra("uid");


        layoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });
        layoutVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,VendorViewActivity.class);
                startActivity(intent);

            }
        });
        layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminProfile.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrefManager(AdminHomeActivity.this).logout();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(AdminHomeActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
