package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.arjun.easy2buy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.UsersearchModel;

/**
 * Created by wolfsoft3 on 16/7/18.
 */

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {
    Context context;
    private List<UsersearchModel> usersearchModelList;


    @Override
    public PopularAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itempopular, parent, false);

        return new PopularAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularAdapter.MyViewHolder holder, int position) {
        UsersearchModel modelfoodrecycler = usersearchModelList.get(position);

        holder.nearbytext1.setText(modelfoodrecycler.getNearbytext1());
        holder.nearbytext2.setText(modelfoodrecycler.getNearbytext2());
        holder.nearbytext3.setText(modelfoodrecycler.getNearbytext3());
        holder.nearbytext4.setText(modelfoodrecycler.getNearbytext4());
        Picasso.with(context).load(modelfoodrecycler.getNearbyimg1()).into(holder.nearbyimg1);


       //holder.nearbyimg1.setImageResource(modelfoodrecycler.getNearbyimg1());
        holder.nearbyimg2.setText(modelfoodrecycler.getNearbyimg2());



    }

    @Override
    public int getItemCount() {
        return usersearchModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView nearbyimg1;
        TextView nearbytext1,nearbytext2,nearbytext3,nearbytext4,nearbyimg2;


        public MyViewHolder(View itemView) {
            super(itemView);
            nearbyimg1 = itemView.findViewById(R.id.nearbyimg1);
            nearbyimg2 = itemView.findViewById(R.id.nearbyimg2);

            nearbytext1 = itemView.findViewById(R.id.nearbytext1);
            nearbytext2 = itemView.findViewById(R.id.nearbytext2);
            nearbytext3 = itemView.findViewById(R.id.nearbydist);
            nearbytext4 = itemView.findViewById(R.id.nearbytext4);

        }
    }
    public PopularAdapter(Context mainActivityContacts, List<UsersearchModel> offerList) {
        this.usersearchModelList = offerList;
        this.context = mainActivityContacts;
    }
}
