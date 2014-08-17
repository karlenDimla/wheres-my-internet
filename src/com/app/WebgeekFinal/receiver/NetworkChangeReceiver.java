package com.app.WebgeekFinal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;
import com.app.WebgeekFinal.callback.VolleyListener;
import com.app.WebgeekFinal.helper.VolleyHelper;
import com.app.WebgeekFinal.model.ConnectionData;
import com.app.WebgeekFinal.network.error.CustomVolleyError;
import com.app.WebgeekFinal.utility.*;

/**
 * Created by kdimla on 8/16/14.
 */
public class NetworkChangeReceiver extends BroadcastReceiver implements VolleyListener {
    private ConnectionData mConnectionData;
    private String mIpAddress;
    private Context mContext;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        this.mContext = context;
        checkConnection(context);
    }

    private void checkConnection(Context context){
        ConnectionUtility connectionUtility = ConnectionUtility.getInstance(context);
        if(NetworkUtility.isConnectedToNetwork(context)){
            if(ConnectionUtility.isConnectedWifi(context)){
                int strength = connectionUtility.getWifiStrength();
                Location location = LocationUtility.getLocation(context);
                String uuid = UuidUtility.getUuid(context);
                String ssid = ConnectionUtility.getSsid(context);
                if(!ssid.equals(SharedPrefUtility.getInstance(context).getString("lastssid",""))){
                    Boolean hasPassword = ConnectionUtility.hasPassword(ConnectionUtility.getConfig(ssid));
                    mConnectionData = new ConnectionData(location.getLatitude(), location.getLongitude(),strength, uuid, ssid, "");
                    mConnectionData.setmHasPassword(hasPassword);
                    getIpAddress(context);
                }
            }
        }
    }
    private void getIpAddress(final Context context){
        VolleyHelper.getInstance(context).getIpAddress(this);
    }

    @Override
    public void onResponse(Object response) {
        if(response != null && response instanceof  String){
            mIpAddress = (String)response;
            Log.i("kl", "Ip: "+ mIpAddress);
            mConnectionData.setmIp(mIpAddress);
            mConnectionData.print();
            VolleyHelper.getInstance(mContext).postListData(mContext, mConnectionData,this);
        }
    }

    @Override
    public void onErrorResponse(CustomVolleyError customVolleyError) {
        Toast.makeText(mContext, "ERROR: "+customVolleyError.getmErrorMessage(), Toast.LENGTH_SHORT).show();
    }
}
