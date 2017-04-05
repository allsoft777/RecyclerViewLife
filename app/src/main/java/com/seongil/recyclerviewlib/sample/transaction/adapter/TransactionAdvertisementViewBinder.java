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

package com.seongil.recyclerviewlib.sample.transaction.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seongil.recyclerviewlib.lib.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlib.lib.single.viewbinder.AbstractViewBinder;
import com.seongil.recyclerviewlib.sample.R;
import com.seongil.recyclerviewlib.sample.model.TransactionAdvertisement;

/**
 * @author seongil2.kim
 * @since: 17. 1. 16
 */
public class TransactionAdvertisementViewBinder extends AbstractViewBinder {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================
    public TransactionAdvertisementViewBinder(
        int viewType,
        @NonNull LayoutInflater inflater,
        @NonNull RecyclerViewItemClickListener itemClickListener) {
        super(viewType, inflater, itemClickListener);
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    public boolean isForViewType(@NonNull RecyclerViewItem item) {
        return item instanceof TransactionAdvertisement;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new TransactionAdvertisementItemViewHolder(
            mLayoutInflater.inflate(R.layout.list_item_advertisement_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItem item,
                                 @NonNull final RecyclerView.ViewHolder holder) {
        final TransactionAdvertisement data = (TransactionAdvertisement) item;
        final TransactionAdvertisementItemViewHolder viewHolder = (TransactionAdvertisementItemViewHolder) holder;
        final Resources res = viewHolder.itemView.getResources();
        viewHolder.name.setText(data.getAdvertisementName());
        Glide
            .with(viewHolder.itemView.getContext())
            .fromResource()
            .load(data.getThumbnailId())
            .into(viewHolder.imageview);
        holder.itemView.setOnClickListener(v -> mItemViewClickListener.onClickedRecyclerViewItem(holder, data, holder.getLayoutPosition()));
    }

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
    public static class TransactionAdvertisementItemViewHolder extends RecyclerView.ViewHolder {

        public final TextView name;
        public final ImageView imageview;

        public TransactionAdvertisementItemViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            imageview = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
}
