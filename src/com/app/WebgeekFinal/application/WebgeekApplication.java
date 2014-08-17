package com.app.WebgeekFinal.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;
import com.app.WebgeekFinal.callback.DataCallback;
import com.app.WebgeekFinal.callback.VolleyListener;
import com.app.WebgeekFinal.font.Fonts;
import com.app.WebgeekFinal.helper.VolleyHelper;
import com.app.WebgeekFinal.model.ConnectionData;
import com.app.WebgeekFinal.network.error.CustomVolleyError;
import com.app.WebgeekFinal.utility.ConnectionUtility;
import com.app.WebgeekFinal.utility.LocationUtility;
import com.app.WebgeekFinal.utility.NetworkUtility;
import com.app.WebgeekFinal.utility.UuidUtility;

import java.util.ArrayList;

/**
 * Created by kdimla on 8/16/14.
 */
public class WebgeekApplication extends Application implements VolleyListener {
    private static Context sContext;
    private ConnectionData mConnectionData;
    private VolleyHelper mVolleyHelper;
    private String mIpAddress;
    private DataCallback mDataCallback;

    public void setmConnectionData(ConnectionData connectionData){
        this.mConnectionData = connectionData;
    }

    public ConnectionData getConnectionData(){
        return this.mConnectionData;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        mVolleyHelper = VolleyHelper.getInstance(sContext);
        initializeFonts();
        if(LocationUtility.isLocationEnabled(sContext)){
            checkConnection();
        }
    }

    public void checkConnection(){
        ConnectionUtility connectionUtility = ConnectionUtility.getInstance(sContext);
        if(NetworkUtility.isConnectedToNetwork(sContext)){
            if(ConnectionUtility.isConnectedWifi(sContext)){
                int strength = connectionUtility.getWifiStrength();
                Location location = LocationUtility.getLocation(sContext);
                String uuid = UuidUtility.getUuid(sContext);
                String ssid = ConnectionUtility.getSsid(sContext);
                Boolean hasPassword = ConnectionUtility.hasPassword(ConnectionUtility.getConfig(ssid));
                mConnectionData = new ConnectionData(location.getLatitude(), location.getLongitude(), strength, uuid, ssid, "");
                mConnectionData.setmHasPassword(hasPassword);
                getIpAddress();
            }
        }
    }

    private void getIpAddress(){
        mVolleyHelper.getIpAddress(this);
    }

    public void getConnectionDataList(DataCallback callback){
        mDataCallback = callback;
        mVolleyHelper.getListData(this);
    }

    @Override
    public void onResponse(Object response) {
        if(response != null){
            if(response instanceof  String){
                mIpAddress = (String)response;
                Log.i("kl", "Ip: "+ mIpAddress);
                mConnectionData.setmIp(mIpAddress);
                mConnectionData.print();
                VolleyHelper.getInstance(sContext).postListData(this,mConnectionData,this);
            }else if(response instanceof ArrayList){
                if(mDataCallback != null){
                    mDataCallback.onSuccessGet(response);
                    mDataCallback = null;
                }
            }
        }

    }

    @Override
    public void onErrorResponse(CustomVolleyError customVolleyError) {
        if(customVolleyError.getmErrorMessage() != null){
            Toast.makeText(sContext, "error in ip addr: "+customVolleyError.getmErrorMessage(), Toast.LENGTH_SHORT).show();
        }
        if(mDataCallback != null){
            mDataCallback.onErrorGet();
            mDataCallback = null;
        }
    }

    private void initializeFonts() {
        Fonts.RALEWEY_THIN = Typeface.createFromAsset(getAssets(), Fonts.PATH_RALEWEY_THIN);
        Fonts.MONTSERRAT_REGULAR = Typeface.createFromAsset(getAssets(), Fonts.PATH_MONTSERRAT_REGULAR);
    }

}
