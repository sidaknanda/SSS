package com.android.sss;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by OPTIMUSDOM ubuntu151 on 13/10/15.
 */
public class StudentFeedAdapter extends RecyclerView.Adapter<StudentFeedAdapter.StudentFeedViewHolder> {

    private Context context;
    private ArrayList<StudentFeedModel> studentFeeds;

    public StudentFeedAdapter(Context context, ArrayList<StudentFeedModel> studentFeeds) {
        this.context = context;
        this.studentFeeds = studentFeeds;
        Collections.reverse(studentFeeds);
    }

    @Override
    public StudentFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_studentfeed, parent, false);
        return new StudentFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentFeedViewHolder holder, int position) {
        holder.textViewStatus.setText(Utils.STUDENT_STATUS[studentFeeds.get(position).getFlag()-1]);
        holder.textViewDate.setText(studentFeeds.get(position).getDate());
        holder.textViewTime.setText(studentFeeds.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return studentFeeds.size();
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class StudentFeedViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStatus;
        TextView textViewDate;
        TextView textViewTime;

        public StudentFeedViewHolder(View itemView) {

            super(itemView);
            textViewStatus = (TextView) itemView.findViewById(R.id.textViewStatus);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);

        }
    }
}
