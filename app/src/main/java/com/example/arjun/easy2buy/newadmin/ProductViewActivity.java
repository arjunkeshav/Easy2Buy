package com.example.arjun.easy2buy.newadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arjun.easy2buy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductViewActivity extends AppCompatActivity {
    ArrayList<ProductReviewModel> pro_review=new ArrayList<ProductReviewModel>();
    UserDetails_AdminEdit userDetails_adminEdit; ArrayList<AddProduct> product;
    int position;
    String id,vender_id;
    ProgressDialog p;
    EditText review;
    ImageView post;
    TextView category,price,disc;
    ImageView dp;TextView pro_title;
    RecyclerView shop_review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        p=new ProgressDialog(this);
        shop_review= findViewById(R.id.rec_shop);
        category= findViewById(R.id.product_category);
        price= findViewById(R.id.product_price);
        disc= findViewById(R.id.product_dis);
        review= findViewById(R.id.review);
        post= findViewById(R.id.post_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        shop_review.setLayoutManager(new LinearLayoutManager(ProductViewActivity.this));

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        position=args.getInt("pos");
        product = (ArrayList<AddProduct>) args.getSerializable("ARRAYLIST");
        try{
        toolbar.setTitle(product.get(position).getProductName());}
        catch (Exception e){};
        setSupportActionBar(toolbar);
        try {
            id=product.get(position).getProductId();
            vender_id=product.get(position).getVendorId();
            dp=findViewById(R.id.dp);
            category.setText("Catagory :"+product.get(position).getProductCatog());
            price.setText(product.get(position).getProductPrice());
            disc.setText(product.get(position).getProductDesc());
            Log.d("dp-pro",product.get(position).getProductImage());
            Picasso.with(ProductViewActivity.this).load(product.get(position).getProductImage()).into(dp);

        } catch (Exception e) {
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Delete", Snackbar.LENGTH_LONG)
                        .setAction("Delete", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                p.setTitle("deleting user accound ");
                                p.setMessage("this will delete vendor and products");
                                p.show();

                                delete_shop_review();
                            }
                        }).show();
            }
        });


     post.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // addreview();
         }
     });



    }



    private void delete_shop_review() {

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("vendorReviews").orderByChild("vendorId").equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                        {
                            ProductReviewModel user = childDataSnapshot.getValue(ProductReviewModel.class);
                            pro_review.add(user);

                        }
                        Log.e("simiventorreview",""+pro_review.size()); //displays the key for the node
                       ProductReviewAdapter v=new ProductReviewAdapter(ProductViewActivity.this,pro_review);
                        shop_review.setAdapter(v);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



}
