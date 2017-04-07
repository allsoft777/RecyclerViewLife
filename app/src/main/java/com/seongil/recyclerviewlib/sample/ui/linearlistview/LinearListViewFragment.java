/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.seongil.recyclerviewlib.sample.ui.linearlistview;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seongil.recyclerviewlib.model.RecyclerViewFooterItem;
import com.seongil.recyclerviewlib.model.RecyclerViewHeaderItem;
import com.seongil.recyclerviewlib.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlib.model.common.ViewStatus;
import com.seongil.recyclerviewlib.sample.R;
import com.seongil.recyclerviewlib.sample.application.MainApplication;
import com.seongil.recyclerviewlib.sample.model.DataProvider;
import com.seongil.recyclerviewlib.sample.ui.dialog.OptionDialogFragment;
import com.seongil.recyclerviewlib.sample.ui.linearlistview.adapter.LinearListViewAdapter;
import com.seongil.recyclerviewlib.scroll.LinearRecyclerViewScrollListener;
import com.seongil.recyclerviewlib.single.viewbinder.AbstractViewBinder.RecyclerViewItemClickListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author seongil2.kim
 * @since: 17. 4. 7
 */
public class LinearListViewFragment extends Fragment
      implements RecyclerViewItemClickListener, OptionDialogFragment.OptionDialogListener {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    private RecyclerView mListView;
    private DialogFragment mCurShowingDialog;
    private LinearListViewAdapter mAdapter;

    private boolean mExistPrevItems = true;
    private boolean mInLoadingPrevItems = false;
    private boolean mExistNextItems = true;
    private boolean mInLoadingNextItems = false;

    private LinearRecyclerViewScrollListener mScrollListener;
    private Disposable mLoaderTopDirection;
    private Disposable mLoaderBottomDirection;

    private int mItemCount = OptionDialogFragment.LOADING_ITEM_MIN_CNT;
    private int mItemCountPerCycle = OptionDialogFragment.LOADING_ITEM_MIN_CNT_PER_CYCLE;
    private int mLoadingDirection = OptionDialogFragment.LOADING_FOOTER;
    private int mLoadingMode = OptionDialogFragment.LOADING_INFINITE;
    private int mLoadingFinishedAction = OptionDialogFragment.HIDE_HEADER_FOOTER;

    // ========================================================================
    // constructors
    // ========================================================================
    public static synchronized LinearListViewFragment newInstance() {
        return new LinearListViewFragment();
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_recyclerview_only, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (RecyclerView) view.findViewById(R.id.recycler_view);
        initialize();
    }

    @Override
    public void onFinishedOptionSelection(
          int loadingItemCnt, int loadingItemCntPerCycler, int loadingDirection,
          int loadingMode, int actionAfterFinishedLoading) {
        mItemCount = loadingItemCnt;
        mItemCountPerCycle = loadingItemCntPerCycler;
        mLoadingDirection = loadingDirection;
        mLoadingMode = loadingMode;
        mLoadingFinishedAction = actionAfterFinishedLoading;
        initialize();
    }

    @Override
    public void onPause() {
        if (mCurShowingDialog != null) {
            mCurShowingDialog.dismiss();
        }
        stopLoading();
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_option_dialog:
                launchOptionDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickedRecyclerViewItem(
          @NonNull RecyclerView.ViewHolder vh, @NonNull RecyclerViewItem info, int position) {
        if (info instanceof RecyclerViewHeaderItem) {
            mAdapter.updateHeaderViewStatus(ViewStatus.VISIBLE_LOADING_VIEW, true);
            fetchTopDirectionDataSet(mItemCountPerCycle, true);
        } else if (info instanceof RecyclerViewFooterItem) {
            mAdapter.updateFooterViewStatus(ViewStatus.VISIBLE_LOADING_VIEW, true);
            fetchBottomDirectionDataSet(mItemCountPerCycle, true);
        } else {
            Toast.makeText(getContext().getApplicationContext(), "Clicked item's position : " + position,
                  Toast.LENGTH_SHORT).show();
        }
    }

    // ========================================================================
    // methods
    // ========================================================================
    private void initDataCache() {
        DataProvider.getInstance().initialize(getActivity().getApplicationContext(), mItemCount);
        mExistNextItems = true;
        mExistPrevItems = true;
        mInLoadingNextItems = false;
        mInLoadingPrevItems = false;
    }

    private void initialize() {

        // Stop loading tasks currently.
        stopLoading();

        // Clear caches to load data for the first time.
        initDataCache();

        // Clear the dataSet previously loaded.
        if (mAdapter != null) {
            mAdapter.clearDataSet();
        }

        mAdapter = new LinearListViewAdapter(mLoadingMode, getActivity().getLayoutInflater(), this);
        if (mLoadingDirection == OptionDialogFragment.LOADING_HEADER) {
            mAdapter.registerHeaderView();
        } else if (mLoadingDirection == OptionDialogFragment.LOADING_FOOTER) {
            mAdapter.registerFooterView();
        } else {
            mAdapter.registerHeaderView();
            mAdapter.registerFooterView();
        }

        LinearLayoutManager llm = new LinearLayoutManager(MainApplication.getAppContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(llm);
        mListView.setAdapter(mAdapter);
        mListView.setHasFixedSize(true);
        mListView.setFocusable(false);
        mScrollListener = new LinearRecyclerViewScrollListener(llm, mAdapter) {

            @Override
            protected boolean isLoadingNextItems() {
                return mInLoadingNextItems;
            }

            @Override
            protected boolean isLoadingPrevItems() {
                return mInLoadingPrevItems;
            }

            @Override
            public void onLoadNextData() {
                super.onLoadNextData();
                if (mExistNextItems && !mInLoadingNextItems) {
                    fetchBottomDirectionDataSet(mItemCountPerCycle, false);
                }
            }

            @Override
            public void onLoadPrevData() {
                super.onLoadPrevData();
                if (mExistPrevItems && !mInLoadingPrevItems) {
                    fetchTopDirectionDataSet(mItemCountPerCycle, false);
                }
            }
        };
        mListView.clearOnScrollListeners();
        mListView.addOnScrollListener(mScrollListener);

        // Start loading data as per the options user selected.
        if (mLoadingDirection == OptionDialogFragment.LOADING_FOOTER) {
            fetchBottomDirectionDataSet(mItemCountPerCycle, false);
        } else if (mLoadingDirection == OptionDialogFragment.LOADING_HEADER) {
            fetchTopDirectionDataSet(mItemCountPerCycle, false);
        } else {
            fetchBottomDirectionDataSet(mItemCountPerCycle, false);
            fetchTopDirectionDataSet(mItemCountPerCycle, false);
        }
    }

    private void stopLoading() {
        if (mLoaderTopDirection != null) {
            mLoaderTopDirection.dispose();
        }
        if (mLoaderBottomDirection != null) {
            mLoaderBottomDirection.dispose();
        }
    }

    private void fetchedPrevDataSet(List<RecyclerViewItem> result) {
        if (result.size() < mItemCountPerCycle) {
            // There is no items to be loaded in the previous request.
            mExistPrevItems = false;

            if (mLoadingFinishedAction == OptionDialogFragment.HIDE_HEADER_FOOTER) {
                mAdapter.unregisterHeaderView();
            } else {
                mAdapter.updateHeaderViewStatus(ViewStatus.VISIBLE_LABEL_VIEW, false);
            }
        }
        if (mAdapter.headerViewStatusCodeIsOneCycle()) {
            mAdapter.updateHeaderViewStatusCodeForOneCycle(false);
        }
        mAdapter.addFirstCollection(result);
    }

    private void fetchedNextDataSet(List<RecyclerViewItem> result) {
        if (result == null || result.size() < mItemCountPerCycle) {
            // There is no items to load in the next request.
            mExistNextItems = false;

            if (mLoadingFinishedAction == OptionDialogFragment.HIDE_HEADER_FOOTER) {
                mAdapter.unregisterFooterView();
            } else {
                mAdapter.updateFooterViewStatus(ViewStatus.VISIBLE_LABEL_VIEW, false);
            }
        }
        if (mAdapter.footerViewStatusCodeIsOneCycle()) {
            mAdapter.updateFooterViewStatusCodeForOneCycle(false);
        }
        mAdapter.addLastCollection(result);
    }

    private void fetchTopDirectionDataSet(int count, boolean force) {
        mInLoadingPrevItems = true;
        mLoaderTopDirection = DataProvider.getInstance().generateData(count, force)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .delay(500, TimeUnit.MILLISECONDS)
              .subscribe(recyclerViewItems -> {
                  fetchedPrevDataSet(recyclerViewItems);
                  mInLoadingPrevItems = false;
              }, t -> mInLoadingPrevItems = false);
    }

    private void fetchBottomDirectionDataSet(int count, boolean force) {
        mInLoadingNextItems = true;
        mLoaderBottomDirection = DataProvider.getInstance().generateData(count, force)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .delay(500, TimeUnit.MILLISECONDS)
              .subscribe(recyclerViewItems -> {
                  fetchedNextDataSet(recyclerViewItems);
                  mInLoadingNextItems = false;
              }, t -> mInLoadingNextItems = false);
    }

    private void launchOptionDialog() {
        mCurShowingDialog =
              OptionDialogFragment.newInstance(
                    mItemCount, mItemCountPerCycle, mLoadingDirection, mLoadingMode, mLoadingFinishedAction);
        mCurShowingDialog.setTargetFragment(this, 1);
        mCurShowingDialog.show(getFragmentManager(), "dialog");
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
