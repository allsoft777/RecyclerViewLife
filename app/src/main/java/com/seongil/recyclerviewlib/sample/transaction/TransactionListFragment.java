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

package com.seongil.recyclerviewlib.sample.transaction;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seongil.recyclerviewlib.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlib.single.viewbinder.AbstractViewBinder;
import com.seongil.recyclerviewlib.sample.R;
import com.seongil.recyclerviewlib.sample.mock.TransactionDataGenerator;
import com.seongil.recyclerviewlib.sample.transaction.adapter.TransactionListAdapter;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author seongil2.kim
 * @since: 17. 1. 10
 */
public class TransactionListFragment extends Fragment {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    private TransactionListAdapter mAdapter;
    private RecyclerView mListView;
    private TextView mEmptyView;
    private ProgressBar mLoadingView;
    private View mErrorLayout;

    // ========================================================================
    // constructors
    // ========================================================================
    public static synchronized TransactionListFragment newInstance() {
        return new TransactionListFragment();
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (RecyclerView) view.findViewById(R.id.listview);
        mAdapter = new TransactionListAdapter(getActivity().getLayoutInflater(), mItemClickListener);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(llm);
        mListView.setAdapter(mAdapter);
        mListView.setHasFixedSize(true);

        mEmptyView = (TextView) view.findViewById(R.id.empty_text);
        mLoadingView = (ProgressBar) view.findViewById(R.id.loading_bar);
        mErrorLayout = view.findViewById(R.id.error_view);

        showLoadingView();
        loadData();
    }

    // ========================================================================
    // methods
    // ========================================================================
    private void loadData() {
        Single
            .create(new GetMockDataOnSubscribe())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::renderListData);
    }

    private void renderListData(List<RecyclerViewItem> list) {
        mAdapter.addLastCollection(list);
        showListView();
    }

    private void showListView() {
        mListView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    private void showLoadingView() {
        mListView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
    private static class GetMockDataOnSubscribe implements SingleOnSubscribe<List<RecyclerViewItem>> {

        @Override
        public void subscribe(SingleEmitter<List<RecyclerViewItem>> e) throws Exception {
            List<RecyclerViewItem> list = TransactionDataGenerator.generate(10);
            if (e.isDisposed()) {
                return;
            }
            e.onSuccess(list);
        }
    }

    private AbstractViewBinder.RecyclerViewItemClickListener mItemClickListener =
        new AbstractViewBinder.RecyclerViewItemClickListener() {

            @Override
            public void onClickedRecyclerViewItem(@NonNull RecyclerView.ViewHolder vh, @NonNull RecyclerViewItem info, int position) {
                Toast.makeText(getActivity().getApplicationContext(), "Clicked position : " + position, Toast.LENGTH_SHORT).show();
            }
        };
}
