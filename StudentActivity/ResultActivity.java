package com.example.tanzeem.internity.StudentActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Toast;

import com.example.tanzeem.internity.InterviewerListActivity.InterviewerList;
import com.example.tanzeem.internity.R;

public class ResultActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Setting the custom toolbar
        Toolbar toolbar = findViewById(R.id.result_toolbar);
        setSupportActionBar(toolbar);

        //setting the parent activity icon and intent
        if(getSupportActionBar() != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
                    finish();
                }
            });
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        String score = sharedPreferences.getString("Score","");
        //Toast.makeText(this,"Score : " + score,Toast.LENGTH_LONG).show();

        //setting the fragment transaction manager
        fragmentTransaction = getFragmentManager().beginTransaction();

        //choosing the particular fragment
        if (Float.valueOf(score) == 0){
            fragmentTransaction.add(R.id.result_fragment_container,new ResultFragmentDefault());
        }
        else if (Float.valueOf(score) > 0 && Float.valueOf(score) < 60)
            fragmentTransaction.add(R.id.result_fragment_container,new ResultFragmentThree());
        else if(Float.valueOf(score) >= 60 && Float.valueOf(score) < 90)
            fragmentTransaction.add(R.id.result_fragment_container,new ResultFragmentTwo());
        else if(Float.valueOf(score) >=90)
            fragmentTransaction.add(R.id.result_fragment_container,new ResultFragmentOne());

        //commit the changes
        fragmentTransaction.commit();

    }

    /*
    @Override
    public void onBackPressed() {
        //preventing back action button
        moveTaskToBack(false);
    }
    */
}

