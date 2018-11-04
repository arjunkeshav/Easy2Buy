package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import fragment.Nearby;
import fragment.Popular;
import fragment.Recommended;
import fragment.TopReview;



public class TabUsersearchAdapter extends FragmentStatePagerAdapter {
    int numoftabs;
    private ArrayList<String> productUri;
    private ArrayList<String> productName;
    private ArrayList<String> productPrice;
    private ArrayList<String> productDist;
    private ArrayList<String> productVendor;
    private ArrayList<String> productOffer;
    private ArrayList<String> productId;
    private String userid;


    public TabUsersearchAdapter(FragmentManager fm, int  mnumoftabs,ArrayList<String> productUri, ArrayList<String> productName,ArrayList<String> productPrice,ArrayList<String> productDist,ArrayList<String> productVendor,ArrayList<String> productOffer,ArrayList<String> productId,String userid) {
        super(fm);
        this.numoftabs = mnumoftabs;
        this.productUri = productUri;
        this.productName = productName;
        this.productPrice=productPrice;
        this.productDist=productDist;
        this.productVendor =productVendor;
        this.productOffer =productOffer;
        this.productId = productId;
        this.userid = userid;




    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Nearby tab1 = new Nearby(productUri,productName,productPrice,productDist,productVendor,productOffer,productId,userid);
                return tab1;

            case 1:
                Popular tab2 = new Popular(productUri,productName,productPrice,productDist,productVendor,productOffer,productId,userid);
                return tab2;

//            case 2:
//                TopReview tab3 = new TopReview();
//                return tab3;
//
//            case 3:
//                Recommended tab4 = new Recommended();
//                return tab4;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
            return numoftabs;
    }
}
