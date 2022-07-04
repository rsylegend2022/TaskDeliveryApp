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

import com.example.android.common.logger.Log;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private TaskHolder[] mDataSet;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskName;
        private final TextView taskDescription;
        private final TextView taskAddress;
        private final TextView taskType;
        private final TextView taskEmergency;
        private final TextView taskStatus;
        private final TextView taskWorkload;

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
            taskDescription = (TextView) v.findViewById(R.id.Description);
            taskAddress = (TextView) v.findViewById(R.id.Address);
            taskType = (TextView) v.findViewById(R.id.Type);
            taskEmergency = (TextView) v.findViewById(R.id.Emergency);
            taskStatus = (TextView) v.findViewById(R.id.Status);
            taskWorkload = (TextView) v.findViewById(R.id.Workload);
        }


        public void setData(TaskHolder taskHolder) {
            taskName.setText(taskHolder.name);
            taskDescription.setText(taskHolder.description);
            taskAddress.setText(taskHolder.address);
            taskType.setText(taskHolder.type);
            taskEmergency.setText(taskHolder.urgency);
            taskWorkload.setText(taskHolder.workload);
            taskStatus.setText(taskHolder.status.toString());
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(TaskHolder[] dataSet) {
        mDataSet = dataSet;
    }

    public void setmDataSet(TaskHolder[] dataSet) {
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.setData(mDataSet[position]);
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
