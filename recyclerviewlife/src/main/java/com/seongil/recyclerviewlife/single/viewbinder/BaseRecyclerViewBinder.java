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

package com.seongil.recyclerviewlife.single.viewbinder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;

/**
 * @author seong-il, kim
 * @since 17. 3. 21
 */
public interface BaseRecyclerViewBinder {

    // ========================================================================
    // Methods
    // ========================================================================

    /**
     * Returns the view type
     *
     * @return Integer view type
     */
    int getItemViewType();

    /**
     * Checks the given position of the data set is correct or not.
     *
     * @param recyclerViewItem Target recyclerview item.
     *
     * @return true, if this DataSet is responsible, otherwise false
     */
    boolean isForViewType(RecyclerViewItem recyclerViewItem);

    /**
     * Creates the {@link RecyclerView.ViewHolder} for the given data source item
     *
     * @param parent The ViewGroup parent of the given DataSet
     *
     * @return The new instantiated {@link RecyclerView.ViewHolder}
     */
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    /**
     * Called to bind the {@link RecyclerView.ViewHolder} to the item of DataSet
     *
     * @param recyclerViewItem Target recyclerview item.
     * @param position The position in DataSet
     * @param holder The {@link RecyclerView.ViewHolder} to bind
     */
    void onBindViewHolder(RecyclerViewItem recyclerViewItem, int position, RecyclerView.ViewHolder holder);
}
