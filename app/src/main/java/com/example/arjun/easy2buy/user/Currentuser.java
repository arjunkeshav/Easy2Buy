package com.example.arjun.easy2buy.user;

import android.graphics.Bitmap;
import android.widget.ImageView;



import com.example.arjun.easy2buy.PrefManager;
import com.google.android.gms.common.api.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Currentuser {
    private void queryForCurrentUser(final String id) {
        ValueEventListener v=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                if(data.exists())
                {
                    for(DataSnapshot s:data.getChildren())
                    {

                        UserRegistration u = s.getValue(UserRegistration.class);


//                        znav_user.setText(u.getName());
//                        nav_id.setText(u.getMobile());
                        String url=u.getImage();
                        try{
                            new PrefManager(Home.this).set_designation_pref(u.getDesignation());}catch (Exception e){}

                        RequestQueue r= Volley.newRequestQueue(Home.this);
                        ImageRequest ss=new ImageRequest(url, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
//                                nav_dp.setImageBitmap(response);
                            }
                        }, 0, 0, ImageView.ScaleType.CENTER_CROP, // Image scale type
                                Bitmap.Config.RGB_565, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.fillInStackTrace();
                            }
                        });

                        r.add(ss);

//                        showManagerModule();

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Query q= FirebaseDatabase.getInstance().getReference(UserRegistrionTofdb.TABLENAME)
                .orderByChild("id").equalTo(id);

        q.addListenerForSingleValueEvent(v);

    }
}
