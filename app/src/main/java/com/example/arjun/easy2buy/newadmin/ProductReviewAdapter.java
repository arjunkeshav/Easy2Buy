package com.example.arjun.easy2buy.newadmin;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.Myview> {

    ProductViewActivity  userDetails_adminEdit;
    ArrayList<ProductReviewModel> vendor_review_array;



    public ProductReviewAdapter(ProductViewActivity productViewActivity, ArrayList<ProductReviewModel> pro_review) {
        this.userDetails_adminEdit = productViewActivity;
        this.vendor_review_array = pro_review;
    }

    @Override
    public Myview onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(userDetails_adminEdit).inflate(R.layout.child_ventor_review,parent,false);
       Myview m=new Myview(v);
       return  m;
    }

    @Override
    public void onBindViewHolder(final Myview holder, final int position) {
        holder.id.setText(String.valueOf(vendor_review_array.get(position).getProductId()));
        holder.text.setText(vendor_review_array.get(position).getReviewDesc());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              removeReview(position,holder);
            }
        });

    }


    @Override
    public int getItemCount() {
        return vendor_review_array.size();
    }

    public class Myview extends RecyclerView.ViewHolder {
        TextView id, text;
        CardView c;
        ImageView cancel;

        public Myview(View itemView) {
            super(itemView);
            cancel = itemView.findViewById(R.id.cancel);
            text = itemView.findViewById(R.id.text_reciew);
            id = itemView.findViewById(R.id.id_review);
            c = itemView.findViewById(R.id.card_review);
        }
    }

    public void removeReview(final int position, final Myview holder) {


        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("productReviews").child(String.valueOf(vendor_review_array.get(position).getReviewId())).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      //  vendor_review_array.remove(position);
                       holder.c.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(userDetails_adminEdit,"Failed to remove review ",Toast.LENGTH_SHORT).show();
                    }
                });


    }

}