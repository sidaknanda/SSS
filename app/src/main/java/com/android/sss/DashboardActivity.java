package com.android.sss;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
    private DashboardListAdapter dashboardListAdapter;
    private ArrayList<StudentModel> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initSetup();
        setupListAdapter();
    }

    private void initSetup() {
        preferences = getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerDashboard = (DrawerLayout) findViewById(R.id.drawer_layout);
        dashboardItems = (ExpandableListView) findViewById(R.id.expandableListViewDashboard);

        setSupportActionBar(toolbar);
        Bundle bundle = new Bundle();
        // setting first student to fragment
        bundle.putParcelable(Utils.PARAM_SELECTED_STUDENT, Utils.getLoggedInUserStudents().get(0));
        FragmentManager transaction = getSupportFragmentManager();
        StudentFeedFragment studentFeedFragment = new StudentFeedFragment();
        studentFeedFragment.setArguments(bundle);
        transaction.beginTransaction().replace(R.id.frameLayout, studentFeedFragment).commit();

        students = Utils.getLoggedInUserStudents();

        setupDrawer();
    }

    private void setupDrawer() {
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

    private void setupListAdapter() {
        setupExpandableListData();
        dashboardListAdapter = new DashboardListAdapter(this, dashboardList, dashboardMap);
        dashboardItems.setAdapter(dashboardListAdapter);
        dashboardItems.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Utils.PARAM_SELECTED_STUDENT, Utils.getLoggedInUserStudents().get(childPosition));
                FragmentManager transaction = getSupportFragmentManager();
                StudentFeedFragment studentFeedFragment = new StudentFeedFragment();
                studentFeedFragment.setArguments(bundle);
                transaction.beginTransaction().replace(R.id.frameLayout, studentFeedFragment).commit();
                drawerDashboard.closeDrawers();
                return false;
            }
        });
        dashboardItems.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setupItemsClick(parent, v, groupPosition, id);
                return false;
            }
        });
    }

    private void setupItemsClick(ExpandableListView parent, View v, int groupPosition, long id) {
        switch (groupPosition) {
            case 0:
                break;
            case 1:
                drawerDashboard.closeDrawers();
                break;
            case 2:
                drawerDashboard.closeDrawers();
                break;
            case 3:
                drawerDashboard.closeDrawers();
                editor.putString(Utils.PREF_JSON_USER_DETAILS, null);
                editor.commit();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case 4:
                drawerDashboard.closeDrawers();
                break;
        }
    }


    private void setupExpandableListData() {
        dashboardMap = new HashMap();

        dashboardList = new ArrayList();

        dashboardList.add(Utils.DASHBOARD_LIST_STUDENTS);
        dashboardList.add(Utils.DASHBOARD_LIST_CONTACTSCHOOL);
        dashboardList.add(Utils.DASHBOARD_LIST_CHANGEPASSWORD);
        dashboardList.add(Utils.DASHBOARD_LIST_LOGOUT);
        dashboardList.add(Utils.DASHBOARD_LIST_REPORTBUG);

        dashboardMap.put(dashboardList.get(0), generateStudentList());
        dashboardMap.put(dashboardList.get(1), new ArrayList<String>());
        dashboardMap.put(dashboardList.get(2), new ArrayList<String>());
        dashboardMap.put(dashboardList.get(3), new ArrayList<String>());
        dashboardMap.put(dashboardList.get(4), new ArrayList<String>());
    }

    private ArrayList<String> generateStudentList() {
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