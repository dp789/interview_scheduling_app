package com.example.tanzeem.internity.InterviewerListActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.tanzeem.internity.R;

import java.util.List;

public class InterviewerListRecyclerView extends RecyclerView.Adapter<InterviewerListRecyclerView.MyViewHolder> {

    private Context interviewerContext;
    private List<InterviewerListGetterSetter> interviewerData;

    public InterviewerListRecyclerView(Context interviewerContext, List<InterviewerListGetterSetter> interviewerData) {
        this.interviewerContext = interviewerContext;
        this.interviewerData = interviewerData;
        Log.e("Recycler View","Entered in the Recycler View");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //Setting the layout item view to the recycler view
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(interviewerContext);
        view = layoutInflater.inflate(R.layout.interviewer_list_item,parent,false);
        return new InterviewerListRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InterviewerListRecyclerView.MyViewHolder myViewHolder, int position) {

        //Binding the data from recycler view with the view holder
        myViewHolder.nameText.setText(interviewerData.get(position).getName());
        myViewHolder.descriptionText.setText(interviewerData.get(position).getDescription());
        myViewHolder.thumbnailImage.setImageResource(interviewerData.get(position).getThumbnail());

    }

    @Override
    public int getItemCount() {
        return interviewerData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        TextView descriptionText;
        ImageView thumbnailImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            //Setting the variables reference
            nameText = itemView.findViewById(R.id.interviewer_name);
            descriptionText = itemView.findViewById(R.id.interviewer_description);
            thumbnailImage = itemView.findViewById(R.id.interviewer_photo);
        }
    }
}
