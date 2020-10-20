package com.example.tanzeem.internity.InterviewerListActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.tanzeem.internity.LoginActivity.LoginActivity;
import com.example.tanzeem.internity.R;
import com.example.tanzeem.internity.SchedulesActivity.ShivaniSingh;

import java.util.List;

public class InterviewerList extends AppCompatActivity implements View.OnClickListener {

    //creating a list to add interviewer data and pass it to the recycler view and bind the data with it.
    List<InterviewerListGetterSetter> listInterviewer;
    RecyclerView recyclerView;
    InterviewerListRecyclerView adapter;
    CardView card1,card2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewer_list);

        //setting up the custom toolbar
        Toolbar toolbar = findViewById(R.id.interviewer_list_toolbar);
        setSupportActionBar(toolbar);

        //changing the overflow menu icon
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.ic_overflow));


        Log.e("Interviewer Activity","Entered the onCreate of Interviewer List");

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);

        /*

        //initializing the  list
        listInterviewer = new ArrayList<>();

        //Adding the data to the recycler view
        listInterviewer.add(new InterviewerListGetterSetter("Shivani Chauhan",
                "Working in Banglore",R.color.colorAccent));
        listInterviewer.add(new InterviewerListGetterSetter("Siddharth Suneel",
                "Working in Delhi",R.color.colorPrimary));
        listInterviewer.add(new InterviewerListGetterSetter("Alok Singh",
                "Working in Delhi",R.color.colorPrimaryDark));
        listInterviewer.add(new InterviewerListGetterSetter("Shivani Chauhan",
                "Working in Banglore",R.color.colorAccent));
        listInterviewer.add(new InterviewerListGetterSetter("Siddharth Suneel",
                "Working in Delhi",R.color.colorPrimary));
        listInterviewer.add(new InterviewerListGetterSetter("Alok Singh",
                "Working in Delhi",R.color.colorPrimaryDark));
        listInterviewer.add(new InterviewerListGetterSetter("Shivani Chauhan",
                "Working in Banglore",R.color.colorAccent));
        listInterviewer.add(new InterviewerListGetterSetter("Siddharth Suneel",
                "Working in Delhi",R.color.colorPrimary));
        listInterviewer.add(new InterviewerListGetterSetter("Alok Singh",
                "Working in Delhi",R.color.colorPrimaryDark));

        //Setting the reference of recycler view and adapter
        recyclerView = findViewById(R.id.interviewer_list_recyclerview);
        adapter = new InterviewerListRecyclerView(this,listInterviewer);
        //set the layout to linear
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //set adapter
        recyclerView.setAdapter(adapter);


        //Setting the on item click listener on the recycler view list items

        */
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.card1:
                Intent intent = new Intent(this,ShivaniSingh.class);
                startActivity(intent);
                break;
            case R.id.card2:
                Toast.makeText(this,"Card 2 clicked",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_interviewer_list,menu);

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

            Intent intent = new Intent(this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
