package com.app.WebgeekFinal.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import com.app.WebgeekFinal.activity.MyActivity;
import com.app.WebgeekFinal.model.ConnectionData;
import com.app.WebgeekFinal.utility.ParserUtility;
import com.app.WebgeekFinal.utility.SharedPrefUtility;

import java.util.ArrayList;

/**
 * Created by kdimla on 8/17/14.
 */
public class SmsReceiver extends BroadcastReceiver {
    private final SmsManager mSmsManager = SmsManager.getDefault();
    private static final String SENDERNUMBER="21588718";
    public void onReceive(Context context, Intent intent){
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    if(senderNum.equals(SENDERNUMBER)){
                        String header = message.substring(1, message.indexOf(")")-1);
                        String actualmsg = message.substring(message.indexOf(")") + 1);

                        int actualCnt = Integer.parseInt(header.split("/")[1]);
                        int currReceivedmsgs = SharedPrefUtility.getInstance(context).getInt("receivedmsgscount", 0);

                        Log.i("kl","actualCnt: "+actualCnt+" currmsgscnt:"+currReceivedmsgs);

                        currReceivedmsgs++;
                        SharedPrefUtility.getInstance(context).putInt("receivedmsgscount",currReceivedmsgs);
                        SharedPrefUtility.getInstance(context).putString("msg"+header.split("/")[0], actualmsg);
                        if(actualCnt == currReceivedmsgs){
                            Intent intentNew = new Intent(context,MyActivity.class);
                            intentNew.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                            intentNew.putExtra("SMSBODY",formData(context,actualCnt));
                            context.startActivity(intent);
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formData(Context context, int expectedMsgsCnt){
        StringBuffer buffer = new StringBuffer();
        for(int cnt = 1; cnt <= expectedMsgsCnt; cnt++){
            String message = SharedPrefUtility.getInstance(context).getString("msg" + cnt, "");
            SharedPrefUtility.getInstance(context).putString("msg" + cnt, "");
            buffer.append(message);
        }
        SharedPrefUtility.getInstance(context).putInt("receivedmsgscount",0);

        return buffer.toString();
    }
}