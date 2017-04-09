/*
 * Copyright (C) 2017 Seongil Kim <kims172@gmail.com>
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

package com.seongil.recyclerviewlife.sample.ui.linearlistview.adapter.viewbinder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlife.sample.R;
import com.seongil.recyclerviewlife.sample.model.AdvertisementItem;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractViewBinder;

/**
 * @author seong-il, kim
 * @since 17. 4. 8
 */
public class LinearListAdvertisementViewBinder extends AbstractViewBinder {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================
    public LinearListAdvertisementViewBinder(int viewType, @NonNull LayoutInflater inflater,
          @Nullable RecyclerViewItemClickListener itemClickListener) {
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
        return item instanceof AdvertisementItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AdvertisementViewHolder(
              mLayoutInflater.inflate(R.layout.linearlistview_item_advertisement, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItem item, @NonNull RecyclerView.ViewHolder holder) {
        final AdvertisementItem data = (AdvertisementItem) item;
        final AdvertisementViewHolder viewHolder = (AdvertisementViewHolder) holder;
        viewHolder.itemView.setOnClickListener(v -> {
            if (mItemViewClickListener != null) {
                mItemViewClickListener.onClickedRecyclerViewItem(holder, data, viewHolder.getLayoutPosition());
            }
        });
        viewHolder.title.setText(data.getTitle());

        Glide.with(viewHolder.itemView.getContext())
              .fromResource()
              .load(data.getIconRes())
              .into(viewHolder.icon);
    }

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
    public static class AdvertisementViewHolder extends RecyclerView.ViewHolder {

        public final TextView title;
        public final ImageView icon;

        public AdvertisementViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.primary_text);
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }
}
