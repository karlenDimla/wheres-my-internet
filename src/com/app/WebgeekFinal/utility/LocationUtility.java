package com.app.WebgeekFinal.utility;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by kdimla on 8/16/14.
 */
public class LocationUtility {
    private static String PROVIDER = LocationManager.NETWORK_PROVIDER;

    public static LocationManager getLocationManager(Context context){
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager;
    }
    public static Location getLocation(Context context){
        Log.i("kl", "is enabled: "+isLocationEnabled(context));
        Location location = getLocationManager(context).getLastKnownLocation(PROVIDER);
        return location;
    }

    public static Boolean isLocationEnabled(Context context){
        return getLocationManager(context).isProviderEnabled(PROVIDER);
    }

}
