package com.android.sss;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by optimus158 on 26-Aug-15.
 */
public class VolleySingleton {

    private static VolleySingleton singleton = null;
    private RequestQueue requestQueue;

    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(SSSApplication.getAppContext());
    }

    public static VolleySingleton getInstance() {
        if (singleton == null) {
            singleton = new VolleySingleton();
        }
        return singleton;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
