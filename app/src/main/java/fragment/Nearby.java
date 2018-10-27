package fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.arjun.easy2buy.R;

import java.util.ArrayList;

import adapter.UsersearchAdapter;
import model.UsersearchModel;


@SuppressLint("ValidFragment")
public class Nearby extends Fragment {


    //Integer[] nearbyimg1 = {R.drawable.foodimg4,R.drawable.img2,R.drawable.foodimg3,R.drawable.img1};
    //Integer[] nearbyimg2 = {R.drawable.ic_like,R.drawable.ic_like,R.drawable.ic_like,R.drawable.ic_like};
    ArrayList<String> productUri = new ArrayList<>();
    ArrayList<String> productName = new ArrayList<>();
    ArrayList<String> productPrice = new ArrayList<>();
    ArrayList<String> productDist = new ArrayList<>();
    ArrayList<String> productVendor = new ArrayList<>();
    ArrayList<String> productOffer = new ArrayList<>();
    ArrayList<String> productId = new ArrayList<>();

    public Nearby() {
    }

    @SuppressLint("ValidFragment")
    public Nearby(ArrayList<String> productUri,ArrayList<String> productName,ArrayList<String> productPrice,ArrayList<String> productDist,ArrayList<String> productVendor,ArrayList<String> productOffer,ArrayList<String> productId) {
        this.productUri =productUri;
        this.productName = productName;
        this.productPrice=productPrice;
        this.productDist=productDist;
        this.productVendor =productVendor;
        this.productOffer=productOffer;
        this.productId =productId;

    }

    //String[] nearbytext1 = {"Cocobolo Poolside \nBar + Grill","Wild Honey at Scotts Square","Palm Beach Seafood Restaurant","Shin Minori Japanese Restaurant"};
   // String[] nearbytext2 = {"60 Kub Pines Apt. 797","473 Keeling Station","55 Dicki Point Suite 867","833 Kuhn Mission Suite 860"};
   // String[] nearbytext3 = {"238 reviews","238 reviews","238 reviews","238 reviews"};


    private UsersearchAdapter usersearchAdapter;
    private RecyclerView recyclerview;
    private ArrayList<UsersearchModel> usersearchModelList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nearbyfragment,container,false);


        recyclerview = view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( getContext(),2);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        usersearchModelList = new ArrayList<>();


        for (int i = 0; i < productName.size(); i++) {
            UsersearchModel view1 = new UsersearchModel(productUri.get(i),productName.get(i),productPrice.get(i),productDist.get(i),productVendor.get(i),productOffer.get(i),productId.get(i));
            usersearchModelList.add(view1);
        }
        usersearchAdapter = new UsersearchAdapter(getActivity(),usersearchModelList);
        recyclerview.setAdapter(usersearchAdapter);

        return view;
    }
}
