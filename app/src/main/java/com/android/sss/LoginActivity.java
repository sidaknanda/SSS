package com.android.sss;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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

    private EditText etLoginId, etPassword;
    private FloatingActionButton fabLogin;
    private VolleySingleton volleySingleton = VolleySingleton.getInstance();
    private ProgressDialog dialog = Utils.getProgressDialog(LoginActivity.this);
    private JSONArray userDetailsArray;
    String loginId;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginId = (EditText) findViewById(R.id.et_loginId);
        etPassword = (EditText) findViewById(R.id.et_password);

        fabLogin = (FloatingActionButton) findViewById(R.id.fab_login);
        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginId = etLoginId.getText().toString();
                password = etPassword.getText().toString();

                dialog.show();

                RequestQueue queue = volleySingleton.getRequestQueue();

                StringRequest loginRequest = new StringRequest(Utils.getLoginUrl(loginId, password), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            userDetailsArray = new JSONArray(response);
                            resisterGCM();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(loginRequest);
            }
        });
    }

    private void resisterGCM() {

    }
}