package com.example.arjun.easy2buy.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import com.example.arjun.easy2buy.PrefManager;
import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.login.SignInActivity;
import com.example.arjun.easy2buy.map.MapsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;



public class UserDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    BottomBar bottomBar;
    TextView textView;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        bottomBar = findViewById(R.id.bottomBar);


        for (int i = 0; i < bottomBar.getTabCount(); i++) {
            bottomBar.getTabAtPosition(i).setGravity(Gravity.CENTER_VERTICAL);
        }
        editText =findViewById(R.id.editTextSearch);

        textView = findViewById(R.id.txtSearch);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboardActivity.this,UserSearchActivity.class);
                startActivity(intent);
            }
        });





        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            {
             super.onBackPressed();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the UserDashboardActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        if(id==R.id.action_exit)
        {
          new PrefManager(this).logout();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
          Intent intent = new Intent(UserDashboardActivity.this,SignInActivity.class);
          startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.8
        int id = item.getItemId();

        if (id == R.id.nav_map)
        {

            Intent i=new Intent(this,MapsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_firebase)
        {

//            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("product");
//
//// Creating new user node, which returns the unique key value
//// new user node would be /users/$userid/
//            String userId = mDatabase.push().getKey();
//            String name = "Arjun";
//            String address= "Thazhathetharayil";
//
//// creating user object
//            User user = new User(name,address,userId);
//
//// pushing user to 'users' node using the userId
//            mDatabase.child(Objects.requireNonNull(userId)).setValue(user);


        }
        else if (id == R.id.nav_upload)
        {
            Intent i=new Intent(this,UserProfileActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_manage)
        {

        }
        else if (id == R.id.nav_share)
        {

        }
        else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
