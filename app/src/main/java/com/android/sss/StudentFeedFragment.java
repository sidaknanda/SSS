package com.android.sss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by OPTIMUSDOM ubuntu151 on 8/10/15.
 */
public class StudentFeedFragment extends Fragment {

    private int position;// expandable listview click student position

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_feed, container, false);
        return view;
    }
}