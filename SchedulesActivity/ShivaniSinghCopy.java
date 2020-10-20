package com.example.tanzeem.internity.SchedulesActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.tanzeem.internity.LoginActivity.LoginActivity;
import com.example.tanzeem.internity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShivaniSinghCopy extends AppCompatActivity {

    private DatabaseReference databaseReference,databaseReference3;

    private RecyclerView mScheduleRecyclerViewCopy;
    List<ScheduleGetterSetter> mScheduleListCopy;
    ScheduleRecyclerView adapterCopy;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shivani_singh_copy);

        Log.e("Shivani Singh Copy","Entered on Create method.");

        //setting up the toolbar reference
        Toolbar toolbar = findViewById(R.id.shivani_singh_copy_toolbar);
        setSupportActionBar(toolbar);

        //changing the overflow menu icon
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.ic_overflow));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student Cards");
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Student Cards");


        //progress dialog reference and setting the message
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading the cards.....");
        progressDialog.show();

        //Initializing the list
        mScheduleListCopy = new ArrayList<>();

        //setting the reference of recycler view and adapter
        mScheduleRecyclerViewCopy = findViewById(R.id.schedule_recylerview1);
        adapterCopy = new ScheduleRecyclerView(this,mScheduleListCopy);

        //set the layout to linear
        mScheduleRecyclerViewCopy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //set Adapter
        mScheduleRecyclerViewCopy.setAdapter(adapterCopy);

        //Adding the values to recycler view
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    Map<String, String> map = (Map<String, String>) ds.getValue();

                    String nam = map.get("Name");
                    String desc = map.get("Description");
                    String tme = map.get("Time");
                    String scr = map.get("Score");

                    //Addint the data to recycler view
                    mScheduleListCopy.add(new ScheduleGetterSetter(nam,desc,tme,scr ));
                    //Log.e("Value","Name: " + nam);
                    //Log.e("Value","Description: " + desc);
                    //Log.e("Value","Time: " + tme);
                    adapterCopy.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error","Retrieving data.");
            }
        });

        //dismiss the progress dialog
        progressDialog.dismiss();

        //Getting the calculated score intent from questions activity
        String  cal_score = getIntent().getStringExtra("calculated_score");
        String card_Name = getIntent().getStringExtra("card_name");

        //Toast.makeText(this,"Card name is " + card_Name,Toast.LENGTH_SHORT).show();

        //Set the score to card
        if(card_Name != null)
            setScore(card_Name,cal_score);

    }
    private void setScore(final String card_Name, final String cal_score) {

        Log.e("TAG","Entered in setScore method");

        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();

                    String nam = (String) map.get("Name");
                    String key = (String) map.get("Key");
                    Log.e("Key: ",key);
                    Log.e("Name: ",nam);

                    if (card_Name.equals(nam)){

                        Log.e("TAG","Entered the if conditon in set score method");
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Student Cards");

                        map.put("Score",cal_score);
                        reference.child(key).updateChildren(map);

                        updateScoreInLogin(cal_score,nam);

                        break;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        startActivity(new Intent(this,ShivaniSinghCopy.class));

    }

    //Method to update score in the login field also
    private void updateScoreInLogin(final String cal_score, final String cardname) {

        Log.e("updateScoreInLogin","Entered the update score in login method to update the login child score");

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Login");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds1:dataSnapshot.getChildren()){

                    Map<String ,Object> map1 = (Map<String, Object>) ds1.getValue();

                    String usrnme = (String) map1.get("Username");
                    String key2 = (String) map1.get("key");

                    if (cardname.equals(usrnme)){

                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Login");

                        map1.put("Score",cal_score);

                        reference2.child(key2).updateChildren(map1);

                        Log.e("TAG","Score updated in the login child");

                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //Preventing the back action
        moveTaskToBack(false);
    }
}
