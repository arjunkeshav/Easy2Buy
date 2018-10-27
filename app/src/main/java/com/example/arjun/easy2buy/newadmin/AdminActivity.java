package com.example.arjun.easy2buy.newadmin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import com.example.arjun.easy2buy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
RecyclerView recyclerView,recyclerView_users;

ArrayList<UserModel> usersArray=new ArrayList<UserModel>();
    ArrayList<UserModel> vendorArray=new ArrayList<UserModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView= findViewById(R.id.rec);
        recyclerView_users= findViewById(R.id.rec_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_users.setLayoutManager(new LinearLayoutManager(this));
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//
//                DatabaseReference mDatabase;
//                mDatabase = FirebaseDatabase.getInstance().getReference();
//                mDatabase.child("simi").setValue("text","text1");
//
//
//
//            }
//        });
        try{
            vendorArray.clear();
        recylercall("vendor");
            Log.e("simireccall",""+vendorArray.size());

        }
       finally {
            usersArray.clear();
            recylercall("user");
        }
    }

    private void recylercall(final String admin) {

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("user").orderByChild("userType").equalTo(admin)
               .addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                       {
                           UserModel user = childDataSnapshot.getValue(UserModel.class);
                          Log.e("arjun",""+usersArray.size()); //displays the key for the node
                           if(admin.equalsIgnoreCase("vendor"))
                           { vendorArray.add(user);}
                           else if(admin.equalsIgnoreCase("user"))
                           { usersArray.add(user);}

                       }

                       if(admin.equalsIgnoreCase("vendor"))
                       {       Log.d("arjun",""+vendorArray.size());
                       recyclerView.setAdapter(new VendorAdapter(AdminActivity.this,vendorArray));
                           Log.e("arjun recall_user",""+usersArray.size());

                       }
                       else if(admin.equalsIgnoreCase("user"))
                       {    Log.d("simiuser",""+usersArray.size());
                       recyclerView_users.setAdapter(new VendorAdapter(AdminActivity.this,usersArray));}




                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
    }

//    private void callproduct_data(ArrayList<UserModel> usersArray) {
//
//        DatabaseReference mDatabase;
//// ...
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//
//        mDatabase.child("users").orderByChild("vendorId").equalTo(usersArray.get())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
//                        {
//                            UserModel user = childDataSnapshot.getValue(UserModel.class);
//                            //  Log.v(TAG,""+ user.getAddress()); //displays the key for the node
//                            usersArray.add(user);
//                        }
//
//                        callproduct_data(usersArray);
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();


//        try{
//            vendorArray.clear();
//            recylercall("vendor");
//          //  Log.e("simireccall",""+vendorArray.size());
//
//        }
//        finally {
//            usersArray.clear();
//            recylercall("user");
//        }
    }
}
