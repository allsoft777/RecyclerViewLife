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

package com.seongil.recyclerviewlib.lib.single.viewbinder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.seongil.recyclerviewlib.lib.model.common.RecyclerViewItem;

/**
 * @author seong-il, kim
 * @since 17. 3. 21
 */
@SuppressWarnings("unused")
public abstract class AbstractViewBinder implements ViewBinder {

    // ========================================================================
    // Constants
    // ========================================================================
    /**
     * Constant indicating the header view type. {@link AbstractHeaderViewBinder} will use this constant.
     * Also, {@link ViewBinderListManager} can have a one item having this view type.
     */
    public static final int RECYCLER_HEADER_VIEW_TYPE = 1000;

    /**
     * Constant indicating the footer view type. {@link AbstractFooterViewBinder} will use this constant.
     * Also, {@link ViewBinderListManager} can have a one item having this view type.
     */
    public static final int RECYCLER_FOOTER_VIEW_TYPE = 1001;

    // TODO implementation
    public static final int RECYCLER_GROUP_VIEW_TYPE = 1002;

    // ========================================================================
    // Fields
    // ========================================================================
    /**
     * The item view type
     */
    protected int mViewType;

    /**
     * Click listener for the RecyclerView item.
     */
    @Nullable
    protected RecyclerViewItemClickListener mItemViewClickListener;

    /**
     * Long click listener for the RecyclerView item.
     */
    @Nullable
    protected RecyclerViewItemLongClickListener mItemViewLongClickListener;

    /**
     * Layout inflater.
     */
    @NonNull
    protected LayoutInflater mLayoutInflater;

    // ========================================================================
    // Constructors
    // ========================================================================
    public AbstractViewBinder(final int viewType, @NonNull LayoutInflater inflater) {
        this(viewType, inflater, null, null);
    }

    public AbstractViewBinder(
        final int viewType,
        @NonNull LayoutInflater inflater,
        @Nullable RecyclerViewItemClickListener itemClickListener) {
        this(viewType, inflater, itemClickListener, null);
    }

    public AbstractViewBinder(
        final int viewType,
        @NonNull LayoutInflater inflater,
        @Nullable RecyclerViewItemLongClickListener itemLongClickListener) {
        this(viewType, inflater, null, itemLongClickListener);
    }

    public AbstractViewBinder(
        final int viewType,
        @NonNull LayoutInflater inflater,
        @Nullable RecyclerViewItemClickListener itemClickListener,
        @Nullable RecyclerViewItemLongClickListener itemLongClickListener) {

        mViewType = viewType;
        mLayoutInflater = inflater;
        mItemViewClickListener = itemClickListener;
        mItemViewLongClickListener = itemLongClickListener;
    }

    // ========================================================================
    // Getter & Setter
    // ========================================================================
    @VisibleForTesting
    protected RecyclerViewItemClickListener getClickListener() {
        return mItemViewClickListener;
    }

    // ========================================================================
    // Methods for/from SuperClass/Interfaces
    // ========================================================================
    @Override
    public int getItemViewType() {
        return mViewType;
    }

    // ========================================================================
    // Methods
    // ========================================================================

    // ========================================================================
    // Inner and Anonymous Classes
    // ========================================================================

    /**
     * Interface definition for a callback invoked when user is clicked the {@link RecyclerViewItem}
     */
    public interface RecyclerViewItemClickListener {

        // ========================================================================
        // Constants
        // ========================================================================

        // ========================================================================
        // Methods
        // ========================================================================
        void onClickedRecyclerViewItem(
            @NonNull RecyclerView.ViewHolder vh,
            @NonNull RecyclerViewItem info,
            final int position);
    }

    /**
     * Interface definition for a callback invoked when user is long-clicked the {@link RecyclerViewItem}
     */
    public interface RecyclerViewItemLongClickListener {

        // ========================================================================
        // Constants
        // ========================================================================

        // ========================================================================
        // Methods
        // ========================================================================
        void onLongClickedRecyclerViewItem(
            @NonNull RecyclerView.ViewHolder vh,
            @NonNull RecyclerViewItem info,
            final int position);
    }
}