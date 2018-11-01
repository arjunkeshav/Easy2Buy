package adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.newadmin.ProductViewActivity;
import com.example.arjun.easy2buy.vendor.AddproductActivity;
import com.example.arjun.easy2buy.vendor.UpdateProduct;
import com.example.arjun.easy2buy.vendor.VendorDashboardActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class ProductAdapt extends RecyclerView.Adapter <ProductAdapt.Myview> {

    VendorDashboardActivity vendorDashboardActivity;
    ArrayList<String> productImage = new ArrayList<>();
    ArrayList<String> productName = new ArrayList<>();
    ArrayList<String> productPrice = new ArrayList<>();
    ArrayList<String> productId = new ArrayList<>();







    public ProductAdapt(VendorDashboardActivity vendorDashboardActivity, ArrayList<String> productImage, ArrayList<String> productName, ArrayList<String> productPrice, ArrayList<String> productId) {

        this.vendorDashboardActivity=vendorDashboardActivity;
        this.productImage=productImage;
        this.productName=productName;
        this.productPrice=productPrice;
        this.productId=productId;


    }


    @NonNull
    @Override
    public ProductAdapt.Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(vendorDashboardActivity).inflate(R.layout.child_product,parent,false);
        Log.e("length", String.valueOf(productId.size()));
        return new ProductAdapt.Myview(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapt.Myview holder, final int position) {

            try { Log.e("position",productName.get(position));
                holder.name.setText(productName.get(position));
            } catch (Exception e) {
            }
            try {
                holder.price.setText(productPrice.get(position));
            } catch (Exception e) {
            }
            try {
                Picasso.with(vendorDashboardActivity).load(productImage.get(position)).into(holder.dp);
            } catch (Exception e) {
            }
            holder.c_pro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(vendorDashboardActivity, UpdateProduct.class);

                    i.putExtra("uid", productId.get(position));
                    vendorDashboardActivity.startActivity(i);


                }
            });

    }

    @Override
    public int getItemCount() {
        return productId.size();
    }

    public class  Myview extends RecyclerView.ViewHolder {
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
