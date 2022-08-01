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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        private final TextView taskAddress;
        private final TextView taskType;
        private final TextView taskEmergency;
        private final ImageView itemImage;

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
            taskAddress = (TextView) v.findViewById(R.id.Address);
            taskType = (TextView) v.findViewById(R.id.Type);
            taskEmergency = (TextView) v.findViewById(R.id.Emergency);
            itemImage = (ImageView) v.findViewById(R.id.itemImage);
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
        Map<String, Integer> mapper = new HashMap();
        if (WeatherWrapper.getInstance().getStatus() > 0) {
            for (Task data : dataSet) {
                if (data.type.equals("Water")) {
                    if (data.urgency.equals("Low")) {
                        data.urgency = "Mid";
                    } else if (data.urgency.equals("Mid")) {
                        data.urgency = "High";
                    }
                }
            }
        } else if (WeatherWrapper.getInstance().getStatus() < 0) {
            for (Task data : dataSet) {
                if (data.type.equals("Water")) {
                    if (data.urgency.equals("High")) {
                        data.urgency = "Mid";
                    } else if (data.urgency.equals("Mid")) {
                        data.urgency = "Low";
                    }
                }
            }
        }
        mapper.put("Low", 1);
        mapper.put("Mid", 2);
        mapper.put("High", 3);
        Collections.sort(dataSet, new Comparator<Task>(){
            public int compare(Task o1, Task o2){
                return mapper.get(o2.urgency) - mapper.get(o1.urgency);
            }
        });
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
                mIntent.putExtra("creator", mDataSet.get(position).creator);
                mIntent.putExtra("owner", mDataSet.get(position).owner);
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
