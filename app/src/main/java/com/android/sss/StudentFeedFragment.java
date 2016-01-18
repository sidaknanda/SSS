package com.android.sss;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;

import java.util.ArrayList;


public class StudentFeedFragment extends Fragment {

    private StudentModel selectedStudent;
    private TextView tv_noData;
    private TextView tv_details;
    private RecyclerView rv_feedList;
    private ProgressDialog dialog;
    private View fragmentView;
    private VolleySingleton volleySingleton = VolleySingleton.getInstance();
    private ArrayList<StudentFeedModel> studentFeeds = new ArrayList();
    private StudentFeedAdapter studentFeedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        selectedStudent = this.getArguments().getParcelable(Utils.PARAM_SELECTED_STUDENT);
        fragmentView = inflater.inflate(R.layout.fragment_student_feed, container, false);
        init();
        setupRecyclerView();
        getStudentFeed();
        return fragmentView;
    }

    private void init() {
        tv_noData = (TextView) fragmentView.findViewById(R.id.textViewNoData);
        tv_noData.setVisibility(View.GONE);
        tv_details = (TextView) fragmentView.findViewById(R.id.textViewStudentDetails);
        tv_details.setText(selectedStudent.getStudentName() + "\n" + selectedStudent.getStudentClass() + "\n" + selectedStudent.getAdmissionNo());
        dialog = Utils.getProgressDialog(getActivity());
    }

    private void getStudentFeed() {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog.show();
            RequestQueue requestQueue = volleySingleton.getRequestQueue();
            StringRequest request = new StringRequest(Utils.getStudentUpdatesUrl(selectedStudent.getRFID()), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray feed = new JSONArray(response);
                        studentFeeds = new Gson().fromJson(feed.toString(), new TypeToken<ArrayList<StudentFeedModel>>() {
                        }.getType());
                        setStudentFeedToList();
                        dialog.dismiss();
                    } catch (Exception e) {
                        tv_noData.setVisibility(View.VISIBLE);
                        e.printStackTrace();
                        dialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.server_issue), Toast.LENGTH_SHORT).show();
                    tv_noData.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    1,
                    1));
            requestQueue.add(request);
            dismissSwipeToRefresh();
        } else {
            Toast.makeText(getActivity(), getString(R.string.internet_issue), Toast.LENGTH_SHORT).show();
            dismissSwipeToRefresh();
        }

    }

    private void dismissSwipeToRefresh() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void setStudentFeedToList() {
        studentFeedAdapter = new StudentFeedAdapter(getActivity(), studentFeeds);
        rv_feedList.setAdapter(studentFeedAdapter);
    }

    private void setupRecyclerView() {
        rv_feedList = (RecyclerView) fragmentView.findViewById(R.id.recyclerViewStudentFeed);
        rv_feedList.setHasFixedSize(true);
        rv_feedList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_feedList.setItemAnimator(new DefaultItemAnimator());
        rv_feedList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        swipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStudentFeed();
            }
        });
    }
}