package com.app.WebgeekFinal.network.request;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.app.WebgeekFinal.callback.VolleyListener;
import com.app.WebgeekFinal.network.error.CustomVolleyError;

/**
 * Created by kdimla on 8/16/14.
 */
public abstract class AbstractRequest<T> extends Request<T> {
    private static final String TAG = AbstractRequest.class.getSimpleName();
    protected final VolleyListener<T> mListener;
    private int mErrorCode;
    private String mErrorMessage;

    public AbstractRequest(int method, VolleyListener<T> listener, String url) {
        super(method, url, null);
        this.mListener = listener;
        this.mErrorCode = 0;
        this.mErrorMessage = "";
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        if(error.networkResponse != null)
        mErrorCode = error.networkResponse.statusCode;
        mErrorMessage = error.getMessage();
        CustomVolleyError volleyError = new CustomVolleyError(mErrorCode,mErrorMessage);

        if (mListener != null) {
            mListener.onErrorResponse(volleyError);
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }
}
