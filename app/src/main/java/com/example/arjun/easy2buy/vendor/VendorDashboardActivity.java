package com.example.arjun.easy2buy.vendor;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arjun.easy2buy.PrefManager;
import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.login.SignInActivity;
import com.example.arjun.easy2buy.user.UserDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VendorDashboardActivity extends AppCompatActivity {
 Button button;
 Button addProduct;
 FirebaseUser firebaseUser;
 FirebaseAuth mAuth;
 String uid;



    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dashboard);
        uid = (String) getIntent().getExtras().get("uid");


       // firebaseUser = mAuth.getCurrentUser();
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
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
        addProduct = findViewById(R.id.addProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uid != null) {
                    //String uid = firebaseUser.getUid();
                    Intent intent = new Intent(VendorDashboardActivity.this,AddproductActivity.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(VendorDashboardActivity.this,"current user is not assigned",Toast.LENGTH_SHORT).show();
                }


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
