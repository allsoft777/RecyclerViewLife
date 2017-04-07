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

package com.seongil.recyclerviewlib.single.viewbinder;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.seongil.recyclerviewlib.LibUtils;
import com.seongil.recyclerviewlib.model.common.RecyclerViewItem;

/**
 * @author seong-il, kim
 * @since 17. 3. 21
 */
public class ViewBinderListManager {

    // ========================================================================
    // Constants
    // ========================================================================
    private static final String TAG = "ViewBinderListManager";
    private static final String LOG_PREFIX = "[" + TAG + "]";

    // ========================================================================
    // Fields
    // ========================================================================
    private final SparseArrayCompat<ViewBinder> mViewBinderList;

    // ========================================================================
    // Constructors
    // ========================================================================
    public ViewBinderListManager() {
        this(8);
    }

    public ViewBinderListManager(final int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity < 0: " + capacity);
        }

        mViewBinderList = new SparseArrayCompat<>(capacity);
    }

    // ========================================================================
    // Getter & Setter
    // ========================================================================
    @VisibleForTesting
    protected SparseArrayCompat<ViewBinder> getViewBinderList() {
        return mViewBinderList;
    }

    // ========================================================================
    // Methods for/from SuperClass/Interfaces
    // ========================================================================

    // ========================================================================
    // Methods
    // ========================================================================
    public boolean addViewBinder(@NonNull ViewBinder viewBinder) {
        LibUtils.checkNotNull(viewBinder, "ViewBinder is null. You must pass valid object.");

        final int viewType = viewBinder.getItemViewType();
        if (existViewBinder(viewBinder)) {
            StringBuilder sb = new StringBuilder(1024);
            sb.append(
                  LOG_PREFIX + "ViewBinder is been registered in ViewBinderList already. type : " + viewType + '\n');
            if (viewType == AbstractViewBinder.RECYCLER_HEADER_VIEW_TYPE) {
                sb.append("Requested ViewType is HeaderView type. So, you have to use another type value.");
            } else if (viewType == AbstractViewBinder.RECYCLER_FOOTER_VIEW_TYPE) {
                sb.append("Requested ViewType is FooterView type. So, you have to use another type value.");
            }
            throw new IllegalArgumentException(TAG + sb);
        }
        mViewBinderList.put(viewType, viewBinder);
        return true;
    }

    public boolean removeViewBinder(int viewType) {
        ViewBinder element;
        final int size = mViewBinderList.size();
        for (int i = 0; i < size; i++) {
            element = mViewBinderList.valueAt(i);
            if (element.getItemViewType() == viewType) {
                mViewBinderList.removeAt(i);
                Log.d(TAG, LOG_PREFIX + "Removed the ViewBinder. type : " + viewType);
                return true;
            }
        }
        Log.d(TAG, LOG_PREFIX + "Failed to remove the ViewBinder. (There is no view binder) type : " + viewType);
        return false;
    }

    public boolean existViewBinder(@NonNull final ViewBinder viewBinder) {
        LibUtils.checkNotNull(viewBinder, "ViewBinder is null. You must pass a valid object.");

        final int viewType = viewBinder.getItemViewType();
        return mViewBinderList.get(viewType) != null;
    }

    public int getItemViewType(RecyclerViewItem item) {
        final int size = mViewBinderList.size();
        ViewBinder element;
        for (int i = 0; i < size; i++) {
            element = mViewBinderList.valueAt(i);
            if (element.isForViewType(item)) {
                return element.getItemViewType();
            }
        }
        throw new IllegalArgumentException("No ViewBinder");
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final ViewBinder viewBinder = mViewBinderList.get(viewType);
        LibUtils.checkNotNull(viewBinder, "No ViewBinder added for ViewType " + viewType);

        return viewBinder.onCreateViewHolder(parent);
    }

    public void onBindViewHolder(
          RecyclerViewItem item,
          RecyclerView.ViewHolder viewHolder) {

        ViewBinder viewBinder = mViewBinderList.get(viewHolder.getItemViewType());
        if (viewBinder == null) {
            throw new NullPointerException(
                  "No ViewBinder added for ViewType " + viewHolder.getItemViewType());
        }
        viewBinder.onBindViewHolder(item, viewHolder);
    }

    public void clear() {
        if (mViewBinderList.size() == 0) {
            return;
        }
        mViewBinderList.clear();
    }

    public int size() {
        return mViewBinderList.size();
    }

    // ========================================================================
    // Inner and Anonymous Classes
    // ========================================================================
}
