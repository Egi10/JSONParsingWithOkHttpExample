package com.example.egi_fcb.jsonparsingwithokhttpexample.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by egi_fcb on 8/9/17.
 */

public class InternetConnection {

    // Check Whether Internet Connection Is Available Or Not

    public static boolean checkConnection(Context context){
        return ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
