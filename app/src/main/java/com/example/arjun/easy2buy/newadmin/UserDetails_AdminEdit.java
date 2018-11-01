package com.example.arjun.easy2buy.newadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arjun.easy2buy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserDetails_AdminEdit extends AppCompatActivity {

    VendorReviewAdapter v;
    ArrayList<UserModel> a=new ArrayList<UserModel>();
    ArrayList<AddProduct> product=new ArrayList<AddProduct>();
    ArrayList<VenderReview> vendor_review_array=new ArrayList<VenderReview>();
    String id,usertype;
    ProgressDialog p;
    EditText review;
    ImageView post;
    ImageView dp;TextView pro_title;
    RecyclerView ventorreview,vendor_products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details__admin_edit);
        p=new ProgressDialog(this);
        ventorreview= findViewById(R.id.rec_ventor_review);
        vendor_products= findViewById(R.id.rec_ventor_product);
        pro_title= findViewById(R.id.product_title);
        review= findViewById(R.id.review);
        post= findViewById(R.id.post_review);


        ventorreview.setLayoutManager(new LinearLayoutManager(this));
        vendor_products.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
      a = (ArrayList<UserModel>) args.getSerializable("ARRAYLIST");

      //  a= (ArrayList<UserModel>) getIntent().gets("list");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(a.get(0).getEmail());
        id=a.get(0).getUserId();
        usertype=a.get(0).getUserType();
        dp=findViewById(R.id.dp);
        try {
            Log.d("dp-pro",a.get(0).getDp());
            Picasso.with(UserDetails_AdminEdit.this).load(a.get(0).getDp()).into(dp);
        } catch (Exception e) {
        }


        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Remove this User", Snackbar.LENGTH_LONG)
                        .setAction("Delete user", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if(usertype.equalsIgnoreCase("vendor"))
                                {listreview();
                                    p.setTitle("deleting user account ");
                                    p.setMessage("this will delete vendor and their products");
                                    p.show();
                                    removeUser();

                                }
                                else if(usertype.equalsIgnoreCase("user"))
                                {//listreview();
                                    removeSingleUser();
                                }

                            }
                        }).show();
            }
        });


        if(usertype.equalsIgnoreCase("vendor"))
        {listreview();
        pro_title.setVisibility(View.VISIBLE);
        show_allproduct();

        }
        else if(usertype.equalsIgnoreCase("user"))
        {//listreview();
             }


    }



    private void removeSingleUser() {

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("user").child(id).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed to remove user ",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void show_allproduct() {

        DatabaseReference mDatabase1;
        mDatabase1 = FirebaseDatabase.getInstance().getReference();
        mDatabase1.child("Product").orderByChild("vendorId").equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                        {
//                                        DatabaseReference mDatabase2;
//                                        mDatabase2 = FirebaseDatabase.getInstance().getReference();
//                                        mDatabase2.child("Product").orderByChild("vendorId").equalTo(id)
                            try{
                            AddProduct user = childDataSnapshot.getValue(AddProduct.class);
                                product.add(user);}
                            catch (Exception e){}

                        }
                        vendor_products.setAdapter(new ProductAdapter(UserDetails_AdminEdit.this,product));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    private void removeUser() {


        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("user").child(id).setValue(null)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    DatabaseReference mDatabase1;
                    mDatabase1 = FirebaseDatabase.getInstance().getReference();
                    mDatabase1.child("Product").orderByChild("vendorId").equalTo(id)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                                    {
//                                        DatabaseReference mDatabase2;
//                                        mDatabase2 = FirebaseDatabase.getInstance().getReference();
//                                        mDatabase2.child("Product").orderByChild("vendorId").equalTo(id)
                                        childDataSnapshot.getRef().setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                p.dismiss();
                                                finish();
                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed to remove user ",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void listreview() {
        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("vendorReviews").orderByChild("vendorId").equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                        {
                            VenderReview user = childDataSnapshot.getValue(VenderReview.class);
                            vendor_review_array.add(user);

                        }
                        Log.e("ventorreview",""+vendor_review_array.size()); //displays the key for the node
                         v=new VendorReviewAdapter(UserDetails_AdminEdit.this,vendor_review_array);
                        ventorreview.setAdapter(v);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void removeReview(final int position, final VendorReviewAdapter.Myview holder) {


        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("vendorReviews").child(String.valueOf(vendor_review_array.get(position).getReviewId())).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                          vendor_review_array.remove(position);
                          vendor_review_array.remove(position);
                        holder.c.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Failed to remove review ",Toast.LENGTH_SHORT).show();
                    }
                });


    }

}
