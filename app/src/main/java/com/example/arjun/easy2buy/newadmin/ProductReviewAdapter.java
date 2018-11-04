package com.example.arjun.easy2buy.newadmin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arjun.easy2buy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.Myview> {

    Activity productReview;
    ArrayList<ProductReviewModel> product_review_array;



    public ProductReviewAdapter(Activity productReview, ArrayList<ProductReviewModel> pro_review) {
        this.productReview = productReview;
        this.product_review_array = pro_review;
    }

    @Override
    public Myview onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(productReview).inflate(R.layout.item_reviews,parent,false);
       Myview m=new Myview(v);
       return  m;
    }

    @Override
    public void onBindViewHolder(final Myview holder, final int position) {
        holder.id.setText(String.valueOf(product_review_array.get(position).getUserName()));
        holder.text.setText(product_review_array.get(position).getReviewDesc());
//        holder.cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              removeReview(position,holder);
//            }
//        });t
        try {
            Log.e("url",product_review_array.get(position).getUserImage());
            Picasso.with(productReview).load(product_review_array.get(position).getUserImage()).into(holder.userImage);
        }
        catch(Exception e){
           e.printStackTrace();

        }

    }


    @Override
    public int getItemCount() {
        return product_review_array.size();
    }

    public class Myview extends RecyclerView.ViewHolder {
        TextView id, text;
        CardView c;
        ImageView cancel;
        ImageView userImage;

        public Myview(View itemView) {
            super(itemView);
            cancel = itemView.findViewById(R.id.cancel);
            text = itemView.findViewById(R.id.text_reciew);
            id = itemView.findViewById(R.id.id_review);
            userImage = itemView.findViewById(R.id.userImage);
            //c = itemView.findViewById(R.id.card_review);
        }
    }

    public void removeReview(final int position, final Myview holder) {


        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("productReviews").child(String.valueOf(product_review_array.get(position).getReviewId())).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      //  product_review_array.remove(position);
                       holder.c.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(productReview,"Failed to remove review ",Toast.LENGTH_SHORT).show();
                    }
                });


    }

}