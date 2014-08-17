package com.app.WebgeekFinal.utility;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by kdimla on 8/17/14.
 */
public class SmsUtility {
    public static void deleteSMS(Context context, String message, String number) {
        try {
            Uri uriSms = Uri.parse("content://sms/inbox");
            Cursor c = context.getContentResolver().query(
                    uriSms,
                    new String[] { "_id", "thread_id", "address", "person",
                            "date", "body" }, "read=0", null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    long id = c.getLong(0);
                    long threadId = c.getLong(1);
                    String address = c.getString(2);
                    String body = c.getString(5);
                    String date = c.getString(3);

                    if (message.equals(body) && address.equals(number)) {
                        context.getContentResolver().delete(
                                Uri.parse("content://sms/" + id), "date=?",
                                new String[] { c.getString(4) });
                    }
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendSMS(Activity activity, Context context, String number, String message)
    {
        if (number.length()>0 && message.length()>0)
        {
            PendingIntent pi = PendingIntent.getActivity(context, 0,
                    new Intent(context, activity.getClass()), 0);
            SmsManager.getDefault().sendTextMessage(number, null, message, pi, null);
        }
    }
}
