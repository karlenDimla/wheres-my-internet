package com.app.WebgeekFinal.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

/**
 * Created by kdimla on 8/16/14.
 */
public class ConnectionUtility {
    private static  Context mContext;
    private static ConnectionUtility mInstance;

    public static ConnectionUtility getInstance(Context context){
       if(mInstance == null){
           mInstance = new ConnectionUtility(context);
       }
       return mInstance;
    }

    private ConnectionUtility(Context context){
        this.mContext = context;
    }

    public int getWifiStrength(){
        int strength = 0;
        WifiManager wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        strength = wifiManager.getConnectionInfo().getLinkSpeed();
        return strength;
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static String getSsid(Context context){
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.getConnectionInfo().getSSID();
        return wifiManager.getConnectionInfo().getSSID();
    }

    public static String getIp(Context context){
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);
        return ipAddress;
    }

    public static boolean hasPassword(WifiConfiguration wifiConfig) {
        if(wifiConfig == null){
            return true;
        }
        return !TextUtils.isEmpty(wifiConfig.preSharedKey)
                || !TextUtils.isEmpty(wifiConfig.wepKeys[0])
                || !TextUtils.isEmpty(wifiConfig.wepKeys[1])
                || !TextUtils.isEmpty(wifiConfig.wepKeys[2])
                || !TextUtils.isEmpty(wifiConfig.wepKeys[3]);
    }

    public static WifiConfiguration getConfig(String ssid) {
        WifiManager wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wifiManager.getConnectionInfo();
        if( wi != null )
        {
            WifiConfiguration activeConfig = null;
            for( WifiConfiguration conn : wifiManager.getConfiguredNetworks() )
            {
                if( conn.SSID.equals(ssid))
                {
                    activeConfig = conn;
                    break;
                }
            }
            return activeConfig;
        }
        return null;
    }

}
