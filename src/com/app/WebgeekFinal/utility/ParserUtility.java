package com.app.WebgeekFinal.utility;

import com.app.WebgeekFinal.model.ConnectionData;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by kdimla on 8/17/14.
 */
public class ParserUtility {
    public static ArrayList<ConnectionData> parseList(String responseString) throws JSONException{
        ArrayList<ConnectionData> data = new ArrayList<ConnectionData>();
        JSONArray jsonArray = new JSONArray(responseString);
        for(int cnt = 0; cnt < jsonArray.length(); cnt++){
            data.add(new ConnectionData(jsonArray.getJSONObject(cnt)));
        }
        return data;
    }
}
