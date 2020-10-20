package com.example.tanzeem.internity.SchedulesActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tanzeem.internity.InterviewerListActivity.InterviewerList;
import com.example.tanzeem.internity.NotificationHelper;
import com.example.tanzeem.internity.QuestionsActivity.Questions;
import com.example.tanzeem.internity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class  ShivaniSingh extends AppCompatActivity implements View.OnClickListener{

    FloatingActionButton fab1,fab2;
    EditText postName,postDescription,postTime,removeName;

    NotificationHelper helper;

    private ProgressDialog progressDialog,progressDialog1,progressDialog2;

    private DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3;

    private View dialogView,dialogView1;

    private RecyclerView mSchedulRecyclerView;
    List<ScheduleGetterSetter> mScheduleList;
    ScheduleRecyclerView adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shivani_singh);
        Log.e("Shivani Singh","Entered on Create method.");


        Toolbar toolbar = findViewById(R.id.shivani_singh_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), InterviewerList.class));
                    finish();
                }
            });
        }
        //if(getSupportActionBar() != null){
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //    getSupportActionBar().setHomeButtonEnabled(true); }


        //Setting reference to the recycler view
        //mScheduleList = findViewById(R.id.schedule_recylerview);
        //mScheduleList.setHasFixedSize(true);
        //mScheduleList.setLayoutManager(new LinearLayoutManager(this));


        //Setting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student Cards");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Student Cards");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Student Cards");

        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setMessage("Loading the cards.....");
        progressDialog1.show();
        //Initializing the list
        mScheduleList = new ArrayList<>();

        //setting the reference of recycler view and adapter
        mSchedulRecyclerView = findViewById(R.id.schedule_recylerview);
        adapter = new ScheduleRecyclerView(this,mScheduleList);

        //set the layout to linear
        mSchedulRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //set Adapter
        mSchedulRecyclerView.setAdapter(adapter);

        //Adding the values to recycler view
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    Map<String, String> map = (Map<String, String>) ds.getValue();

                    String nam = map.get("Name");
                    String desc = map.get("Description");
                    String tme = map.get("Time");
                    String scr = map.get("Score");

                    //Addint the data to recycler view
                    mScheduleList.add(new ScheduleGetterSetter(nam,desc,tme,scr));
                    //Log.e("Value","Name: " + nam);
                    //Log.e("Value","Description: " + desc);
                    //Log.e("Value","Time: " + tme);
                    //Log.e("Value","Score: " + scr);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error","Retrieving data.");
            }
        });

        progressDialog1.dismiss();


        //setting the new progress bar
        progressDialog = new ProgressDialog(this);
        progressDialog2 = new ProgressDialog(this);



        //Giving the reference to floating action buttons
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        //setting the on click listener
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

    }



    //Method when the a button is clicked
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.fab1:
                //Toast.makeText(this,"Insert Button",Toast.LENGTH_SHORT).show();
                insertCardDialog();
                break;

            case R.id.fab2:
                //Toast.makeText(this,"Delete Button",Toast.LENGTH_SHORT).show();
                removeCardDialog();
                break;
        }
    }

    //To add any students card
    public void insertCardDialog(){

        Log.e("insertCardDialog","Enter the dialog creating method");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Get the layout inflator
        LayoutInflater inflater = this.getLayoutInflater();

        //inflating the view
        dialogView = inflater.inflate(R.layout.insert_dialog,null);
        //bulding the components
        builder.setView(dialogView)
                //Setting the title of dialog box
                .setTitle("Insert Student Card")
                //Add action buttons
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.O)
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.e("Entered the","create card method");
                        //Setting the progress dialog message
                        progressDialog.setMessage("Creating the card......");
                        progressDialog.show();
                        //Inserting card data to the database
                        //startCreating();
                        startCreating();

                        //Creating a notification on every card created
                        //Notification.Builder builder = helper.getChannelNotification();
                        //helper.getManager().notify(new Random().nextInt(),builder.build());

                        //dismiss the progress dialog
                        progressDialog.dismiss();

                        //refreshing the current activity
                        //Intent intentRefresh = getIntent();
                        //finish();
                        //startActivity(intentRefresh);

                        startActivity(new Intent(getApplicationContext(),ShivaniSingh.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Toast.makeText(ShivaniSingh.this,"Card removed",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

       builder.show();
    }

    //Adding data to the database
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startCreating() {

        Log.e("startCreating","Entered the start Creating methos");
        //Giving reference to dialog box texts to insert the data in the database
        postName = dialogView.findViewById(R.id.student_name_dialog_text);
        postDescription = dialogView.findViewById(R.id.student_description_dialog_text);
        postTime = dialogView.findViewById(R.id.time);

        /*
        //Creating a calender instance
         calendar = Calendar.getInstance();

        //Set the calender instance to the hour and minute we picked in time picker
        //calendar.set(Calendar.HOUR_OF_DAY,postTime.getHour());
        //calendar.set(Calendar.MINUTE,postTime.getMinute());

        //Get the int value of hour and convert it into string
        int hour = postTime.getHour();
        int minute = postTime.getMinute();
        String hour_string = String.valueOf(hour);
        String minute_string = String .valueOf(minute);

        //Convert 24 hour format to 12 hour format
        if (hour > 12)
            hour_string = String.valueOf(hour - 12);
        if (minute < 10)
            minute_string = "0" + String.valueOf(minute);

        //Setting the time string
        time = hour_string + ":" + minute_string;
        */

        //Setting the name string and description string
        String name_val = postName.getText().toString();
        String description_val = postDescription.getText().toString();
        String time_val = postTime.getText().toString();


        //Checking whether the strings are empty or not
        if (!TextUtils.isEmpty(name_val) && !TextUtils.isEmpty(description_val)){

            DatabaseReference newReference = databaseReference.push();

            String key = newReference.getKey();

            newReference.child("Name").setValue(name_val);
            newReference.child("Description").setValue(description_val);
            newReference.child("Time").setValue(time_val);
            newReference.child("Key").setValue(key);
            newReference.child("Score").setValue("Not Calculated");

            updateTimeInLogin(time_val,name_val);

            Toast.makeText(ShivaniSingh.this,"Card inserted successfully",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Enter the values first, card not created!!!",Toast.LENGTH_SHORT).show();

    }

    //Updating the time in student profile
    private void updateTimeInLogin(final String timeVal, final String nameVal) {

        Log.e("updateTimeInLogin","Entered the update time in login method to update the login child text");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Login");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    Map<String, Object> map = (Map<String, Object>) ds.getValue();

                    String usrnme = (String) map.get("Username");
                    String key = (String) map.get("key");

                    if (nameVal.equals(usrnme)){

                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Login");

                        map.put("Text","Your Interview is scheduled at " + timeVal);

                        reference1.child(key).updateChildren(map);

                        Log.e("TAG","Time updated in the login child");

                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //To remove any students card
    public void removeCardDialog(){
        Log.e("removeCardDialog","Enter the dialog removing method");

        AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);

        LayoutInflater mInflater = this.getLayoutInflater();

        dialogView1 = mInflater.inflate(R.layout.remove_dialog,null);

        mbuilder.setView(dialogView1)
                //Set title of dialog box
                .setTitle("Remove Student Card")
                //Add action buttons
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.e("Entered the","remove card method");
                        //Setting the progress dialog message
                        progressDialog2.setMessage("Removing the card......");
                        progressDialog2.show();
                        //Inserting card data to the database
                        //startCreating();
                        startRemoving();

                        //dismiss the progress dialog
                        progressDialog2.dismiss();

                        //refreshing the current activity
                        Intent intentRefresh = getIntent();
                        finish();
                        startActivity(intentRefresh);

                        Toast.makeText(ShivaniSingh.this,"Card Removed Successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
        mbuilder.show();
    }

    private void startRemoving() {
        Log.e("startRemoving","Entered the start Removing methos");
        //Creating the reference
        removeName = dialogView1.findViewById(R.id.remove_student_name_dialog_text);
        final String removeText = removeName.getText().toString();


        //Checking whether the strings are empty or not
        if (!TextUtils.isEmpty(removeText)){

            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()) {

                        Map<String, String> map = (Map<String, String>) ds.getValue();

                        String nam = map.get("Name");
                        if (nam.equals(removeText)) {
                            ds.getRef().removeValue();
                            break;
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Error","Removing data.");
                }
            });

            //Removing the key value
            //databaseReference2.child(key).removeValue();
        }
        else
            Toast.makeText(getApplicationContext(),"Enter the values first, card not created!!!",Toast.LENGTH_SHORT).show();

    }

    /*
    @Override
    public void onBackPressed() {
        //preventing back action button
        moveTaskToBack(true);
    }
    */


    public class NotificationHelper extends ContextWrapper {
        private static final String CHANNEL_ID = "com.example.tanzeem.internity.SchedulesActivity";
        private static final String CHANNEL_NAME = "Tanzeem Channel";
        private NotificationManager manager;

        public NotificationHelper(Context base) {
            super(base);
            createChannels();
        }

        private void createChannels(){

            NotificationChannel newChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                newChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                newChannel.enableLights(true);
                newChannel.enableVibration(true);
                newChannel.setLightColor(Color.GREEN);
                newChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

                getManager().createNotificationChannel(newChannel);
            }

        }

        public NotificationManager getManager() {
            if(manager == null)
                manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            return manager;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public Notification.Builder getChannelNotification() {
            return new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                    .setContentText("New card is created")
                    .setContentTitle("Card Notification")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true);
        }
    }
}
