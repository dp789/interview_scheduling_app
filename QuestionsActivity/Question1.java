package com.example.tanzeem.internity.QuestionsActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tanzeem.internity.R;

public class Question1 extends Fragment implements View.OnClickListener{

    Button nextButton;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5;
    RadioGroup radioGroup;

    private int value;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.question_one_fragment,container,false);

        Log.e("Question 1","Question 1 fragment changed.");

        //setting the reference
        nextButton = view.findViewById(R.id.next_button1);
        radioButton1 = view.findViewById(R.id.radiobutton_option1);
        radioButton2 = view.findViewById(R.id.radiobutton_option2);
        radioButton3 = view.findViewById(R.id.radiobutton_option3);
        radioButton4 = view.findViewById(R.id.radiobutton_option4);
        radioButton5 = view.findViewById(R.id.radiobutton_option5);
        radioGroup = view.findViewById(R.id.radio_group1);

        radioButton1.setChecked(true);
        value = 1;

        //Setting the on click listener on radio buttons
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
        radioButton5.setOnClickListener(this);



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Declaring a shared preferences object with file name
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

                //Making an editore object to edit the shared prefrences file
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Log.e("Value 1 is : ", String.valueOf(value));
                //Save the checked radio button value to file with a key value
                editor.putInt("Answer1", value);
                editor.commit();

                //Toast.makeText(getActivity(),"Selected value is" + value,Toast.LENGTH_SHORT).show();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Question2());
                fragmentTransaction.commit();
            }
        });


        return  view;
    }

    @Override
    public void onClick(View v) {

        boolean checked  = ((RadioButton) v).isChecked();

        switch (v.getId()) {

            case R.id.radiobutton_option1:
                if (checked) {
                    value = 1;
                    //Toast.makeText(getActivity(), "Option 1 clicked", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radiobutton_option2:
                if (checked) {
                    value = 2;
                    //Toast.makeText(getActivity(), "Option 2 clicked", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radiobutton_option3:
                if (checked){
                    value = 3;
                    //Toast.makeText(getActivity(), "Option 3 clicked", Toast.LENGTH_SHORT).show();
                }break;
            case R.id.radiobutton_option4:
                if (checked){
                    value = 4;
                    //Toast.makeText(getActivity(), "Option 4 clicked", Toast.LENGTH_SHORT).show();
                }break;
            case R.id.radiobutton_option5:
                if (checked){
                    value = 5;
                    //Toast.makeText(getActivity(), "Option 4 clicked", Toast.LENGTH_SHORT).show();
                }break;
        }
    }


}
