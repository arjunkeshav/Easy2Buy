package com.example.arjun.easy2buy.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.newadmin.ProductReviewModel;
import com.example.arjun.easy2buy.newadmin.ProductReviewAdapter;
import com.example.arjun.easy2buy.newadmin.UserDetails_AdminEdit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;
import model.Followers;


public class ProductDetailsActivity extends AppCompatActivity {

//    Integer[] FoodImage_Id = {R.drawable.pexel,R.drawable.pexel,R.drawable.pexel,R.drawable.pexel,R.drawable.pexel};
//    Integer[] Food_13_Image_Id={R.drawable.pexel,R.drawable.pexel,R.drawable.pexel,R.drawable.pexel,R.drawable.pexel};
//    String[] Food5_RestaurantName_Id2 = {"Cocobolo Poolside Bar + Grill","The White Rabbit","Burlamacco Ristorante"};

    private RecyclerView recyclerView;
//    private Food5_Detail_Adapter food5_detail_adapter;
//    private ArrayList<Food5_Detail_Model> modelArrayList;
//    private Food5_Detail_Adapter2 food5_detail_adapter2;
//    private ArrayList<Food5_Detail_Model2> modelArrayList2;


//viewpager code

   // private FoodPagerAdapter loginPagerAdapter;
    private ViewPager viewPager;
    private CircleIndicator indicator;

    TextView txtProductName;
    TextView txtVendorName;
    TextView txtDesc;
    ImageView imgProduct;
    ImageView post;
    EditText review;
    String uid;
    String userid;
    String userName;
    String userImage;
    LinearLayout layout;
    LinearLayout follow;
    Button buttonDirection;
    double sourcelat,sourcelong,destinationlat,destinationlong;
    private FusedLocationProviderClient client;
    String vendorId;
    RecyclerView recyclerViewReview;
    ArrayList<ProductReviewModel> product_review_array =new ArrayList<ProductReviewModel>();
    ProductReviewAdapter v;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        try {
            uid = getIntent().getStringExtra("uid");
            userid = getIntent().getStringExtra("userid");

            String dist = getIntent().getStringExtra("distance");
        } catch (Exception e) {
        }
        Log.e("data", uid);


        client = LocationServices.getFusedLocationProviderClient(this);

