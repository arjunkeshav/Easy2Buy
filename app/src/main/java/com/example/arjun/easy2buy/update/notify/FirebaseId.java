package com.example.arjun.easy2buy.update.notify;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseId extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();


        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("fcm", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

         refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message_fcm");
        String key= FirebaseAuth.getInstance().getCurrentUser().getUid();
//        //user id Firebaseuser
//        myRef.child(key).setValue(new NotificationTokenModel(key,refreshedToken));
        FcmTokenUpload n=new FcmTokenUpload(key,refreshedToken,FirebaseAuth.getInstance().getCurrentUser().getEmail());
        myRef.child(key).setValue(n);
    }

}
