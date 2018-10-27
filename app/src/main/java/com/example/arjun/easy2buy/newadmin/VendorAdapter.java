package com.example.arjun.easy2buy.newadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arjun.easy2buy.R;

import java.io.Serializable;
import java.util.ArrayList;

class VendorAdapter extends RecyclerView.Adapter <VendorAdapter.Myview> {
    AdminActivity mainActivity; ArrayList<UserModel> usersArray;
    public VendorAdapter(AdminActivity mainActivity, ArrayList<UserModel> usersArray) {
        this.mainActivity=mainActivity;
        this.usersArray=usersArray;
    }


    @Override
    public Myview onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mainActivity).inflate(R.layout.child_vendor,parent,false);
        Myview m=new Myview(v);

        return m;
    }

    @Override
    public void onBindViewHolder(Myview holder, final int position) {

        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(mainActivity,UserDetails_AdminEdit.class);
               ArrayList<UserModel>a=new ArrayList<UserModel>();
//                i.putExtra("list",a.add(usersArray.get(position)));
                a.add(usersArray.get(position));
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)a);
                i.putExtra("BUNDLE",args);
                mainActivity.startActivity(i);
            }
        });
        holder.email.setText(usersArray.get(position).getEmail());
        holder.id.setText(usersArray.get(position).getUserId());
        holder.type.setText(usersArray.get(position).getUserType());
    }

    @Override
    public int getItemCount() {
        return usersArray.size();
    }

    public class Myview extends RecyclerView.ViewHolder {
        TextView email,type,id;CardView c;
        public Myview(View itemView) {
            super(itemView);
            email=itemView.findViewById(R.id.email);
            type=itemView.findViewById(R.id.usertype);
            id=itemView.findViewById(R.id.userid);
            c=itemView.findViewById(R.id.card);
        }
    }
}
