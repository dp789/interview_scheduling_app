package com.example.tanzeem.internity.SchedulesActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanzeem.internity.QuestionsActivity.Questions;
import com.example.tanzeem.internity.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ScheduleRecyclerView extends RecyclerView.Adapter<ScheduleRecyclerView.MyViewHolder>{

    private Context scheduleContext;
    private List<ScheduleGetterSetter> scheduleData;

    //private DatabaseReference databaseReference;

    View view;

    public ScheduleRecyclerView(Context scheduleContext, List<ScheduleGetterSetter> scheduleData) {
        this.scheduleContext = scheduleContext;
        this.scheduleData = scheduleData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(scheduleContext);
        view = layoutInflater.inflate(R.layout.schedules_recycler_row,parent,false);
        return new ScheduleRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        //Binding the data from recycler view with the view holder
        holder.nameText.setText(scheduleData.get(position).getName());
        holder.descriptionText.setText(scheduleData.get(position).getDescription());
        holder.timeText.setText(scheduleData.get(position).getTime());
        holder.calculatedScoreText.setText(scheduleData.get(position).getScore());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(scheduleContext,scheduleData.get(position).getName() +"'s card clicked",Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = scheduleContext.getSharedPreferences("UserInfo",
                        Context.MODE_PRIVATE);
                String user_type = sharedPreferences.getString("UserType","");

                if(!user_type.equals("type1")) {
                    Intent intent = new Intent(scheduleContext, Questions.class);
                    intent.putExtra("card_name", scheduleData.get(position).getName());
                    scheduleContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameText,descriptionText,timeText,calculatedScoreText;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textview_name);
            descriptionText = itemView.findViewById(R.id.textview_description);
            timeText = itemView.findViewById(R.id.textview_time);
            calculatedScoreText = itemView.findViewById(R.id.Calculated_Score);
        }
    }
}
