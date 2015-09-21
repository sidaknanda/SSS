package com.android.sss;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by optimus158 on 26-Aug-15.
 */
public class RegisterGcmIdService extends IntentService {

    private final Context context = SSSApplication.getAppContext();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private VolleySingleton singleton = VolleySingleton.getInstance();
    private JSONObject userDetailsJson;

    public RegisterGcmIdService() {
        super("RegisterService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initSetup();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken("865134249857",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i("SN", "GCM Registration Token: " + token);
            sendRegistrationToServer(token);
            // [END register_for_gcm]
        } catch (Exception e) {
            Toast.makeText(context, "Failed to complete token refresh", Toast.LENGTH_LONG).show();
            Log.d("SN", "Failed to complete token refresh", e);
        }
    }

    private void sendRegistrationToServer(String token) {
        try {
            RequestQueue queue = singleton.getRequestQueue();
            StringRequest request = new StringRequest(Utils.getGcmDeviceRegistrationUrl(userDetailsJson.getString(Utils.LOGINID), userDetailsJson.getString(Utils.PASSWORD), token), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(SSSApplication.getAppContext(), response, Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SSSApplication.getAppContext(), "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initSetup() {
        try {
            preferences = context.getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
            userDetailsJson = new JSONArray(preferences.getString(Utils.PREF_JSON_USER_DETAILS, null)).getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
