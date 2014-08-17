package com.app.WebgeekFinal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import com.app.WebgeekFinal.R;

/**
 * Created by kdimla on 8/17/14.
 */
public class SplashAcivity extends Activity {

    private static final long MAXIMUM_SPLASH_TIME = 3000;
    public static SplashAcivity mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mInstance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        startTermination();
    }

    private void startTermination() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                terminateNow();
            }
        }, MAXIMUM_SPLASH_TIME);

    }

    private void terminateNow() {
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
        finish();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}
