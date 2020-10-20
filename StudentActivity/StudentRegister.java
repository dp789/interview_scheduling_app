package com.example.tanzeem.internity.StudentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanzeem.internity.LoginActivity.LoginActivity;
import com.example.tanzeem.internity.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegister extends AppCompatActivity implements View.OnClickListener{

    Button student_registeration_form_button;
    DatabaseReference databaseReference,databaseReference1;
    EditText registerFirstName,registerLastName,registerColg,registerGender,registerEmail,registerPassword,registerPassingYear,
                registerTech,registerPhone,registerWhy,registerHow;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        progressDialog = new ProgressDialog(this);

        //Setting the reference
        registerFirstName = findViewById(R.id.register_firstName);
        registerLastName = findViewById(R.id.register_lastName);
        registerColg = findViewById(R.id.register_colg);
        registerGender = findViewById(R.id.register_gender);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        registerPassingYear = findViewById(R.id.register_passingYear);
        registerTech = findViewById(R.id.register_tech);
        registerPhone = findViewById(R.id.register_phone);
        registerWhy = findViewById(R.id.register_why);
        registerHow = findViewById(R.id.register_how);
        student_registeration_form_button = findViewById(R.id.student_registeration_form_button);

        //Setting on click listener
        student_registeration_form_button.setOnClickListener(this);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student Register");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Login");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.student_registeration_form_button:
                //Save the registration info to database
                progressDialog.setMessage("Registering the student....");
                progressDialog.show();
                saveToDatabase();
                progressDialog.dismiss();

                break;
        }
    }

    //Saving registration info to the database
    private void saveToDatabase() {

        String firstName_val = registerFirstName.getText().toString();
        String lastName_val = registerLastName.getText().toString();
        String colg_val = registerColg.getText().toString();
        String gener_val = registerGender.getText().toString();
        String email_val = registerEmail.getText().toString();
        String pass_val = registerPassword.getText().toString();
        String passYear_val = registerPassingYear.getText().toString();
        String tech_val = registerTech.getText().toString();
        String phone_val = registerPhone.getText().toString();
        String why_val = registerWhy.getText().toString();
        String how_val = registerHow.getText().toString();

        //Checking whether all field are filled or not
        if (!TextUtils.isEmpty(firstName_val) && !TextUtils.isEmpty(lastName_val) && !TextUtils.isEmpty(colg_val) &&
                !TextUtils.isEmpty(gener_val) && !TextUtils.isEmpty(email_val) && !TextUtils.isEmpty(pass_val) &&
                !TextUtils.isEmpty(passYear_val) && !TextUtils.isEmpty(tech_val) && !TextUtils.isEmpty(phone_val) &&
                !TextUtils.isEmpty(why_val) && !TextUtils.isEmpty(how_val)){

            //Saving data to Registration  child
            DatabaseReference newRef1 = databaseReference.push();

            String key1 = newRef1.getKey();

            newRef1.child("FirstName").setValue(firstName_val);
            newRef1.child("LastName").setValue(lastName_val);
            newRef1.child("College").setValue(colg_val);
            newRef1.child("Gender").setValue(gener_val);
            newRef1.child("Email").setValue(email_val);
            newRef1.child("Password").setValue(pass_val);
            newRef1.child("PassingYear").setValue(passYear_val);
            newRef1.child("Tech").setValue(tech_val);
            newRef1.child("Phone").setValue(phone_val);
            newRef1.child("Why").setValue(why_val);
            newRef1.child("How").setValue(how_val);
            newRef1.child("Key").setValue(key1);

            //Saving data to Login child to generate username and password
            DatabaseReference newRef2 = databaseReference1.push();

            String key2 = newRef2.getKey();

            newRef2.child("Username").setValue(firstName_val + "_" + lastName_val);
            newRef2.child("Password").setValue(pass_val);
            newRef2.child("Type").setValue("type3");
            newRef2.child("key").setValue(key2);
            newRef2.child("Score").setValue("0");
            newRef2.child("Email").setValue(email_val);
            newRef2.child("Text").setValue("Interview Schedule to be Updated");

            //Toast.makeText(this,"Username : " + firstName_val + "_" + lastName_val,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

            Toast.makeText(this,"Registration Successful" + "\n" +
                    "Username : " + firstName_val + "_" + lastName_val,Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(this,"Some fields are left blank",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        //preventing back button action
        moveTaskToBack(false);
    }
}
