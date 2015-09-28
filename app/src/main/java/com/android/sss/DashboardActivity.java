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
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by OPTIMUSDOM ubuntu151 on 21/9/15.
 */
public class DashboardActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerDashboard;
    private ExpandableListView dashboardItems;
    private HashMap<String, List<String>> dashboardMap;
    private ArrayList<String> dashboardList;
    private DashboardExpandableListViewAdapter dashboardExpandableListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initSetup();
        setupDashboardListAdapter();
    }

    private void initSetup() {
        preferences = getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerDashboard = (DrawerLayout) findViewById(R.id.drawer_layout);
        dashboardItems = (ExpandableListView) findViewById(R.id.expandableListViewDashboard);

        setSupportActionBar(toolbar);
        setupDashboardDrawer();
    }

    private void setupDashboardDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerDashboard, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("SSS");
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

    private void setupDashboardListAdapter() {
        setupExpandableListviewData();
        dashboardExpandableListViewAdapter = new DashboardExpandableListViewAdapter(this, dashboardList, dashboardMap);
        dashboardItems.setAdapter(dashboardExpandableListViewAdapter);
    }

    private void setupExpandableListviewData() {
        dashboardMap = new HashMap();

        dashboardList = new ArrayList();

        dashboardList.add(Utils.DASHBOARD_LIST_STUDENTS);
        dashboardList.add(Utils.DASHBOARD_LIST_CONTACTSCHOOL);
        dashboardList.add(Utils.DASHBOARD_LIST_CHANGEPASSWORD);
        dashboardList.add(Utils.DASHBOARD_LIST_LOGOUT);
        dashboardList.add(Utils.DASHBOARD_LIST_REPORTBUG);

        dashboardMap.put(dashboardList.get(0), generateStudentNameList());
        dashboardMap.put(dashboardList.get(1), null);
        dashboardMap.put(dashboardList.get(2), null);
        dashboardMap.put(dashboardList.get(3), null);
        dashboardMap.put(dashboardList.get(4), null);
    }

    private ArrayList<String> generateStudentNameList() {
        ArrayList<String> studentsNames = new ArrayList<String>();
        ArrayList<StudentModel> loggedInStudentsDetails = Utils.getLoggedInUserStudents();
        for (StudentModel student : loggedInStudentsDetails) {
            studentsNames.add(student.getStudentName());
        }
        return studentsNames;
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}