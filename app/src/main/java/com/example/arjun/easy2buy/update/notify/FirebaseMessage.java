package com.example.arjun.easy2buy.update.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.arjun.easy2buy.R;
import com.example.arjun.easy2buy.user.ProductDetailsActivity;
import com.example.arjun.easy2buy.user.UserHomeActivity;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;

public class FirebaseMessage extends FirebaseMessagingService {

    JSONObject notiData;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> params = remoteMessage.getData();
        notiData = new JSONObject(params);

        Log.e("-> ", "onMessageReceived: " + notiData.toString());

        try { Log.e("-> ", "onMessageReceived:image url " + remoteMessage.getData().get("image-url"));
            Log.e("-> ", "onMessageReceived:title " + remoteMessage.getData().get("title"));
            Log.e("-> ", "onMessageReceived:click " + notiData.getString("uid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String NOTIFICATION_CHANNEL_ID = "com.fisat.nyzam.zigma";

//        long pattern[] = {0, 1000, 500, 1000};
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, remoteMessage.getNotification().getTitle(),
//                    NotificationManager.IMPORTANCE_HIGH);
//            notificationChannel.setDescription(remoteMessage.getNotification().getBody());
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.setVibrationPattern(pattern);
//            notificationChannel.enableVibration(true);
//            if (mNotificationManager != null) {
//                mNotificationManager.createNotificationChannel(notificationChannel);
//            }
//        }
//
//        // to diaplay notification in DND Mode
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = null;
//            if (mNotificationManager != null) {
//                channel = mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID);
//            }
//            if (channel != null) {
//                channel.canBypassDnd();
//            }
//        }
//
//        // Create an Intent for the activity you want to start
//        Intent resultIntent = new Intent(this, UserHomeActivity.class);// FIXME: 29-10-2018
//        resultIntent.putExtra("noti_data", notiData.toString());
//        // Create the TaskStackBuilder and add the intent, which inflates the back stack
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(resultIntent);
//
//        // Get the PendingIntent containing the entire back stack
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
//        builder.setAutoCancel(true)
//                .setSmallIcon(R.drawable.ic_check)// icon
//                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
//                .setContentTitle(remoteMessage.getNotification().getTitle())
//                .setContentText(remoteMessage.getNotification().getBody())
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setAutoCancel(true);
//
//
//
//        RequestQueue r = Volley.newRequestQueue(this);
//        ImageRequest ss = new ImageRequest(remoteMessage.getData().get("image-url"), new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                builder.setLargeIcon(response)
//                        .setStyle(new NotificationCompat.BigPictureStyle()
//                                //   .setSummaryText(remoteMessage.getData().get("message"))
//                                .bigPicture(response));
//            }
//        }, 0, 0, ImageView.ScaleType.CENTER_CROP, // Image scale type
//                Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.fillInStackTrace();
//            }
//        });
//
//        r.add(ss);
//
//
//
//
//
//
//        builder.setContentIntent(resultPendingIntent);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(1000, builder.build());

        picjob(remoteMessage);
    }


        /*Log.e("TAG", "onMessageReceived: " );
        if (remoteMessage.getData().size() > 0) {
            Log.d("fcm_simi", "Message data payload: " + remoteMessage.getData());
            NotificationManager  notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notifications)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true);
            notificationManager.notify(1 *//* ID of notification *//*, notificationBuilder.build());
             picjob(remoteMessage);
        }*/

    private void picjob(final RemoteMessage remoteMessage) {

        Intent notificationIntent;
//        if(StartActivity.isAppRunning){
//            notificationIntent = new Intent(this, ChildActivity.class);
//        }else{
//            notificationIntent = new Intent(this, StartActivity.class);
//        }
        String val ="LPMdg4GHl6DAjIteWo1";
        notificationIntent = new Intent(this, ProductDetailsActivity.class);
        notificationIntent.putExtra("uid",remoteMessage.getData().get("uid"));

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        final Bitmap bitmap = getBitmapfromUrl(remoteMessage.getData().get("image-url")); //obtain the image
        long pattern[] = {0, 1000, 500, 1000};
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_check)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("message"))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setLargeIcon(bitmap)
                .setWhen(System.currentTimeMillis())
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        //   .setSummaryText(remoteMessage.getData().get("message"))
                        .bigPicture(bitmap))
                .setContentIntent(pendingIntent);

//        RequestQueue r = Volley.newRequestQueue(this);
//        ImageRequest ss = new ImageRequest(remoteMessage.getData().get("image-url"), new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                notificationBuilder.setLargeIcon(response)
//                        .setStyle(new NotificationCompat.BigPictureStyle()
//                             //   .setSummaryText(remoteMessage.getData().get("message"))
//                                .bigPicture(response));
//            }
//        }, 0, 0, ImageView.ScaleType.CENTER_CROP, // Image scale type
//                Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.fillInStackTrace();
//            }
//        });
//
//        r.add(ss);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Setting up Notification channels for android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence adminChannelName = "arjun";
            String adminChannelDescription = "notify";

            NotificationChannel adminChannel;
            adminChannel = new NotificationChannel("1", adminChannelName, NotificationManager.IMPORTANCE_LOW);
            adminChannel.setDescription(adminChannelDescription);
            adminChannel.enableLights(true);
            adminChannel.setLightColor(Color.RED);
            adminChannel.enableVibration(true);
            adminChannel.setVibrationPattern(pattern);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(adminChannel);
            }
        }
        int notificationId = new Random().nextInt(60000);


        if (notificationManager != null) {
            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
        }

    }

    //Simple method for image downloading
    public Bitmap getBitmapfromUrl(String imageUrl) {

        try {

            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
