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
import com.hdvision.gadleaderboard.utils.LearnerSkillIQRecycleAdapter;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeanerSkillHolderFragment extends Fragment {

    private static final String TAG = LeanerSkillHolderFragment.class.getSimpleName();


    private LearnerSkillIQViewModel mLearnerViewModel;

    private RecyclerView mRecyclerView;
    private LearnerSkillIQRecycleAdapter mSkillIQAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLearnerViewModel = ViewModelProviders.of(this).get(LearnerSkillIQViewModel.class);
        mLearnerViewModel.fetchData();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_skill, container, false);
        mRecyclerView = root.findViewById(R.id.list_items2);

        Observer<List<Learner>> hoursDataObserver = this::updateData;
        Observer<String> errorObserver = this::setError;


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mSkillIQAdapter = new LearnerSkillIQRecycleAdapter(this.getContext());
        mLearnerViewModel.getLearnerSkillIQData().observe(getViewLifecycleOwner(), hoursDataObserver);
        mLearnerViewModel.getErrorUpdates().observe(getViewLifecycleOwner(), errorObserver);
        mRecyclerView.setAdapter(mSkillIQAdapter);


        return root;
    }

    public void updateData(List<Learner> data) {
        mSkillIQAdapter.setItems(data);
    }


    public void setError(String msg) {
        showErrorToast(msg);
    }

    private void showErrorToast(String error) {
        Toast.makeText(this.getContext(), "Error:" + error, Toast.LENGTH_LONG).show();
    }

}