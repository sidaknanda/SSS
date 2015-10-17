package com.android.sss;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
    private ArrayList<StudentModel> loggedInStudents;
    private VolleySingleton volleySingleton = VolleySingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initSetup();
        setupListAdapter();
    }

    private void initSetup() {
        loggedInStudents = Utils.getLoggedInUserStudents();

        preferences = getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerDashboard = (DrawerLayout) findViewById(R.id.drawer_layout);
        dashboardItems = (ExpandableListView) findViewById(R.id.expandableListViewDashboard);

        setSupportActionBar(toolbar);
        Bundle bundle = new Bundle();
        // setting first student to fragment
        bundle.putParcelable(Utils.PARAM_SELECTED_STUDENT, loggedInStudents.get(0));
        FragmentManager transaction = getSupportFragmentManager();
        StudentFeedFragment studentFeedFragment = new StudentFeedFragment();
        studentFeedFragment.setArguments(bundle);
        transaction.beginTransaction().replace(R.id.frameLayout, studentFeedFragment).commit();

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
                bundle.putParcelable(Utils.PARAM_SELECTED_STUDENT, loggedInStudents.get(childPosition));
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
                logout();
                break;
            case 4:
                drawerDashboard.closeDrawers();
                break;
        }
    }

    private void logout() {
        final ProgressDialog dialog = Utils.getProgressDialog(this);
        if (Utils.isNetworkAvailable(this)) {
            dialog.show();
            RequestQueue queue = volleySingleton.getRequestQueue();
            StringRequest request = new StringRequest(Utils.getGcmDeviceDeRegistrationUrl(loggedInStudents.get(0).getLoginId(), loggedInStudents.get(0).getPassword()), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    editor.putString(Utils.PREF_JSON_USER_DETAILS, null);
                    editor.commit();
                    startActivity(new Intent(SSSApplication.getAppContext(), LoginActivity.class));
                    dialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SSSApplication.getAppContext(), "Issue !!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            queue.add(request);
        } else {
            Toast.makeText(this, "Internet needed to Logout !!!", Toast.LENGTH_SHORT).show();
            return;
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
        ArrayList<String> studentsNames = new ArrayList();
        for (StudentModel student : loggedInStudents) {
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