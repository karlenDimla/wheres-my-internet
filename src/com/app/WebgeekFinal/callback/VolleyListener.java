package com.app.WebgeekFinal.callback;

import com.app.WebgeekFinal.network.error.CustomVolleyError;

/**
 * Created by kdimla on 8/16/14.
 */
public interface VolleyListener<T> {
    void onResponse(T response);
    void onErrorResponse(CustomVolleyError customVolleyError);
}
