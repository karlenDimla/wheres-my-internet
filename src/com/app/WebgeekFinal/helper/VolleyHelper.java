package com.app.WebgeekFinal.helper;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.app.WebgeekFinal.callback.VolleyListener;
import com.app.WebgeekFinal.model.ConnectionData;
import com.app.WebgeekFinal.network.request.GetDataRequest;
import com.app.WebgeekFinal.network.request.IpRequest;
import com.app.WebgeekFinal.network.request.PostDataRequest;

/**
 * Created by kdimla on 8/16/14.
 */
public class VolleyHelper {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static VolleyHelper sInstance;

    public static VolleyHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new VolleyHelper(context);
        }
        return sInstance;
    }

    private VolleyHelper(Context context) {
        setRequestQueue(Volley.newRequestQueue(context));
        getRequestQueue().start();
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public void setRequestQueue(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void setImageLoader(ImageLoader mImageLoader) {
        this.mImageLoader = mImageLoader;
    }

    public Request<?> getIpAddress(final VolleyListener listener) {
        return performWebRequest(new IpRequest(listener));
    }

    public Request<?> getListData(final VolleyListener listener) {
        return performWebRequest(new GetDataRequest(listener));
    }

    public Request<?> postListData(Context context,ConnectionData data, final VolleyListener listener) {
        Log.i("KL", "POST DATAAAAA");
        return performWebRequest(new PostDataRequest<Object>(context,listener, data));
    }

    private Request<?> performWebRequest(final Request<?> request) {
        getRequestQueue().add(request);
        return request;
    }
}

