package com.example.arjun.easy2buy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.arjun.easy2buy.login.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView = (TextView) findViewById(R.id.textHello);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrefManager(MainActivity.this).logout();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
        // this is for testing
    }
}

// code for retrieving all image url from database


//    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference imagesRef = rootRef.child("images");
//    ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                String url = ds.getValue(String.class);
//                Log.d("TAG", url);
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {}
//    };
//imagesRef.addListenerForSingleValueEvent(valueEventListener);
