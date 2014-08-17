package com.app.WebgeekFinal.utility;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by kdimla on 8/16/14.
 */
public class UuidUtility {
    public static String getUuid(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
