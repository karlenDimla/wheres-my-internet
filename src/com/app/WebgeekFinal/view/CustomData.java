package com.app.WebgeekFinal.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import com.app.WebgeekFinal.R;
import com.app.WebgeekFinal.model.ConnectionData;

/**
 * Created by kdimla on 8/17/14.
 */
public class CustomData extends Dialog implements View.OnClickListener{
    private ConnectionData mData;
    private CustomTextView mSsidTextView;
    private CustomTextView mSpeedextView;
    private CustomTextView mIpTextView;
    private CustomTextView mHadPwdTextView;
    private CustomTextView mHadPwdFalseTextView;
    private ImageView mExitBtn;

    public CustomData(Context context, ConnectionData data) {
        super(context, R.style.PauseDialog);
        this.mData = data;
    }

    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.data_view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initializeViews();
        initializeValues();
    }

    protected void initializeViews(){
        mSsidTextView = (CustomTextView) findViewById(R.id.data_view_text_ssid);
        mSpeedextView = (CustomTextView) findViewById(R.id.data_view_text_strength);
        mIpTextView = (CustomTextView) findViewById(R.id.data_view_text_ip);
        mExitBtn = (ImageView) findViewById(R.id.data_view_exit);
        mHadPwdTextView= (CustomTextView)findViewById(R.id.data_view_text_haspwd_true);
        mHadPwdFalseTextView= (CustomTextView)findViewById(R.id.data_view_text_haspwd_false);
    }

    protected void initializeValues(){
        mExitBtn.setOnClickListener(this);
        mSsidTextView.setText("Ssid: " + mData.getmSsid());
        mSpeedextView.setText("Signal Strength: "+mData.getmSignalStrength());
        mIpTextView.setText("IP: "+mData.getmIp());
        if(mData.getmHasPassword()){
            mHadPwdTextView.setVisibility(View.VISIBLE);
            mHadPwdFalseTextView.setVisibility(View.GONE);
        }else{
            mHadPwdFalseTextView.setVisibility(View.VISIBLE);
            mHadPwdTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        switch(id){
            case R.id.data_view_exit:
                this.dismiss();
                break;
        }
    }
}
