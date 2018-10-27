package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.user.ProductDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.UsersearchModel;



public class UsersearchAdapter extends RecyclerView.Adapter<UsersearchAdapter.MyViewHolder> {
    Context context;
    private List<UsersearchModel> usersearchModelList;


    @Override
    public UsersearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemnearby, parent, false);

        return new UsersearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersearchAdapter.MyViewHolder holder, int position) {
        final UsersearchModel modelrecycler = usersearchModelList.get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("uid",modelrecycler.getProductId());
                intent.putExtra("distance",modelrecycler.getNearbytext3());
                context.startActivity(intent);
            }
        });

        holder.nearbytext1.setText(modelrecycler.getNearbytext1());
        holder.nearbytext2.setText(modelrecycler.getNearbytext2());
        holder.nearbytext3.setText(modelrecycler.getNearbytext3());
        holder.nearbytext4.setText(modelrecycler.getNearbytext4());
        //holder.Picasso.with(this).load(modelrecycler.getNearbyimg1()).into(nearbyimg1);
        Picasso.with(context).load(modelrecycler.getNearbyimg1()).into(holder.nearbyimg1);

        //holder.nearbyimg1.setImageResource(modelrecycler.getNearbyimg1());
        holder.nearbyimg2.setText(modelrecycler.getNearbyimg2());



    }

    @Override
    public int getItemCount() {
        return usersearchModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView nearbyimg1;
        TextView nearbytext1,nearbytext2,nearbytext3,nearbytext4,nearbyimg2;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);
            nearbyimg1 = itemView.findViewById(R.id.nearbyimg1);
            nearbyimg2 = itemView.findViewById(R.id.nearbyimg2);
            cardView =  itemView.findViewById(R.id.cardItem);

            nearbytext1 = itemView.findViewById(R.id.nearbytext1);
            nearbytext2 = itemView.findViewById(R.id.nearbytext2);
            nearbytext3 = itemView.findViewById(R.id.nearbydist);
            nearbytext4 = itemView.findViewById(R.id.nearbytext4);

        }
    }
    public UsersearchAdapter(Context mainActivityContacts, List<UsersearchModel> offerList) {
        this.usersearchModelList = offerList;
        this.context = mainActivityContacts;
    }
}
