package com.example.tanzeem.internity.StudentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tanzeem.internity.LoginActivity.LoginActivity;
import com.example.tanzeem.internity.R;

public class StudentDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView userName ,userEmail,dashboardText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        Log.e("Student Dashboard","Entered the student's activity.");

        //Setting the custom toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.ic_overflow));


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //disabling the default drawer icon
        toggle.setDrawerIndicatorEnabled(false);
        //setting the on click listener on custom drawer icon
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        //setting the custom drawer icon
        toggle.setHomeAsUpIndicator(R.drawable.ic_toggle_menu);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Setting the navigatin view items
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        userName = header.findViewById(R.id.nav_bar_student_name);
        userEmail = header.findViewById(R.id.nav_bar_email);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        String user_Name = sharedPreferences.getString("Username","abc");
        String user_email = sharedPreferences.getString("Email","xyz");
        String schedule_text = sharedPreferences.getString("Text","def");

        Log.e("User Name : ",user_Name);
        Log.e("Email : ",user_email);
        Log.e("Text : ",schedule_text);

        //userName.setText(user_Name);
        //userEmail.setText(user_email);
        userName.setText(user_Name);
        userEmail.setText(user_email);




        //Setting the dashboard text
        dashboardText = findViewById(R.id.dashboard_text);
        dashboardText.setText(schedule_text);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //preventing back button action
            moveTaskToBack(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            //Deleting all the saved data
            SharedPreferences preferences = getSharedPreferences("UserInfo", 0);
            preferences.edit().clear().apply();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.result_item) {

            Intent i1 = new Intent(this,ResultActivity.class);
            startActivity(i1);

        } else if (id == R.id.feedback_item) {

            Intent i2 = new Intent(this,FeedbackForm.class);
            startActivity(i2);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
