package com.hdvision.gadleaderboard.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.hdvision.gadleaderboard.utils.LearnerRecycleAdapter;
import com.hdvision.gadleaderboard.utils.NetworkUtils;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeanerHourHolderFragment extends Fragment {

    private static final String TAG = LeanerHourHolderFragment.class.getSimpleName();

    private LearnerViewModel mLearnerViewModel;


    private RecyclerView mRecyclerView;
    private LearnerRecycleAdapter mAdapter;

    private SwipeRefreshLayout mRefreshLayout;
    private final static int DATA_FETCHING_INTERVAL = 5 * 1000; //
    private long mLastFetchedDataTimeStamp;
    private AsyncTask<Void, Void, Void> mTaskRefresh;
    private NetworkUtils mNetworkUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLearnerViewModel = ViewModelProviders.of(this).get(LearnerViewModel.class);
        mLearnerViewModel.fetchData();

        mNetworkUtils = new NetworkUtils(getContext());

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = root.findViewById(R.id.list_items);
        mRefreshLayout = root.findViewById(R.id.swipeToRefresh);

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
        mLastFetchedDataTimeStamp = System.currentTimeMillis();
    }


    public void setError(String msg) {
        if (!mNetworkUtils.isInternetAvailable(1000)) {
            showErrorToast("Not Internet");
        } else
            showErrorToast(msg);
    }

    private void showErrorToast(String error) {
        Toast.makeText(this.getContext(), "Error:" + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            mTaskRefresh.execute();
        } catch (IllegalStateException e) {
            Log.d(TAG, e.getMessage());
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        try {
            mTaskRefresh.execute();
        } catch (IllegalStateException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mTaskRefresh.cancel(true);
    }
}