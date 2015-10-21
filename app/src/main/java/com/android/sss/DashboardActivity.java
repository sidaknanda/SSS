package com.android.sss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
        bundle.putParcelable(Utils.PARAM_SELECTED_STUDENT, loggedInStudents.get(Utils.UTIL_ZERO));
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
                if (groupPosition == Utils.UTIL_ZERO) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Utils.PARAM_SELECTED_STUDENT, loggedInStudents.get(childPosition));
                    FragmentManager transaction = getSupportFragmentManager();
                    StudentFeedFragment studentFeedFragment = new StudentFeedFragment();
                    studentFeedFragment.setArguments(bundle);
                    transaction.beginTransaction().replace(R.id.frameLayout, studentFeedFragment).commit();
                } else {
                    if (childPosition == Utils.UTIL_ZERO) {
                        callSchool();
                    } else if (childPosition == Utils.UTIL_ONE) {
                        messageSchool();
                    } else if (childPosition == Utils.UTIL_TWO) {
                        mailSchool();
                    }
                }
                drawerDashboard.closeDrawers();
                dashboardItems.collapseGroup(groupPosition);
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

    private void mailSchool() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sss@sss.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SSS Query");
        try {
            startActivity(emailIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void messageSchool() {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:9958767367"));
        try {
            startActivity(smsIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void callSchool() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + "9958767367"));
        try {
            startActivity(callIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupItemsClick(ExpandableListView parent, View v, int groupPosition, long id) {
        switch (groupPosition) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                changePassword();
                drawerDashboard.closeDrawers();
                break;
            case 3:
                drawerDashboard.closeDrawers();

                break;
            case 4:
                drawerDashboard.closeDrawers();
                logout();
                break;
        }
    }

    private void changePassword() {
        FragmentManager transaction = getSupportFragmentManager();
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        transaction.beginTransaction().replace(R.id.frameLayout, changePasswordFragment).commit();
    }

    private void logout() {
        final ProgressDialog dialog = Utils.getProgressDialog(this);
        if (Utils.isNetworkAvailable(this)) {
            dialog.show();
            RequestQueue queue = volleySingleton.getRequestQueue();
            StringRequest request = new StringRequest(Utils.getGcmDeviceDeRegistrationUrl(loggedInStudents.get(Utils.UTIL_ZERO).getLoginId(), loggedInStudents.get(Utils.UTIL_ZERO).getPassword()), new Response.Listener<String>() {
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
                    Toast.makeText(SSSApplication.getAppContext(), "Server Issue\n" +
                            "Try again after some time", Toast.LENGTH_SHORT).show();
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
        dashboardList.add(Utils.DASHBOARD_LIST_REPORTBUG);
        dashboardList.add(Utils.DASHBOARD_LIST_LOGOUT);

        dashboardMap.put(dashboardList.get(Utils.UTIL_ZERO), generateStudentList());
        dashboardMap.put(dashboardList.get(Utils.UTIL_ONE), generateContactSchoolList());
        dashboardMap.put(dashboardList.get(Utils.UTIL_TWO), new ArrayList<String>());
        dashboardMap.put(dashboardList.get(Utils.UTIL_THREE), new ArrayList<String>());
        dashboardMap.put(dashboardList.get(Utils.UTIL_FOUR), new ArrayList<String>());
    }

    private ArrayList<String> generateContactSchoolList() {
        ArrayList<String> contactSchool = new ArrayList();
        contactSchool.add(getString(R.string.call_school));
        contactSchool.add(getString(R.string.message_school));
        contactSchool.add(getString(R.string.email_school));
        return contactSchool;
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