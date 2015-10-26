package com.android.sss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

    public static final String TAG = "SSS";
    public static final String GCM_MESSAGE_TAG = "SSS";
    public static final String PREF_SSS_PREFERENCES = "SSS Preferences";
    public static final String PREF_JSON_USER_DETAILS = "User Details";
    public static final String LOGINID = "LoginId";
    public static final String PASSWORD = "Password";
    public static final String DASHBOARD_LIST_STUDENTS = "Students";
    public static final String DASHBOARD_LIST_CONTACTSCHOOL = "Contact School";
    public static final String DASHBOARD_LIST_CHANGEPASSWORD = "Change Password";
    public static final String DASHBOARD_LIST_LOGOUT = "Logout";
    public static final String DASHBOARD_LIST_REPORTBUG = "Report Bug";
    public static final String PARAM_SELECTED_STUDENT = "Student Selected";
    public static final String[] STUDENT_STATUS = {"Bus Entry (Morning)", "Bus Exit (Morning)", "School Entry", "School Exit", "Bus Entry (Evening)", "Bus Exit (Evening)"};

    public enum Numbers {ZERO, ONE, TWO, THREE, FOUR}

    public static final String getGcmDeviceRegistrationUrl(String loginId, String password, String gcmId) {
        return "http://172.16.1.74/RegisterGcmId.php?LoginId=" + loginId + "&Password=" + password + "&GCMID=" + gcmId;
    }

    public static final String getGcmDeviceDeRegistrationUrl(String loginId, String password) {
        return "http://172.16.1.74/DeRegisterGcmId.php?LoginId=" + loginId + "&Password=" + password;
    }

    public static final String getLoginUrl(String loginId, String password) {
        return "http://172.16.1.74/GetStudentDetails.php?LoginId=" + loginId + "&Password=" + password;
    }

    static final String getStudentUpdatesUrl(String Rfid) {
        return "http://172.16.1.74/getUpdatesFromDB.php?RFID=" + Rfid;
    }

    static final String getChangePasswordUrl(String loginId, String password) {
        return "http://172.16.1.74/ChangePassword.php?LoginId=" + loginId + "&Password=" + password;
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

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                }
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
        }

        return haveConnectedWifi || haveConnectedMobile;
    }
}
