package com.android.sss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by OPTIMUSDOM ubuntu151 on 18/9/15.
 */
public class Utils {

    public static final String PREF_SSS_PREFERENCES = "SSS Preferences";
    public static final String PREF_JSON_USER_DETAILS = "User Details";
    public static final String LOGINID = "LoginId";
    public static final String PASSWORD = "Password";
    public static final String DASHBOARD_LIST_STUDENTS = "Students";
    public static final String DASHBOARD_LIST_CONTACTSCHOOL = "Contact School";
    public static final String DASHBOARD_LIST_CHANGEPASSWORD = "Change Password";
    public static final String DASHBOARD_LIST_LOGOUT = "Logout";
    public static final String DASHBOARD_LIST_REPORTBUG = "Report Bug";

    public static final String getGcmDeviceRegistrationUrl(String loginId, String password, String gcmId) {
        return "http://172.16.2.150/RegisterGcmId.php?LoginId=" + loginId + "&Password=" + password + "&GCMID=" + gcmId;
    }

    public static final String getLoginUrl(String loginId, String password) {
        return "http://172.16.2.150/GetStudentDetails.php?LoginId=" + loginId + "&Password=" + password;
    }

    public static final ProgressDialog getProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static final ArrayList<StudentModel> getLoggedInUserStudents() {
        try {
            SharedPreferences preferences = SSSApplication.getAppContext().getSharedPreferences(PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
            JSONArray studentsArray = new JSONArray(preferences.getString(PREF_JSON_USER_DETAILS, null));
            return new Gson().fromJson(studentsArray.toString(), new TypeToken<ArrayList<StudentModel>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
