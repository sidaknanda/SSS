package com.android.sss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CirclePageIndicator circlePageIndicator;
    private Button btn_getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIfLoggedIn();
        initSetup();
    }

    private void checkIfLoggedIn() {
        SharedPreferences preferences = getSharedPreferences(Utils.PREF_SSS_PREFERENCES, Context.MODE_PRIVATE);
        if (preferences.getString(Utils.PREF_JSON_USER_DETAILS, null) != null) {
            startActivity(new Intent(this, DashboardActivity.class));
        }
    }

    private void initSetup() {
        viewPager = (ViewPager) findViewById(R.id.viewPagerMain);
        MainViewPagerAdapter myPagerAdapter = new MainViewPagerAdapter(this);
        viewPager.setAdapter(myPagerAdapter);

        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circlePageIndicatorMain);
        circlePageIndicator.setViewPager(viewPager);

        btn_getStarted = (Button) findViewById(R.id.buttonGetStartedMain);
        btn_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
