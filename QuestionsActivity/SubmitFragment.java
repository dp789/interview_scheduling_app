package com.example.tanzeem.internity.QuestionsActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tanzeem.internity.R;
import com.example.tanzeem.internity.SchedulesActivity.ShivaniSingh;
import com.example.tanzeem.internity.SchedulesActivity.ShivaniSinghCopy;

public class SubmitFragment extends Fragment {

    Button prevButton,submitButton;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.submit_button_fragment,container,false);
        Log.e("Submit","Submit fragment changed.");



        prevButton = view.findViewById(R.id.prev_button2);
        submitButton = view.findViewById(R.id.submit_button);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,new Question2());
                fragmentTransaction.commit();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("TAG","Submit button of submit fragment is clicked.");

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

                int  Value1 = sharedPreferences.getInt("Answer1",0);
                int  Value2 = sharedPreferences.getInt("Answer2",0);
                String  cardName = sharedPreferences.getString("CardName","");

                Log.e("Buttons value ", Value1 + ", " + Value2);


                double calculated_score = ((Value1 + Value2)*100) / 10 ;

                Log.e("Buttons value ", String.valueOf(calculated_score));

                Intent intent = new Intent(getActivity(), ShivaniSinghCopy.class);
                intent.putExtra("calculated_score",String.valueOf(calculated_score));
                intent.putExtra("card_name",cardName);
                startActivity(intent);
            }
        });


        return view;
    }
}
