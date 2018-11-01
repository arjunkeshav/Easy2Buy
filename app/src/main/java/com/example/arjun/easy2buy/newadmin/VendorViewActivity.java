package com.example.arjun.easy2buy.newadmin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.arjun.easy2buy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VendorViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserModel> vendorArray=new ArrayList<UserModel>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_view);
        recyclerView= findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try{
            vendorArray.clear();


        }
        finally {
            vendorArray.clear();
            recylercall("vendor");


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
                            Log.e("arjun",""+vendorArray.size()); //displays the key for the node
                            if(admin.equalsIgnoreCase("vendor"))
                            { vendorArray.add(user);}


                        }

                        if(admin.equalsIgnoreCase("vendor"))
                        {       Log.d("arjun",""+vendorArray.size());
                            recyclerView.setAdapter(new VendorAdapter(VendorViewActivity.this,vendorArray));
                            Log.e("arjun recall_user",""+vendorArray.size());

                        }





                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
