package com.android.sss;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by OPTIMUSDOM ubuntu151 on 18/9/15.
 */
public class Utils {

    public static final String PREF_SSS_PREFERENCES = "SSS Preferences";
    public static final String PREF_JSON_USER_DETAILS = "User Details";
    public static final String LOGINID = "LoginId";
    public static final String PASSWORD = "Password";

    public static final String getGcmDeviceRegistrationUrl(String loginId, String password, String gcmId) {
        return "http://172.16.2.128/RegisterGcmId.php?LoginId=" + loginId + "&Password=" + password + "&GCMID=" + gcmId;
    }

    public static final String getLoginUrl(String loginId, String password) {
        return "http://172.16.2.128/GetStudentDetails.php?LoginId=" + loginId + "&Password=" + password;
    }

    public static final ProgressDialog getProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
