package com.example.tanzeem.internity.StudentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanzeem.internity.InterviewerListActivity.InterviewerList;
import com.example.tanzeem.internity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText ffEmail,ffName,ffStream,ffLearned,ffExpectations,ffAdded,ffRemoved,ffBest;

    Button student_feedback_form_button;

    ProgressDialog progressDialog;

    Spinner spinner1,spinner2,spinner3,spinner4,spinner5;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        //Setting the custom toolbar
        Toolbar toolbar = findViewById(R.id.feedback_toolbar);
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

        //Setting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback Form");

        //Setting the reference of all variables
        progressDialog = new ProgressDialog ( this );
        ffEmail = findViewById ( R.id.emailaddress );
        ffName = findViewById ( R.id.fName );
        ffStream= findViewById ( R.id.stream );
        ffLearned = findViewById ( R.id.info);
        ffExpectations = findViewById ( R.id.expectation );
        ffAdded = findViewById ( R.id.added );
        ffRemoved = findViewById ( R.id.removed );
        ffBest = findViewById ( R.id.best );

        student_feedback_form_button = findViewById(R.id.student_feedback_form_button);

        student_feedback_form_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Submitting the form.....");
                progressDialog.show();

                feedback_to_database();

                progressDialog.dismiss();

            }
        });


        //Setting spinner 1
        spinner1 =  findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.rate_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        //Setting spinner 2
        spinner2 =  findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.rate_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        //Setting spinner 3
        spinner3 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.rate_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener( this);

        //Setting spinner 4
        spinner4 =  findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,R.array.rate_array, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setOnItemSelectedListener(this);

        //Setting spinner5
        spinner5 =  findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,R.array.rate_array, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        spinner5.setOnItemSelectedListener(this);


    }

    private void feedback_to_database() {
        Log.e("TAG","Entered the feedback to database method");

        //Getting all the data from their references
        String ff_email = ffEmail.getText().toString();
        String ff_name = ffName.getText().toString();
        String ff_stream = ffStream.getText().toString();
        String ff_learned = ffLearned.getText().toString();
        String ff_expectations = ffExpectations.getText().toString();
        String ff_added = ffAdded.getText().toString();
        String ff_removed = ffRemoved.getText().toString();
        String ff_best = ffBest.getText().toString();
        String spinner_val1 = spinner1.getSelectedItem().toString();
        String spinner_val2 = spinner2.getSelectedItem().toString();
        String spinner_val3 = spinner3.getSelectedItem().toString();
        String spinner_val4 = spinner4.getSelectedItem().toString();
        String spinner_val5 = spinner5.getSelectedItem().toString();

        Log.e("Spinner values","spinner1 : " + spinner_val1 + "\n" +
                "spinner2 : " + spinner_val2 + "\n" +
                "spinner3 : " + spinner_val3 + "\n" +
                "spinner4 : " + spinner_val4 + "\n" +
                "spinner5 : " + spinner_val5 );

        if (TextUtils.isEmpty(ff_email) || TextUtils.isEmpty(ff_name) || TextUtils.isEmpty(ff_stream) ||
                TextUtils.isEmpty(ff_learned) || TextUtils.isEmpty(ff_expectations) || TextUtils.isEmpty(ff_added) ||
                TextUtils.isEmpty(ff_removed) || TextUtils.isEmpty(ff_best)){

            Toast.makeText(this,"Enter all fields",Toast.LENGTH_SHORT).show();
        }
        else{

            DatabaseReference reference = databaseReference.push();

            String key = reference.getKey();

            reference.child("Email").setValue(ff_email);
            reference.child("Name").setValue(ff_name);
            reference.child("Stream").setValue(ff_stream);
            reference.child("Learned").setValue(ff_learned);
            reference.child("Expectation").setValue(ff_expectations);
            reference.child("Add").setValue(ff_added);
            reference.child("Remove").setValue(ff_removed);
            reference.child("Best").setValue(ff_best);
            reference.child("RateLearning").setValue(spinner_val1);
            reference.child("RateActiviness").setValue(spinner_val2);
            reference.child("RateTask").setValue(spinner_val3);
            reference.child("RateAttendence").setValue(spinner_val4);
            reference.child("RateCoach").setValue(spinner_val5);
            reference.child("Key").setValue(key);

            startActivity(new Intent(FeedbackForm.this,StudentDashboard.class));

            Toast.makeText(getApplication(),"Feedback Form submitted sucessfully",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
    @Override
    public void onBackPressed() {
        //preventing back action button
        moveTaskToBack(false);
    }
    */
}
