package com.example.tanzeem.internity.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanzeem.internity.InterviewerListActivity.InterviewerList;
import com.example.tanzeem.internity.R;
import com.example.tanzeem.internity.SchedulesActivity.ShivaniSingh;
import com.example.tanzeem.internity.SchedulesActivity.ShivaniSinghCopy;
import com.example.tanzeem.internity.StudentActivity.StudentDashboard;
import com.example.tanzeem.internity.StudentActivity.StudentRegister;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginActivity extends Activity {

    EditText username,password;
    Button loginButton;
    TextView textView;
    Intent loginIntent;

    private DatabaseReference databaseReference;

    int f = 0;

    //String nam,pass,type;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.e("Login Activity","Entered the onCreate of Login Activity");

        //setting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Login");

        //Providing the reference to all variables
        username = findViewById(R.id.Login_username);
        password = findViewById(R.id.Login_password);
        loginButton = findViewById(R.id.Login_button);
        textView = findViewById(R.id.Student_register_text);

        //setting on click listener on login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String usernameText = username.getText().toString();
                final String passwordText = password.getText().toString();

                if (!TextUtils.isEmpty(usernameText) && !TextUtils.isEmpty(passwordText)) {

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                Map<String, String> map = (Map<String, String>) ds.getValue();

                                String nam = map.get("Username");
                                String pass = map.get("Password");
                                String type = map.get("Type");
                                String email = map.get("Email");


                                if (usernameText.equals(nam) && passwordText.equals(pass)) {
                                    f = 0;
                                    //Setting the intent according to type of user
                                    if (type.equals("type1")){
                                        SharedPreferences sharedPreferences  = getSharedPreferences("UserInfo",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("UserType","type1");
                                        editor.commit();

                                        startActivity(new Intent(getApplicationContext(), InterviewerList.class));
                                        //Toast.makeText(getApplicationContext(),"Type 1 user",Toast.LENGTH_SHORT).show();
                                    }
                                    else if (type.equals("type2")){
                                        startActivity(new Intent(getApplicationContext(), ShivaniSinghCopy.class));
                                        //Toast.makeText(getApplicationContext(),"Type 2 user",Toast.LENGTH_SHORT).show();
                                    }
                                    else if (type.equals("type3")){

                                        String scr = map.get("Score");
                                        String txt = map.get("Text");
                                        //Saving the login user info
                                        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("Username",nam);
                                        editor.putString("Email",email);
                                        editor.putString("Score",scr);
                                        editor.putString("Text",txt);
                                        editor.commit();

                                        startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
                                        //Toast.makeText(getApplicationContext(),"Type 3 user",Toast.LENGTH_SHORT).show();
                                    }
                                    Log.e("Type of user", " is: " + type);
                                    break;
                                }
                                else
                                    f = 1;

                            }
                            //Checking whether the user is registered or not
                            if (f == 1)
                                Toast.makeText(LoginActivity.this,"Invalid Username or Password!!!",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(LoginActivity.this,"Logged In Successfully",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //Log.e("Type of user is: ", " is: " + type);

                }
                else
                    Toast.makeText(getApplicationContext(),"Enter the fields first!!!",Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void StudentRegisterMethod(View view){

        //Toast.makeText(this,"Content to be Updated",Toast.LENGTH_SHORT).show();
        Log.e("StudentRegisterMethod","Student Registration Form Activity is called.");
        Intent intent = new Intent(this, StudentRegister.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
