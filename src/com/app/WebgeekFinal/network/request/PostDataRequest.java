package com.app.WebgeekFinal.network.request;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.app.WebgeekFinal.callback.VolleyListener;
import com.app.WebgeekFinal.constant.EndpointConstant;
import com.app.WebgeekFinal.model.ConnectionData;
import com.app.WebgeekFinal.utility.SharedPrefUtility;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by kdimla on 8/16/14.
 */
public class PostDataRequest<T> extends AbstractRequest<T> {
    private ConnectionData mData;
    private Context context;
    private final String BODY_CONTENT_TYPE = "application/json;charset=utf-8;";
    public PostDataRequest(Context context, VolleyListener<T> listener, ConnectionData data) {
        super(Method.POST,  listener, EndpointConstant.POST_DATA_ENDPOINT);
        this.mData = data;
        this.context = context;
    }
    @Override
    public String getBodyContentType() {
        return BODY_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        JSONObject object = new JSONObject();
        try{
            object.put("lat",mData.getmLat());
            object.put("long",mData.getmLong());
            object.put("uuid",mData.getmUuid());
            object.put("ssid",mData.getmSsid());
            object.put("ip",mData.getmIp());
            object.put("strength",mData.getmSignalStrength());
            object.put("haspassword",mData.getmHasPassword());
        }catch (JSONException je){
            je.printStackTrace();
        }
        String body = object.toString();
        Log.i("kl", "POST HEADER: " + body);
        return body.getBytes();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String responseString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            Log.i("kl", "RESPONSE POST: " + responseString);
            SharedPrefUtility.getInstance(context).putString("lastssid",mData.getmSsid());
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return Response.success(null, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }
}
