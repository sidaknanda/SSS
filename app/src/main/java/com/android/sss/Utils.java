package com.android.sss;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by OPTIMUSDOM ubuntu151 on 18/9/15.
 */
public class Utils {

    public static final String getLoginUrl(String loginId, String password) {
        return "http://172.16.1.79/GetStudentDetails.php?LoginId=" + loginId + "&" + password;
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
