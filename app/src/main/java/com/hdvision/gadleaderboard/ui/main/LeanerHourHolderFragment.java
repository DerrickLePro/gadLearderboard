package com.hdvision.gadleaderboard.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hdvision.gadleaderboard.R;
import com.hdvision.gadleaderboard.model.Learner;
import com.hdvision.gadleaderboard.utils.LearnerRecycleAdapter;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeanerHourHolderFragment extends Fragment {

    private static final String TAG = LeanerHourHolderFragment.class.getSimpleName();

    private LearnerViewModel mLearnerViewModel;

    private RecyclerView mRecyclerView;
    private LearnerRecycleAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLearnerViewModel = ViewModelProviders.of(this).get(LearnerViewModel.class);
        mLearnerViewModel.fetchData();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = root.findViewById(R.id.list_items);

        Observer<List<Learner>> hoursDataObserver = this::updateData;
        Observer<String> errorObserver = this::setError;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new LearnerRecycleAdapter(this.getContext());
        mLearnerViewModel.getLearnerHoursData().observe(getViewLifecycleOwner(), hoursDataObserver);
        mLearnerViewModel.getErrorUpdates().observe(getViewLifecycleOwner(), errorObserver);
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    public void updateData(List<Learner> data) {
        mAdapter.setItems(data);
    }


    public void setError(String msg) {
        showErrorToast(msg);
    }

    private void showErrorToast(String error) {
        Toast.makeText(this.getContext(), "Error:" + error, Toast.LENGTH_LONG).show();
    }

}