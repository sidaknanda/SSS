package com.android.sss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by OPTIMUSDOM ubuntu151 on 26/10/15.
 */
public class ReportBugFragment extends Fragment {

    private View fragmentView;
    private EditText et_name, et_contact, et_feedback;
    FloatingActionButton fab_reportBug;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_report_bug, container, false);
        initSetup();
        return fragmentView;
    }

    private void initSetup() {
        et_name = (EditText) fragmentView.findViewById(R.id.editTextName);
        et_contact = (EditText) fragmentView.findViewById(R.id.editTextContactNo);
        et_feedback = (EditText) fragmentView.findViewById(R.id.editTextFeedback);
        fab_reportBug = (FloatingActionButton) fragmentView.findViewById(R.id.fab_reportBug);
        et_name.setText(Utils.getLoggedInUserStudents().get(Utils.Numbers.ZERO.ordinal()).getFatherName());
        et_contact.setText(Utils.getLoggedInUserStudents().get(Utils.Numbers.ZERO.ordinal()).getContactNo1());
        fab_reportBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_name.getText().toString().equals("") && !et_contact.getText().toString().equals("") && !et_feedback.getText().toString().equals("")) {
                    //add code for saving feedback to web or DB
                    Toast.makeText(getActivity(), getString(R.string.thanks_feedback_message), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), DashboardActivity.class));
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.all_fields_required), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
