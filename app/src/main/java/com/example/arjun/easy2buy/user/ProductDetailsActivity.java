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
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.newadmin.ProductReviewModel;
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

import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;


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
    ImageView imgProduct;
    ImageView post;
    EditText review;
    String uid;
    LinearLayout layout;
    Button buttonDirection;
    double sourcelat,sourcelong,destinationlat,destinationlong;
    private FusedLocationProviderClient client;
    String vendorId;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        client = LocationServices.getFusedLocationProviderClient(this);

        imgProduct = findViewById(R.id.imageProduct);
        txtProductName= findViewById(R.id.txtProductName);
        txtVendorName = findViewById(R.id.txtVendorName);
        buttonDirection = findViewById(R.id.buttonDirection);
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




        post = findViewById(R.id.post_review);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReview();
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




        recyclerView = findViewById(R.id.RecyclerView_Food5_Detail_Id);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        uid = getIntent().getStringExtra("uid");

        String dist = getIntent().getStringExtra("distance");


        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Product");
        mRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds :dataSnapshot.getChildren()){

                    String itemName = (String)dataSnapshot.child("productName").getValue();
                    String itemPrice = (String)dataSnapshot.child("productPrice").getValue();
                    String itemImage = (String)dataSnapshot.child("productImage").getValue();
                    destinationlat = (double)dataSnapshot.child("productLat").getValue();
                    destinationlong = (double)dataSnapshot.child("productLong").getValue();
                    vendorId = (String)dataSnapshot.child("vendorId").getValue();


                    Picasso.with(ProductDetailsActivity.this).load(itemImage).into(imgProduct);
                    txtProductName.setText(itemName);
                    txtVendorName.setText(itemPrice);


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

       // recyclerView = findViewById(R.id.RecyclerView2_Food5_Detail_Id);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(ProductDetailsActivity.this);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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

    private void addReview() {

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //  Random r = new Random();
        //  int i1 = r.nextInt(100 - 28) + 28;
        String reviewId= mDatabase.child("productReviews").push().getKey();
        ProductReviewModel vendor=new ProductReviewModel(review.getText().toString(),uid,reviewId);
        mDatabase.child("productReviews").child(String.valueOf(reviewId)).setValue(vendor).addOnSuccessListener(new OnSuccessListener<Void>() {
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
