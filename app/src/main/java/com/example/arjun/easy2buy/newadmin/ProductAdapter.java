package com.example.arjun.easy2buy.newadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arjun.easy2buy.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

class ProductAdapter extends RecyclerView.Adapter <ProductAdapter.Myview> {
    UserDetails_AdminEdit userDetails_adminEdit; ArrayList<AddProduct> product;
    public ProductAdapter(UserDetails_AdminEdit userDetails_adminEdit, ArrayList<AddProduct> product) {
        this.userDetails_adminEdit=userDetails_adminEdit;
        this.product=product;
    }

    @Override
    public Myview onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(userDetails_adminEdit).inflate(R.layout.child_product,parent,false);
     Myview m=new Myview(v);
        return  m;
    }

    @Override
    public void onBindViewHolder(Myview holder, final int position) {
        try {
            holder.name.setText(product.get(position).getProductName());
        } catch (Exception e) {
        }
        try { holder.price.setText(product.get(position).getProductPrice());
        } catch (Exception e) {
        }
        try {     Picasso.with(userDetails_adminEdit).load(product.get(position).getProductImage()).into(holder.dp);
        } catch (Exception e) {
        }
        holder.c_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(userDetails_adminEdit,ProductViewActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)product);
                args.putInt("pos",position);
                i.putExtra("BUNDLE",args);
                userDetails_adminEdit.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class Myview extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView dp;
        CardView c_pro;
        public Myview(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price_pro);
            name = itemView.findViewById(R.id.name_pro);
            dp = itemView.findViewById(R.id.dp_pro);
            c_pro=itemView.findViewById(R.id.c_pro);

        }
    }
}
