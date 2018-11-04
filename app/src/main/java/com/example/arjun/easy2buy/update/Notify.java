package com.example.arjun.easy2buy.update;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.update.notify.Data;
import com.example.arjun.easy2buy.update.notify.Example;
import com.example.arjun.easy2buy.update.notify.FcmTokenUpload;
import com.example.arjun.easy2buy.update.notify.Notification;
import com.example.arjun.easy2buy.update.notify.ResultModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class Notify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendRegistrationToServer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                sentToNotification();

            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //bundle must contain all info sent in "data" field of the notification

            //Toast.makeText(Notify.this, bundle.get("title").toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void sendRegistrationToServer() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message_fcm");
        String key = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //user id Firebaseuser
        FcmTokenUpload n = new FcmTokenUpload(key, refreshedToken, FirebaseAuth.getInstance().getCurrentUser().getEmail());
        myRef.child(key).setValue(n);
    }


    public interface ServiceAPI {
        @Headers({
                "Content-Type:application/json",
                "Authorization:key=AAAA9APnps4:APA91bF6KBFMsH8GvLVi3VG-kQf3EiZOjVfkGVva-3waPnNnbaE9YgA-akHVKT98b9zKz2UFlOOwfyj53Ou8KStcFG71kRVulJmfWx9APb6a5ulqhdI8hgpkgZrwKjMB1ahVmPRTsnSj"

        })
        @POST("/fcm/send")
        Call<ResultModel> sendMessage(@Body Example message);
    }

    private void sentToNotification() {

        //token of users
        String to = FirebaseInstanceId.getInstance().getToken();
        String title = " hai";
        String message = "zigma welcomes you";
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/easy2buy-c7541.appspot.com/o/Product%2F29153?alt=media&token=de59429c-2ea9-4c22-8e1f-abb6450dae4a";
        String uid="-LPHqkZ8rMxgh4IZuVD-";
        Data data = new Data(title, message, imageUrl,uid);

        Example e = new Example();
        e.setData(data);
        e.setTo(to);
        Notification notification = new Notification();
        notification.title = "Shop Name";
        notification.click_action = "OPEN_DETAILS";
        notification.body = "New Notification from Shop";
        e.setNotification(notification);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")//url of FCM message server
                .addConverterFactory(GsonConverterFactory.create())//use for convert JSON file into object
                .build();


        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<ResultModel> call = null;
        Log.e("REQUEST ", new Gson().toJson(e));
        call = api.sendMessage(e);
        //
/*        Call<ResultModel> call = api.sendMessage(e);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {


            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });*/

        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                //Log.e("RESP",new Gson().toJson(response.body())  );
                if (response.body().getSuccess() == 1) {
                    Log.e("Response", response.body().toString());

                    Toast.makeText(Notify.this, "message send", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("notify", response.body().toString());
                    Toast.makeText(Notify.this, "message send failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

                t.printStackTrace();
                Log.d("notifyex", t.getMessage());
                Toast.makeText(Notify.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
