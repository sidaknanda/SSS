package com.android.sss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by OPTIMUSDOM ubuntu151 on 18/9/15.
 */
public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText etLoginId, etPassword;
    private FloatingActionButton fabLogin;
    private VolleySingleton volleySingleton = VolleySingleton.getInstance();
    private ProgressDialog dialog;
    private JSONArray userDetailsArray;
    private String loginId;
    private String password;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initSetup();
        setupButtonClicks();
    }

    private void setupButtonClicks() {
        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginId = etLoginId.getText().toString();
                password = etPassword.getText().toString();
                if (!loginId.equals("") && !password.equals("")) {
                    login();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.all_fields_required), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void login() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            dialog.show();
            RequestQueue queue = volleySingleton.getRequestQueue();

            StringRequest loginRequest = new StringRequest(Utils.getLoginUrl(loginId, password), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        userDetailsArray = new JSONArray(response);

                        editor.putString(Utils.PREF_JSON_USER_DETAILS, userDetailsArray.toString());
                        editor.commit();
                        resisterGCM();
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        dialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), getString(R.string.wrong_credentials), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        dialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(LoginActivity.this, getString(R.string.server_issue), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(loginRequest);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internet_issue), Toast.LENGTH_SHORT).show();
        }
    }

    private void initSetup() {
        preferences = getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dialog = Utils.getProgressDialog(LoginActivity.this);
        etLoginId = (EditText) findViewById(R.id.et_loginId);
        etPassword = (EditText) findViewById(R.id.et_password);
        fabLogin = (FloatingActionButton) findViewById(R.id.fab_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void resisterGCM() {
        startService(new Intent(this, RegisterGcmIdService.class));
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(this, MainActivity.class);
        startActivity(startMain);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}