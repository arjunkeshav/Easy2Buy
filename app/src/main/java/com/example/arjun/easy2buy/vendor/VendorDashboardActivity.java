package com.example.arjun.easy2buy.vendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.arjun.easy2buy.PrefManager;
import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.login.SignInActivity;
import com.example.arjun.easy2buy.user.UserDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;

public class VendorDashboardActivity extends AppCompatActivity {
 Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dashboard);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PrefManager(VendorDashboardActivity.this).logout();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(VendorDashboardActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
