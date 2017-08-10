package com.example.egi_fcb.jsonparsingwithokhttpexample.Parser;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by egi_fcb on 8/9/17.
 */

public class JSONParser {

    //URL
    private static final String MAIN_URL = "http://pratikbutani.x10.mx/json_data.json";

    //TAGs Defined Here
    public static final String TAG = "TAG";

    //Key to Send
    private static final String KEY_USER_ID = "user_id";

    //Response
    private static Response response;

    //Get Data From WEB
    public static JSONObject getDataFromWeb(){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(MAIN_URL).build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        } catch (JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}
