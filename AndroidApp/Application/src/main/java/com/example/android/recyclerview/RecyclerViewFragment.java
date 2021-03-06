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

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.android.RetrofitApi.APIClient;
import com.example.android.RetrofitApi.APIInterface;
import com.example.android.RetrofitApi.POJO.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager} and a
 * {@link GridLayoutManager}.
 */
public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    //private static final int DATASET_COUNT = 30;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected TextView textView;
    protected List<Task> mDataset;
    private String type;
    private static Integer count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("Type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        textView = (TextView) rootView.findViewById(R.id.noTask);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new CustomAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        //count += 1;
        if (count == 0) {
            initDataset();
        }
        count += 1;
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("###########" + type);
        //count += 1;
        //initDataset();
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        count = 0;
    }


    @Override
    public void setUserVisibleHint(boolean userVisibleHint){
        super.setUserVisibleHint(userVisibleHint);
        if (getUserVisibleHint()) {
            initDataset();
        }
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    public void initDataset() {
        if (mAdapter == null) {
            return;
        }
        String owner = null;
        String creator = null;
        if (RoleWrapper.getInstance().getRoleIsVolunteer()) {
            owner = UserWrapper.getInstance().getName();
        } else {
            creator = UserWrapper.getInstance().getName();
        }

        System.out.println(RoleWrapper.getInstance().getRoleIsVolunteer());
        System.out.println(creator);
        System.out.println(owner);

        switch (type) {
            case "all":
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<List<Task>> call1 = apiInterface.doGetListTasksStatus("Created", null, creator);
                call1.enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                        mDataset = response.body();
                        if (mDataset.size() > 0) {
                            textView.setVisibility(View.GONE);
                        }
                        mAdapter.setmDataSet(mDataset);
                        mAdapter.notifyDataSetChanged();
                        onResume();
                    }

                    @Override
                    public void onFailure(Call<List<Task>> call, Throwable t) {
                        call.cancel();
                    }
                });
                break;
            case "progress":
                APIInterface apiInter = APIClient.getClient().create(APIInterface.class);
                Call<List<Task>> call = apiInter.doGetListTasksStatus("InProgress", owner, creator);
                call.enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                        mDataset = response.body();
                        if (mDataset.size() > 0) {
                            textView.setVisibility(View.GONE);
                        }
                        mAdapter.setmDataSet(mDataset);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Task>> call, Throwable t) {
                        call.cancel();
                    }
                });
                break;
            case "finished":
                APIInterface apiInterFinished = APIClient.getClient().create(APIInterface.class);
                Call<List<Task>> callFinished = apiInterFinished.doGetListTasksStatus("Finished", owner, creator);
                callFinished.enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                        mDataset = response.body();
                        if (mDataset.size() > 0) {
                            textView.setVisibility(View.GONE);
                        }
                        mAdapter.setmDataSet(mDataset);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Task>> call, Throwable t) {
                        call.cancel();
                    }
                });
                break;
        }
    }

}
