package com.hdvision.gadleaderboard.ui.main;

import android.os.AsyncTask;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hdvision.gadleaderboard.R;
import com.hdvision.gadleaderboard.model.Learner;
import com.hdvision.gadleaderboard.utils.LearnerSkillIQRecycleAdapter;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeanerSkillHolderFragment extends Fragment {

    private static final String TAG = LeanerSkillHolderFragment.class.getSimpleName();


    private LearnerSkillIQViewModel mLearnerViewModel;

    private RecyclerView mRecyclerView;
    private LearnerSkillIQRecycleAdapter mSkillIQAdapter;

    private SwipeRefreshLayout mRefreshLayout;
    private final static int DATA_FETCHING_INTERVAL = 5 * 1000; //
    private long mLastFetchedDataTimeStamp;
    private Executor mExecutor;
    private AsyncTask<Void, Void, Void> mTaskRefresh;


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

        mRefreshLayout = root.findViewById(R.id.swipeToRefresh);


        Observer<List<Learner>> hoursDataObserver = this::updateData;
        Observer<String> errorObserver = this::setError;


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mTaskRefresh = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mRefreshLayout.setOnRefreshListener(() -> {

                    mLearnerViewModel.fetchData();
                    mRefreshLayout.setRefreshing(false);

                });
                return null;
            }
        };

        mSkillIQAdapter = new LearnerSkillIQRecycleAdapter(this.getContext());
        mLearnerViewModel.getLearnerSkillIQData().observe(getViewLifecycleOwner(), hoursDataObserver);
        mLearnerViewModel.getErrorUpdates().observe(getViewLifecycleOwner(), errorObserver);
        mRecyclerView.setAdapter(mSkillIQAdapter);


        return root;
    }

    public void updateData(List<Learner> data) {
        mSkillIQAdapter.setItems(data);
        mLastFetchedDataTimeStamp = System.currentTimeMillis();
    }


    public void setError(String msg) {
        showErrorToast(msg);
    }

    private void showErrorToast(String error) {
        Toast.makeText(this.getContext(), "Error:" + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mTaskRefresh.execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        mTaskRefresh.cancel(true);
    }
}