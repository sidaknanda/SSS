package com.android.sss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by OPTIMUSDOM ubuntu151 on 8/10/15.
 */
public class StudentFeedFragment extends Fragment {

    private int position;// expandable listview click student position
    private StudentModel selectedStudent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        selectedStudent = this.getArguments().getParcelable(Utils.PARAM_SELECTED_STUDENT);
        View view = inflater.inflate(R.layout.fragment_student_feed, container, false);
        TextView tv_details = (TextView) view.findViewById(R.id.textViewStudentDetails);
        tv_details.setText(selectedStudent.getStudentName() + "\n" + selectedStudent.getStudentClass() + "\n" + selectedStudent.getAdmissionNo());
        return view;
    }
}