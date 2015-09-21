package com.android.sss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by OPTIMUSDOM ubuntu151 on 21/9/15.
 */
public class DashboardActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextView display;
    private Button b_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initSetup();
        setupButtonClicks();

        display.setText(preferences.getString(Utils.PREF_JSON_USER_DETAILS, null));
    }

    private void initSetup() {
        preferences = getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
        display = (TextView) findViewById(R.id.textViewD);
        b_logout = (Button) findViewById(R.id.buttonLogout);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    private void setupButtonClicks() {
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(Utils.PREF_JSON_USER_DETAILS,null);
                editor.commit();
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            }
        });
    }
}
