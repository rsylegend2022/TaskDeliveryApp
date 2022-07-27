/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.recyclerview;

import com.example.android.RetrofitApi.POJO.Task;
import com.example.android.common.logger.Log;

import android.app.Application;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private static List<Task> mDataSet = new ArrayList<>();

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskName;
        //private final TextView taskDescription;
        private final TextView taskAddress;
        private final TextView taskType;
        private final TextView taskEmergency;
        private final ImageView itemImage;
        //private final TextView taskStatus;
        //private final TextView taskWorkload;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            taskName = (TextView) v.findViewById(R.id.textView);
            //taskDescription = (TextView) v.findViewById(R.id.Description);
            taskAddress = (TextView) v.findViewById(R.id.Address);
            taskType = (TextView) v.findViewById(R.id.Type);
            taskEmergency = (TextView) v.findViewById(R.id.Emergency);
            itemImage = (ImageView) v.findViewById(R.id.itemImage);
            //taskStatus = (TextView) v.findViewById(R.id.Status);
            //taskWorkload = (TextView) v.findViewById(R.id.Workload);
        }


        public void setData(Task taskHolder) {
            taskName.setText(taskHolder.name);
            //taskDescription.setText(taskHolder.description);
            taskAddress.setText(taskHolder.address);
            taskType.setText(taskHolder.type);
            if (taskHolder.type.equals("Water")) {
                itemImage.setImageDrawable(ContextCompat.getDrawable(itemImage.getContext(), R.drawable.ic_baseline_wash_24));
            } else if (taskHolder.type.equals("Plant")) {
                itemImage.setImageDrawable(ContextCompat.getDrawable(itemImage.getContext(), R.drawable.ic_baseline_grass_24));
            } else {
                itemImage.setImageDrawable(ContextCompat.getDrawable(itemImage.getContext(), R.drawable.ic_baseline_spa_24));
            }
            taskEmergency.setText(taskHolder.urgency);
            /*
            taskWorkload.setText(taskHolder.workload);
            if (taskHolder.status != null) {
                taskStatus.setText(taskHolder.status.toString());
            } else {
                taskStatus.setText("null");
            }
             */
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(List<Task> dataSet) {
        mDataSet = dataSet;
    }

    public void setmDataSet(List<Task> dataSet) {
        mDataSet = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.setData(mDataSet.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "clicked on ", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(view.getContext(), AcceptTaskActivity.class);
                mIntent.putExtra("taskName", mDataSet.get(position).name);
                mIntent.putExtra("taskDescription", mDataSet.get(position).description);
                mIntent.putExtra("taskAddress", mDataSet.get(position).address);
                mIntent.putExtra("taskType", mDataSet.get(position).type);
                mIntent.putExtra("taskEmergency", mDataSet.get(position).urgency);
                mIntent.putExtra("taskWorkload", mDataSet.get(position).workload);
                mIntent.putExtra("taskStatus", mDataSet.get(position).status);
                view.getContext().startActivity(mIntent);
            }
        });
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataSet == null) {
            return 0;
        }
        return mDataSet.size();
    }
}
