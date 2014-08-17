package com.app.WebgeekFinal.network.request;

import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.app.WebgeekFinal.callback.VolleyListener;
import com.app.WebgeekFinal.constant.EndpointConstant;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by kdimla on 8/16/14.
 */
public class IpRequest extends AbstractRequest<String> {

    public IpRequest(VolleyListener<String> listener) {
        super(Method.GET,  listener, EndpointConstant.IP_ENDPOINT);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        String ip = "";
        try {
            String responseString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            Log.i("kl","RESPONSE IP: "+responseString);
            JSONObject jsonObject = new JSONObject(responseString);
            ip = jsonObject.optString("ip");
        } catch(JSONException je){
            je.printStackTrace();
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return Response.success(ip, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }
}
