package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.arjun.easy2buy.R;

import java.util.List;

import model.UsersearchModel;

/**
 * Created by wolfsoft3 on 16/7/18.
 */

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
        UsersearchModel modelfoodrecycler = usersearchModelList.get(position);

        holder.nearbytext1.setText(modelfoodrecycler.getNearbytext1());
        holder.nearbytext2.setText(modelfoodrecycler.getNearbytext2());
        holder.nearbytext3.setText(modelfoodrecycler.getNearbytext3());

        holder.nearbyimg1.setImageResource(modelfoodrecycler.getNearbyimg1());
        holder.nearbyimg2.setImageResource(modelfoodrecycler.getNearbyimg2());



    }

    @Override
    public int getItemCount() {
        return usersearchModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView nearbyimg1,nearbyimg2;
        TextView nearbytext1,nearbytext2,nearbytext3;


        public MyViewHolder(View itemView) {
            super(itemView);
            nearbyimg1 = itemView.findViewById(R.id.nearbyimg1);
            nearbyimg2 = itemView.findViewById(R.id.nearbyimg2);

            nearbytext1 = itemView.findViewById(R.id.nearbytext1);
            nearbytext2 = itemView.findViewById(R.id.nearbytext2);
            nearbytext3 = itemView.findViewById(R.id.nearbytext3);

        }
    }
    public UsersearchAdapter(Context mainActivityContacts, List<UsersearchModel> offerList) {
        this.usersearchModelList = offerList;
        this.context = mainActivityContacts;
    }
}
