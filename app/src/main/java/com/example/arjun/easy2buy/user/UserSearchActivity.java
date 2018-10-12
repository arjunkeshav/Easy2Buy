package com.example.arjun.easy2buy.user;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;



import java.util.ArrayList;

import adapter.UsersearchAdapter;
import adapter.TabUsersearchAdapter;
import model.UsersearchModel;

import com.example.arjun.easy2buy.R;

public class UserSearchActivity extends AppCompatActivity {
    ViewPager viewPager1;
    TabLayout tabLayout1;
    TabUsersearchAdapter adapter;


    private UsersearchAdapter usersearchAdapter;
    private RecyclerView recyclerview;
    private ArrayList<UsersearchModel> usersearchModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        viewPager1 = findViewById(R.id.viewpager1);

        tabLayout1 = findViewById(R.id.tablayout1);

        tabLayout1.addTab(tabLayout1.newTab().setText("Nearby"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Popular"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Top review"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Recommended"));

        adapter = new TabUsersearchAdapter(getSupportFragmentManager(),tabLayout1.getTabCount());
        viewPager1.setAdapter(adapter);
        viewPager1.setOffscreenPageLimit(4);
        viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));
        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager1.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}





