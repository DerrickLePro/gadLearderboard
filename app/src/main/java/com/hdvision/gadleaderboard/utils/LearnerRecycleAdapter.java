package com.hdvision.gadleaderboard.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hdvision.gadleaderboard.R;
import com.hdvision.gadleaderboard.model.Learner;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by derrick.kaffo on 29/08/2020.
 * kaffoderrick@gmail.com
 */
public class LearnerRecycleAdapter extends RecyclerView.Adapter<LearnerRecycleAdapter.ViewHolder> {

    List<Learner> mItems = new ArrayList<>();
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public LearnerRecycleAdapter(Context context) {

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Learner learner = mItems.get(position);

        String description = learner.getHours() + " " + mContext.getString(R.string.learning_hours) + ", " + learner.getCountry();

        holder.mTextName.setText(learner.getName());
        holder.mTextDescription.setText(description);

        ImageLoader.getInstance().displayImage(learner.getBadgeUrl(), holder.mImageBag);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<Learner> items) {
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextName;
        private final TextView mTextDescription;
        private final ImageView mImageBag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.name_text);
            mTextDescription = itemView.findViewById(R.id.description_text);
            mImageBag = itemView.findViewById(R.id.badgeImage);

        }
    }
}
