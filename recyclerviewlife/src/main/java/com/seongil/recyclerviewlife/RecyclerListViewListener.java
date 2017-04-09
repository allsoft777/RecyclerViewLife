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

package com.seongil.recyclerviewlife;

import com.seongil.recyclerviewlife.scroll.LinearRecyclerViewScrollListener;
import com.seongil.recyclerviewlife.single.RecyclerListViewAdapter;

/**
 * @author seong-il, kim
 * @since 17. 3. 21
 */
public interface RecyclerListViewListener {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // methods
    // ========================================================================

    /**
     * If you called {@link RecyclerListViewAdapter#registerFooterView()} from the concrete view, it will return "true"
     *
     * @return true, User is called {@link RecyclerListViewAdapter#registerFooterView()} from the concrete view.
     */
    boolean registeredFooterView();

    /**
     * If you called {@link RecyclerListViewAdapter#registerHeaderView()} from the concrete view, it will return "true"
     *
     * @return true, User is called {@link RecyclerListViewAdapter#registerHeaderView()} from the concrete view.
     */
    boolean registeredHeaderView();

    /**
     * This method is invoked from {@link LinearRecyclerViewScrollListener}
     * to check the status whether getting more items on the bottom side.
     *
     * @return true, there is more items to load on the bottom side.
     */
    boolean loadFooterItemsMore();

    /**
     * This method is invoked from {@link LinearRecyclerViewScrollListener}
     * to check the status whether getting more items on the top side.
     *
     * @return true, there is more items to load on the top side.
     */
    boolean loadHeaderItemsMore();

    /**
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     * <p>
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     *
     * @return integer value identifying the type of the view needed to represent the item at
     * <code>position</code>. Type codes need not be contiguous.
     */
    int getItemViewType(int position);
}