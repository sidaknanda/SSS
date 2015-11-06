package com.android.sss;

import android.app.Dialog;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
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
            //TODO toast wont disappear
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // [START get_token]
            if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.GCM_Project_Number),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]
                Log.i(Utils.TAG, getString(R.string.gcm_token) + token);
                sendRegistrationToServer(token);
                // [END register_for_gcm]
            } else {
                Toast.makeText(context, getString(R.string.get_google_play_services), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, getString(R.string.gcm_registration_issue), Toast.LENGTH_LONG).show();
            Log.d(Utils.TAG, getString(R.string.failed_gcm_token_refresh), e);
        }
    }

    private void sendRegistrationToServer(String token) {
        try {
            RequestQueue queue = singleton.getRequestQueue();
            StringRequest request = new StringRequest(Utils.getGcmDeviceRegistrationUrl(userDetailsJson.getString(Utils.LOGINID), userDetailsJson.getString(Utils.PASSWORD), token), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SSSApplication.getAppContext(), getString(R.string.device_registration_error) + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initSetup() {
        try {
            preferences = context.getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
            userDetailsJson = new JSONArray(preferences.getString(Utils.PREF_JSON_USER_DETAILS, null)).getJSONObject(Utils.Numbers.ZERO.ordinal());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
