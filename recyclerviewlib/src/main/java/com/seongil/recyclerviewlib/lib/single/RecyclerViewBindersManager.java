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

package com.seongil.recyclerviewlib.lib.single;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.seongil.recyclerviewlib.lib.LibUtils;
import com.seongil.recyclerviewlib.lib.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlib.lib.single.viewbinder.BaseRecyclerViewBinder;

/**
 * @author seong-il, kim
 * @since 17. 3. 21
 */
@SuppressWarnings("unused")
public class RecyclerViewBindersManager<T> {

    // ========================================================================
    // Constants
    // ========================================================================
    private static final String TAG = "RecyclerViewBindersManager";

    // ========================================================================
    // Fields
    // ========================================================================
    private final SparseArrayCompat<BaseRecyclerViewBinder> mViewBinderList = new SparseArrayCompat<>(4);

    // ========================================================================
    // Constructors
    // ========================================================================

    // ========================================================================
    // Getter & Setter
    // ========================================================================

    // ========================================================================
    // Methods for/from SuperClass/Interfaces
    // ========================================================================

    // ========================================================================
    // Methods
    // ========================================================================
    public RecyclerViewBindersManager<T> addViewBinder(@NonNull BaseRecyclerViewBinder viewBinder) {
        final int viewType = viewBinder.getItemViewType();
        if (mViewBinderList.get(viewType) != null) {
            throw new IllegalArgumentException(
                "Given ViewBinder is already added in the list. ViewType : "
                    + viewType + ". Already registered ViewBinder type is " + mViewBinderList.get(viewType));
        }

        mViewBinderList.put(viewType, viewBinder);
        return this;
    }

    public int getItemViewType(@NonNull final RecyclerViewItem recyclerViewItem) {
        BaseRecyclerViewBinder element;
        final int delegatesCount = mViewBinderList.size();
        for (int i = 0; i < delegatesCount; i++) {
            element = mViewBinderList.valueAt(i);
            if (element.isForViewType(recyclerViewItem)) {
                return element.getItemViewType();
            }
        }

        final StringBuilder logger = new StringBuilder(256);
        logger.append("There is no RecyclerViewBinder in the list.\nSize of mViewBinderList : ")
            .append(delegatesCount).append("\nitem list\n");
        for (int i = 0; i < delegatesCount; i++) {
            logger.append("view type : ");
            logger.append(mViewBinderList.get(i) != null ? mViewBinderList.get(i).getItemViewType() : "null");
            logger.append('\n');
        }
        throw new IllegalArgumentException(logger.toString());
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseRecyclerViewBinder viewBinder = mViewBinderList.get(viewType);
        LibUtils.checkNotNull(viewBinder, "No ViewBinder added for ViewType " + viewType);
        final RecyclerView.ViewHolder vh = viewBinder.onCreateViewHolder(parent);
        LibUtils.checkNotNull(vh, "ViewHolder returned from ConCreate view binder is null. View type is " + viewType);
        return vh;
    }

    public void onBindViewHolder(RecyclerViewItem recyclerViewItem, int position, RecyclerView.ViewHolder viewHolder) {
        final BaseRecyclerViewBinder viewBinder = mViewBinderList.get(viewHolder.getItemViewType());
        LibUtils.checkNotNull(viewBinder, "No ViewBinder added for ViewType " + viewHolder.getItemViewType());
        viewBinder.onBindViewHolder(recyclerViewItem, position, viewHolder);
    }

    // ========================================================================
    // Inner and Anonymous Classes
    // ========================================================================
}
