package com.android.sss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by OPTIMUSDOM ubuntu151 on 21/9/15.
 */
public class DashboardActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerDashboard;
    private ExpandableListView studentsListDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initSetup();
        setupButtonClicks();
    }

    private void initSetup() {
        preferences = getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerDashboard = (DrawerLayout) findViewById(R.id.drawer_layout);
        studentsListDashboard = (ExpandableListView) findViewById(R.id.expandableListViewDashboard);

        setSupportActionBar(toolbar);
        setupDashboardDrawer();
    }

    private void setupDashboardDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerDashboard, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("Student Surveillance System");
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Dashboard");
                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerDashboard.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    private void setupButtonClicks() {
    }
}
