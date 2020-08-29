package com.hdvision.gadleaderboard.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hdvision.gadleaderboard.R;
import com.hdvision.gadleaderboard.model.Learner;
import com.hdvision.gadleaderboard.screens.MainScreen;
import com.hdvision.gadleaderboard.utils.LearnerRecycleAdapter;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements MainScreen {

    private static final String TAG = PlaceholderFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "section_number";

    private LearnerViewModel mLearnerViewModel;

    public static final int TAB_LEARNER_HOURS = 1;
    public static final int TAB_SKILL_IQ = 2;
    private final Observer<List<Learner>> hoursDataObserver = learnerHoursModels -> updateData(learnerHoursModels);
    private final Observer<List<Learner>> skillIQDataObserver = learnerSkillIQModels -> updateData(learnerSkillIQModels);
    private final Observer<String> errorObserver = errorMsg -> setError(errorMsg);
    private int mPositionTab;
    private RecyclerView mRecyclerView;
    private LearnerRecycleAdapter mAdapter;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLearnerViewModel = ViewModelProviders.of(this).get(LearnerViewModel.class);


        mPositionTab = 1;
        if (getArguments() != null) {
            mPositionTab = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        mLearnerViewModel.setIndex(mPositionTab);

        Log.d(TAG, "Current tab position: "+ mPositionTab);

            mLearnerViewModel.fetchData();
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = root.findViewById(R.id.list_items);
        //Todo:: use SwipeRefreshLayout to refresh layout

        mAdapter = new LearnerRecycleAdapter(this.getContext(), mPositionTab);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mLearnerViewModel.getLearnerHoursData().observe(getViewLifecycleOwner(), hoursDataObserver);
        mLearnerViewModel.getLearnerSkillIQData().observe(getViewLifecycleOwner(), skillIQDataObserver);
        mLearnerViewModel.getErrorUpdates().observe(getViewLifecycleOwner(), errorObserver);
        return root;
    }

    @Override
    public void updateData(List<Learner> data) {
        mAdapter.setItems(data);
    }

    @Override
    public void setError(String msg) {
        showErrorToast(msg);
    }

    private void showErrorToast(String error) {
        Toast.makeText(this.getContext(), "Error:" + error, Toast.LENGTH_LONG).show();
    }

}