package com.android.sss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by OPTIMUSDOM ubuntu151 on 19/10/15.
 */
public class ChangePasswordFragment extends Fragment {

    private View fragmentView;
    private EditText et_passwordNew;
    private EditText et_passwordNewConfirm;
    private FloatingActionButton b_changePassword;
    private String changePassword;
    private String confirmChangePassword;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog dialog;
    private VolleySingleton volleySingleton = VolleySingleton.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_change_password, container, false);
        initSetup();
        updatePassword();
        return fragmentView;
    }

    private void initSetup() {
        preferences = getActivity().getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
        dialog = Utils.getProgressDialog(getActivity());
        et_passwordNew = (EditText) fragmentView.findViewById(R.id.editTextChangePassword);
        et_passwordNewConfirm = (EditText) fragmentView.findViewById(R.id.editTextConfirmChangePassword);
        b_changePassword = (FloatingActionButton) fragmentView.findViewById(R.id.fab_changePassword);
    }

    private void updatePassword() {
        b_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword = et_passwordNew.getText().toString();
                confirmChangePassword = et_passwordNewConfirm.getText().toString();
                if (!changePassword.equals("") && !confirmChangePassword.equals("")) {
                    if (changePassword.equals(confirmChangePassword)) {
                        update();
                    } else {
                        Toast.makeText(getActivity(), "Passwords do not Match !!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "All Field(s) Required !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void update() {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog.show();
            RequestQueue queue = volleySingleton.getRequestQueue();
            //TODO
            StringRequest loginRequest = new StringRequest(Utils.getChangePasswordUrl(Utils.getLoggedInUserStudents().get(0).getLoginId(), changePassword), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("1")) {
                        updateDevicePreferences();
                        Toast.makeText(getActivity(), "Password Successfully Updated !!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), DashboardActivity.class));
                    } else {
                        Toast.makeText(getActivity(), "Server Issue\nTry again after some time", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getActivity(), "Server Issue\nTry again after some time", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            queue.add(loginRequest);
        } else {
            Toast.makeText(getActivity(), "Internet Connectivity Issue !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDevicePreferences() {
        ArrayList<StudentModel> loggedInStudents = Utils.getLoggedInUserStudents();
        for (StudentModel student : loggedInStudents) {
            student.setPassword(changePassword);
        }
        String temp = new Gson().toJson(loggedInStudents, new TypeToken<ArrayList<StudentModel>>() {
        }.getType());
        editor.putString(Utils.PREF_JSON_USER_DETAILS, temp);
        editor.commit();
    }
}
