package com.app.WebgeekFinal.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import com.app.WebgeekFinal.R;

/**
 * Created by kdimla on 8/17/14.
 */
public class CustomLoadingDialog extends Dialog{
    public CustomLoadingDialog(Context context) {
        super(context, android.R.style.Theme_Black_NoTitleBar);
    }

    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.data_view);
    }
}
