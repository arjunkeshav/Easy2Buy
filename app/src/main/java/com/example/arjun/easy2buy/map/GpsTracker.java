package com.example.arjun.easy2buy.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

@SuppressLint("Registered")
public class GpsTracker extends Service implements LocationListener {
    private Context context;
     boolean isGpsEnabled=false;
     boolean isNetworkEnabled= false;


    Location location;
    LocationManager locationManager;




    public GpsTracker(Context context) {
        this.context = context;
    }
    public Location getLocation(){
        try {
            locationManager=(LocationManager) context.getSystemService(LOCATION_SERVICE);
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                    || (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED)){

                if(isGpsEnabled)
                    {
                        if(location==null){
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,10,this );
                            if(locationManager!=null){
                                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            }
                        }
                    }


                    //if location is not find from Gps then we can find it from network


                if(isNetworkEnabled)
                {
                    if(location==null){
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,10,this );
                        if(locationManager!=null){
                            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        }
                    }
                }


            }

        }
        catch (Exception ex) {


        }
        return location;


    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
