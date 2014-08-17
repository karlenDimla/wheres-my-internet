package com.app.WebgeekFinal.network.error;

/**
 * Created by kdimla on 8/16/14.
 */
public class CustomVolleyError {
    private int mErrorCode;
    private String mErrorMessage;

    public CustomVolleyError(int code, String message){
        this.mErrorCode = code;
        this.mErrorMessage = message;
    }

    public int getmErrorCode() {
        return mErrorCode;
    }

    public void setmErrorCode(int mErrorCode) {
        this.mErrorCode = mErrorCode;
    }

    public String getmErrorMessage() {
        return mErrorMessage;
    }

    public void setmErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }
}
