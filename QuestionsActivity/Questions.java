package com.example.tanzeem.internity.QuestionsActivity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.tanzeem.internity.R;
import com.example.tanzeem.internity.SchedulesActivity.ShivaniSinghCopy;

public class Questions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Toolbar toolbar = findViewById(R.id.questions_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShivaniSinghCopy.class));
                finish();
            }
        });

        //if(getSupportActionBar() != null){
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //    getSupportActionBar().setHomeButtonEnabled(true); }



        String cardName = getIntent().getStringExtra("card_name");
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CardName",cardName);
        editor.commit();

        Log.e("Questions","Entered in onCreate activity.");


        //Setting the fragment in the activity
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,new Question1());
        fragmentTransaction.commit();
    }

    /*
    @Override
    public void onBackPressed() {
        //preventing back action button
        moveTaskToBack(true);
    }
    */
}
