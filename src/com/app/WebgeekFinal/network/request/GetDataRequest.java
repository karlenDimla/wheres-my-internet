package com.app.WebgeekFinal.network.request;

import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.app.WebgeekFinal.callback.VolleyListener;
import com.app.WebgeekFinal.constant.EndpointConstant;
import com.app.WebgeekFinal.model.ConnectionData;
import com.app.WebgeekFinal.utility.ParserUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by kdimla on 8/16/14.
 */
public class GetDataRequest extends AbstractRequest<ArrayList<ConnectionData>> {
    public GetDataRequest(VolleyListener<ArrayList<ConnectionData>> listener) {
        super(Method.GET,  listener, EndpointConstant.GET_DATA_ENDPOINT);
    }

    @Override
    protected Response<ArrayList<ConnectionData>> parseNetworkResponse(NetworkResponse networkResponse) {
        ArrayList<ConnectionData> data = new ArrayList<ConnectionData>();
        try {
            String responseString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            Log.i("kl", "RESPONSE GET DATA: " + responseString);
//            JSONArray jsonArray = new JSONArray(responseString);
//            for(int cnt = 0; cnt < jsonArray.length(); cnt++){
//                data.add(new ConnectionData(jsonArray.getJSONObject(cnt)));
//            }
            data = ParserUtility.parseList(responseString);
        } catch(JSONException je){
            je.printStackTrace();
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return Response.success(data, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }


}
