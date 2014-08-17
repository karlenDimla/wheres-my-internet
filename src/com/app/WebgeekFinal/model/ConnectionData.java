package com.app.WebgeekFinal.model;

import android.location.Location;
import android.util.Log;
import org.json.JSONObject;

import java.sql.Connection;

/**
 * Created by kdimla on 8/16/14.
 */
public class ConnectionData {
    public Boolean getmHasPassword() {
        return mHasPassword;
    }

    public void setmHasPassword(Boolean mHasPassword) {
        this.mHasPassword = mHasPassword;
    }

    private Boolean mHasPassword;

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    private double mLat;

    public double getmLong() {
        return mLong;
    }

    public void setmLong(double mLong) {
        this.mLong = mLong;
    }

    private double mLong;

    private int mSignalStrength;
    private String mUuid;

    public String getmSsid() {
        return mSsid;
    }

    public void setmSsid(String mSsid) {
        this.mSsid = mSsid;
    }

    private String mSsid;

    public String getmIp() {
        return mIp;
    }

    public void setmIp(String mIp) {
        this.mIp = mIp;
    }

    private String mIp;

    public ConnectionData(double lat, double lng, int strength, String id, String ssid, String ip){
        this.mLat = lat;
        this.mLong = lng;
        this.mSignalStrength = strength;
        this.mUuid = id;
        this.mSsid = ssid;
        this.mIp = ip;
    }

    public ConnectionData(JSONObject obj){
        this.mLat = Double.parseDouble(obj.optString("lat"));
        this.mLong = Double.parseDouble(obj.optString("long"));
        this.mSignalStrength = Integer.parseInt(obj.optString("strength"));
        this.mUuid = obj.optString("uuid");
        this.mSsid = obj.optString("ssid");
        this.mIp = obj.optString("ip");
        this.mHasPassword = obj.optBoolean("haspassword");
    }

    public int getmSignalStrength() {
        return mSignalStrength;
    }

    public void setmSignalStrength(int mSignalStrength) {
        this.mSignalStrength = mSignalStrength;
    }
    public String getmUuid() {
        return mUuid;
    }

    public void setmUuid(String mUuid) {
        this.mUuid = mUuid;
    }

    public void print(){
        Log.i("kl", "PRIIIIIIIIINT!!!");
        Log.i("kl", "is connected wifi.  Strength :" + mSignalStrength);
        Log.i("kl", "LOCATION: "+ mLat);
        Log.i("kl", "LOCATION: "+ mLong);
        Log.i("kl", "Uuid: "+ mUuid);
        Log.i("kl", "Ssid: "+ mSsid);
        Log.i("kl", "IP: "+ mIp);
    }
}