        imgProduct = findViewById(R.id.imageProduct);
        txtProductName= findViewById(R.id.txtProductName);
        txtVendorName = findViewById(R.id.txtVendorName);
        buttonDirection = findViewById(R.id.buttonDirection);
        txtDesc= findViewById(R.id.txtDesc);
        buttonDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //10.037171, 76.302251
                    //9.968451, 76.318988
                    getLoc();
                    if(sourcelat==0 && sourcelong==0){
                        getLoc();
                    }
                    else {


                        LatLng origin = new LatLng(sourcelat, sourcelong);
                        LatLng destination = new LatLng(destinationlat, destinationlong);
                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", origin.latitude, origin.longitude, "me", destination.latitude, destination.longitude, "Destination");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                    }

            }
        });
        follow = findViewById(R.id.layoutfollow);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followed();
            }
        });





        post = findViewById(R.id.post_review);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userName==null && userImage == null){
                    setUserName();
                }
                else{
                    addReview();
                }

            }
        });


        review = findViewById(R.id.review);
        layout = findViewById(R.id.layoutCaller);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9496633406"));
                startActivity(intent);
            }
        });

         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userImage = (String) dataSnapshot.child("profileImage").getValue();
                userName = (String) dataSnapshot.child("username").getValue();
                Toast.makeText(ProductDetailsActivity.this,userName,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        recyclerView = findViewById(R.id.RecyclerView_Food5_Detail_Id);
//        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this,LinearLayoutManager.HORIZONTAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());



// recycler view for review

        recyclerViewReview = findViewById(R.id.recyReview);

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("productReviews").orderByChild("productId").equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                        {
                            ProductReviewModel user = childDataSnapshot.getValue(ProductReviewModel.class);
                            product_review_array.add(user);

                        }
                        Log.e("productreview",""+ product_review_array.size()); //displays the key for the node
                        recyclerViewReview.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this));
                        v=new ProductReviewAdapter(ProductDetailsActivity.this, product_review_array);
                        recyclerViewReview.setAdapter(v);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




         DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Product");
        mRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds :dataSnapshot.getChildren()){

                    String itemName = (String)dataSnapshot.child("productName").getValue();
                    String itemPrice = (String)dataSnapshot.child("productPrice").getValue();
                    String itemImage = (String)dataSnapshot.child("productImage").getValue();
                    String itemDesc = (String)dataSnapshot.child("productDesc").getValue();
                    destinationlat = (double)dataSnapshot.child("productLat").getValue();
                    destinationlong = (double)dataSnapshot.child("productLong").getValue();
                    vendorId = (String)dataSnapshot.child("vendorId").getValue();


                    Picasso.with(ProductDetailsActivity.this).load(itemImage).into(imgProduct);
                    txtProductName.setText(itemName);
                    txtVendorName.setText(itemPrice);
                    txtDesc.setText(itemDesc);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        DatabaseReference vendorRef = FirebaseDatabase.getInstance().getReference("user");
//        vendorRef.child(vendorId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        //        modelArrayList = new ArrayList<>();


//        for (int i=0;i<FoodImage_Id.length;i++){
//            Food5_Detail_Model food5_detail_model = new Food5_Detail_Model(FoodImage_Id[i],Food_13_Image_Id[i]);
//
//            modelArrayList.add(food5_detail_model);
//
//        }
//        food5_detail_adapter = new Food5_Detail_Adapter(ProductDetailsActivity.this,modelArrayList);
//        recyclerView.setAdapter(food5_detail_adapter);

//Recycle 2

//       recyclerView = findViewById(R.id.RecyclerView2_Food5_Detail_Id);
////        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(ProductDetailsActivity.this);
////        recyclerView.setLayoutManager(layoutManager1);
////        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        modelArrayList2 = new ArrayList<>();

//        for (int i=0;i<Food5_RestaurantName_Id2.length;i++){
//            Food5_Detail_Model2 food5_detail_model2 = new Food5_Detail_Model2(Food5_RestaurantName_Id2[i]);
//
//            modelArrayList2.add(food5_detail_model2);
//
//        }
//        food5_detail_adapter2 = new Food5_Detail_Adapter2(ProductDetailsActivity.this,modelArrayList2);
//        recyclerView.setAdapter(food5_detail_adapter2);








//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//
//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//        loginPagerAdapter = new FoodPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(loginPagerAdapter);
//        indicator.setViewPager(viewPager);
//        loginPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

    }

    private void setUserName() {
         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userImage = (String) dataSnapshot.child("profileImage").getValue();
                userName = (String) dataSnapshot.child("username").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addReview() {

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //  Random r = new Random();
        //  int i1 = r.nextInt(100 - 28) + 28;
        String reviewId= mDatabase.child("productReviews").push().getKey();
        ProductReviewModel user=new ProductReviewModel(review.getText().toString(),uid,reviewId,userid,userName,userImage);
        mDatabase.child("productReviews").child(String.valueOf(reviewId)).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Review added ",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void followed() {

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference("followed");
        //  Random r = new Random();
        //  int i1 = r.nextInt(100 - 28) + 28;
        String followId= mDatabase.child(vendorId).push().getKey();
        Followers user=new Followers(userid,vendorId,followId);
        mDatabase.child(vendorId).child(String.valueOf(followId)).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getApplicationContext(),"Followed ",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void getLoc() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(ProductDetailsActivity.this,"permission not allowed",Toast.LENGTH_SHORT).show();

            return;
        }
        client.getLastLocation().addOnSuccessListener(ProductDetailsActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    sourcelat = location.getLatitude();
                    sourcelong = location.getLongitude();
                }

            }
        });
    }

}
