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

package com.seongil.recyclerviewlife.single;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.seongil.recyclerviewlife.LibUtils;
import com.seongil.recyclerviewlife.model.RecyclerViewFooterItem;
import com.seongil.recyclerviewlife.model.RecyclerViewHeaderItem;
import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlife.model.common.ViewStatus;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractFooterViewBinder;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractHeaderViewBinder;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractViewBinder;

/**
 * @author seong-il, kim
 * @since 17. 3. 21
 */
public abstract class RecyclerListViewAdapter<T extends RecyclerViewItem>
      extends AbstractRecyclerViewAdapter<T> {

    // ========================================================================
    // Constants
    // ========================================================================

    // ========================================================================
    // Fields
    // ========================================================================
    /**
     * Indicates that the current adapter is using the header view.
     */
    private boolean mUseHeaderView;

    /**
     * Indicates that the current adapter is using the footer view.
     */
    private boolean mUseFooterView;

    // ========================================================================
    // Constructors
    // ========================================================================
    public RecyclerListViewAdapter(
          @NonNull LayoutInflater layoutInflater) {
        super(layoutInflater);
    }

    // ========================================================================
    // Getter & Setter
    // ========================================================================

    // ========================================================================
    // Methods for/from SuperClass/Interfaces
    // ========================================================================
    @Override
    public boolean registeredFooterView() {
        return mUseFooterView;
    }

    @Override
    public boolean registeredHeaderView() {
        return mUseHeaderView;
    }

    @Override
    public boolean loadFooterItemsMore() {
        return mUseFooterView && getRecyclerFooterViewItemFromDataSet().autoLoadData();
    }

    @Override
    public boolean loadHeaderItemsMore() {
        return mUseHeaderView && getRecyclerHeaderViewItemFromDataSet().autoLoadData();
    }

    @Override
    public synchronized void clearDataSet() {
        super.clearDataSet();
        mUseHeaderView = false;
        mUseFooterView = false;
        clearViewBinders();
    }

    // ========================================================================
    // Methods
    // ========================================================================

    /**
     * If you want to use FooterItem on {@link RecyclerView}, you have to override this method.
     *
     * @return Newly instantiated footer view binder.
     */
    protected AbstractFooterViewBinder getNewInstanceOfFooterViewBinder() {
        return null;
    }

    /**
     * If you want to use FooterItem on {@link RecyclerView}, you have to override this method.
     *
     * @return Newly instantiated footer item.
     */
    protected RecyclerViewFooterItem getNewInstanceOfFooterItem() {
        return null;
    }

    /**
     * If you want to use HeaderItem on {@link RecyclerView}, you have to override this method.
     *
     * @return Newly instantiated header view binder.
     */
    protected AbstractHeaderViewBinder getNewInstanceOfHeaderViewBinder() {
        return null;
    }

    /**
     * If you want to use HeaderItem on {@link RecyclerView}, you have to override this method.
     *
     * @return Newly instantiated header item.
     */
    protected RecyclerViewHeaderItem getNewInstanceOfHeaderItem() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public void registerFooterView() {
        if (mUseFooterView) {
            throw new IllegalArgumentException("Footer item is inserted already.");
        }
        final AbstractViewBinder avb = getNewInstanceOfFooterViewBinder();
        LibUtils.checkNotNull(avb, "Footer View Binder is null. You must override getNewInstanceOfFooterViewBinder()");
        addViewBinder(avb);

        final RecyclerViewFooterItem footerItem = getNewInstanceOfFooterItem();
        LibUtils.checkNotNull(footerItem, "Footer View item is null. You must override getNewInstanceOfFooterItem()");

        // decide position to be inserted on DataSet.
        final int position = getItemCount();
        addItem((T) footerItem, position);
        mUseFooterView = true;
    }

    @SuppressWarnings("unchecked")
    public void registerHeaderView() {
        if (mUseHeaderView) {
            throw new IllegalArgumentException("Header item is inserted already.");
        }
        final AbstractViewBinder avb = getNewInstanceOfHeaderViewBinder();
        LibUtils.checkNotNull(avb, "Header View Binder is null. You must override getNewInstanceOfHeaderViewBinder()");
        addViewBinder(avb);

        final RecyclerViewHeaderItem headerItem = getNewInstanceOfHeaderItem();
        LibUtils.checkNotNull(headerItem, "Header View Item is null. You must override getNewInstanceOfHeaderItem()");

        addItem((T) headerItem, 0);
        mUseHeaderView = true;
    }

    public void unregisterFooterView() {
        removeFooterItem();
        removeViewBinder(AbstractViewBinder.RECYCLER_FOOTER_VIEW_TYPE);
        mUseFooterView = false;
    }

    public void unregisterHeaderView() {
        removeHeaderItem();
        removeViewBinder(AbstractViewBinder.RECYCLER_HEADER_VIEW_TYPE);
        mUseHeaderView = false;
    }

    @SuppressWarnings("unchecked")
    public void updateFooterViewStatus(ViewStatus code, boolean applyOneCycle) {
        final RecyclerViewFooterItem footerItem = getRecyclerFooterViewItemFromDataSet();
        LibUtils.checkNotNull(footerItem, "Footer View item is null. You must override getNewInstanceOfFooterItem()");
        footerItem.setStatusCode(code, applyOneCycle);
        replaceItem((T) footerItem, getItemCount() - 1);
    }

    @SuppressWarnings("unchecked")
    public void updateHeaderViewStatus(ViewStatus code, boolean applyOneCycle) {
        final RecyclerViewHeaderItem headerItem = getRecyclerHeaderViewItemFromDataSet();
        LibUtils.checkNotNull(headerItem, "Header View item is null. You must override getNewInstanceOfHeaderItem()");
        headerItem.setStatusCode(code, applyOneCycle);
        replaceItem((T) headerItem, 0);
    }

    public boolean headerViewStatusCodeIsOneCycle() {
        return getRecyclerHeaderViewItemFromDataSet().isApplyOneCycle();
    }

    public boolean footerViewStatusCodeIsOneCycle() {
        return getRecyclerFooterViewItemFromDataSet().isApplyOneCycle();
    }

    @SuppressWarnings("unchecked")
    public void updateHeaderViewStatusCodeForOneCycle(boolean applyOneCycle) {
        final RecyclerViewHeaderItem headerItem = getRecyclerHeaderViewItemFromDataSet();
        LibUtils.checkNotNull(headerItem, "Header View item is null. You must override getNewInstanceOfHeaderItem()");
        headerItem.setApplyOneCycle(applyOneCycle);
        replaceItem((T) headerItem, 0);
    }

    @SuppressWarnings("unchecked")
    public void updateFooterViewStatusCodeForOneCycle(boolean applyOneCycle) {
        final RecyclerViewFooterItem footerItem = getRecyclerFooterViewItemFromDataSet();
        LibUtils.checkNotNull(footerItem, "Footer View item is null. You must override getNewInstanceOfFooterItem()");
        footerItem.setApplyOneCycle(applyOneCycle);
        replaceItem((T) footerItem, getItemCount() - 1);
    }

    protected RecyclerViewHeaderItem getRecyclerHeaderViewItemFromDataSet() {
        if (!mUseHeaderView) {
            throw new RuntimeException(
                  "You are not using the header view. You can't update the code of the header view.");
        }
        return (RecyclerViewHeaderItem) getItem(0);
    }

    protected RecyclerViewFooterItem getRecyclerFooterViewItemFromDataSet() {
        if (!mUseFooterView) {
            throw new RuntimeException(
                  "You are not using the footer view. You can't update the code of the footer view.");
        }
        return (RecyclerViewFooterItem) getItem(getItemCount() - 1);
    }

    // ========================================================================
    // Inner and Anonymous Classes
    // ========================================================================
}
