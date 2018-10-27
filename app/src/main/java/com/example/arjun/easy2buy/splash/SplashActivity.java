package com.example.arjun.easy2buy.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.arjun.easy2buy.MainActivity;
import com.example.arjun.easy2buy.PrefManager;
import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.admin.AdminDashboardActivity;
import com.example.arjun.easy2buy.login.SignInActivity;
import com.example.arjun.easy2buy.user.UserDashboardActivity;
import com.example.arjun.easy2buy.user.UserHomeActivity;
import com.example.arjun.easy2buy.vendor.VendorDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        logincheck();
    }

    private void logincheck() {
        //Here we will validate saved preferences
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!new PrefManager(SplashActivity.this).isUserLoggedOut())
                {

                    //user's email and password both are saved in preferences

                    FirebaseUser user = mAuth.getCurrentUser();
                    assert user != null;
                    final String userId= user.getUid();
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("user");
                    mRef.child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            // Log.v("tmz",""+ ds.getKey()); //displays the key for the node
                            String typename = (String) dataSnapshot.child("userType").getValue();





                            // }
                            assert typename != null;
                            switch (typename) {
                                case "user": {
                                    Intent intent = new Intent(SplashActivity.this, UserHomeActivity.class);
                                    intent.putExtra("uid", userId);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                    break;
                                }
                                case "admin": {
                                    Intent intent = new Intent(SplashActivity.this, AdminDashboardActivity.class);
                                    intent.putExtra("uid", userId);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    break;

                                }
                                case "vendor": {
                                    Intent intent = new Intent(SplashActivity.this, VendorDashboardActivity.class);
                                    intent.putExtra("uid", userId);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                // check logged in or not, if logged in then go to dashboasrd else go to login page
            else {
                    Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);
    }
}
