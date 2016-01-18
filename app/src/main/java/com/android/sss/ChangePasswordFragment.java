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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


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
                        if (!Utils.getLoggedInUserStudents().get(Utils.Numbers.ZERO.ordinal()).getPassword().equals(changePassword)) {
                            update();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.entered_password_updated), Toast.LENGTH_LONG).show();
                            et_passwordNew.setText("");
                            et_passwordNewConfirm.setText("");
                        }
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.passwords_dont_match), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.all_fields_required), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void update() {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog.show();
            RequestQueue queue = volleySingleton.getRequestQueue();
            //TODO
            StringRequest request = new StringRequest(Utils.getChangePasswordUrl(Utils.getLoggedInUserStudents().get(Utils.Numbers.ZERO.ordinal()).getLoginId(), changePassword), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("1")) {
                        updateDevicePreferences();
                        Toast.makeText(getActivity(), getString(R.string.password_successfully_updated), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), DashboardActivity.class));
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.server_issue), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.server_issue), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
        } else {
            Toast.makeText(getActivity(), getString(R.string.internet_issue), Toast.LENGTH_SHORT).show();
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
