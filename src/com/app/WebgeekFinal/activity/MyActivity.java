package com.app.WebgeekFinal.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.Toast;
import com.app.WebgeekFinal.R;
import com.app.WebgeekFinal.application.WebgeekApplication;
import com.app.WebgeekFinal.callback.DataCallback;
import com.app.WebgeekFinal.model.ConnectionData;
import com.app.WebgeekFinal.utility.*;
import com.app.WebgeekFinal.view.CustomData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.*;
import org.json.JSONException;

import java.util.ArrayList;

public class MyActivity extends Activity implements View.OnClickListener, DataCallback, GoogleMap.OnMarkerClickListener{

    private WebgeekApplication mApplication;
    private GoogleMap mGoogleMap;
    private ArrayList<Marker> markerList;
    private ArrayList<ConnectionData> connectionDataList;
    private ProgressDialog dialog;
    private static final String SENDERNUMBER="21588718";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initializeViews();
        initializeActionBar();

        if(!LocationUtility.isLocationEnabled(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enable Location for better experience.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MyActivity.this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.i("kl","is on resume");
        if(getIntent().getStringExtra("SMSBODY") != null){
            String data = getIntent().getStringExtra("SMSBODY");
            try{
                connectionDataList = ParserUtility.parseList(data);
                processData();
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        if(!NetworkUtility.isConnectedToNetwork(this) || !ConnectionUtility.isConnectedWifi(this)){
            SmsUtility.sendSMS(this, this, SENDERNUMBER, "GET ALL");
            Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }else{
            mApplication.getConnectionDataList(this);
        }
    }
    private void renderMap(){
        try {
            if (mGoogleMap == null) {
                mGoogleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            Location currLoc = LocationUtility.getLocation(this);
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMarkerClickListener(this);

            CameraPosition camPos = new CameraPosition.Builder()
                    .target(new LatLng(currLoc.getLatitude(), currLoc.getLongitude()))
                    .zoom(
                            mGoogleMap.getMaxZoomLevel())
                    .bearing(currLoc.getBearing())
                    .tilt(70)
                    .build();


            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camPos));
            mGoogleMap.addCircle(new CircleOptions());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initializeViews(){
        mApplication = (WebgeekApplication) getApplication();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Obtaining data from the server...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        switch(id){
            case R.id.actionbar_menu_refresh:
                dialog.show();
                mApplication.getConnectionDataList(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessGet(Object object){
        if(object != null && markerList == null){
            connectionDataList = (ArrayList<ConnectionData>)object;
            processData();

        }
        dialog.dismiss();

    }

    @Override
    public void onErrorGet(){
        dialog.dismiss();
        Toast.makeText(this, "Something went wrong!!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        connectionDataList.get(markerList.indexOf(marker)).print();
        CustomData dataDialog = new CustomData(this, connectionDataList.get(markerList.indexOf(marker)));
        dataDialog.show();
        return true;
    }
    private void processData(){
        markerList = new ArrayList<Marker>();
        Log.i("kl", "SIZE: " + connectionDataList.size());

        renderMap();
        for(int cnt = 0; cnt < connectionDataList.size(); cnt++){
            MarkerOptions options = new MarkerOptions().
                    position(new LatLng(connectionDataList.get(cnt).getmLat() , connectionDataList.get(cnt).getmLong())).title(connectionDataList.get(cnt).getmSsid());
            if(connectionDataList.get(cnt).getmHasPassword()){
                options= options.icon(BitmapDescriptorFactory.fromResource(R.drawable.red_marker));
            }else{
                options = options.icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker));
            }
            Marker TP = mGoogleMap.addMarker(options);
            markerList.add(TP);
        }
    }

    private void initializeActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Where's my Internet?");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.actionbar, null);

        ImageView mRefreshView = (ImageView) v.findViewById(R.id.actionbar_menu_refresh);
        mRefreshView.setOnClickListener(this);
        actionBar.setCustomView(v);
    }
}

