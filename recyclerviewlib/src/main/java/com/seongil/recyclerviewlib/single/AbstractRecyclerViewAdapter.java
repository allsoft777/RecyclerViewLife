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

package com.seongil.recyclerviewlib.single;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.seongil.recyclerviewlib.LibUtils;
import com.seongil.recyclerviewlib.RecyclerListViewListener;
import com.seongil.recyclerviewlib.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlib.model.RecyclerViewFooterItem;
import com.seongil.recyclerviewlib.model.RecyclerViewHeaderItem;
import com.seongil.recyclerviewlib.single.viewbinder.AbstractViewBinder;
import com.seongil.recyclerviewlib.single.viewbinder.ViewBinder;
import com.seongil.recyclerviewlib.single.viewbinder.ViewBinderListManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author seong-il, kim
 * @since 17. 3. 21
 */
public abstract class AbstractRecyclerViewAdapter<T extends RecyclerViewItem>
    extends RecyclerView.Adapter
    implements RecyclerListViewListener {

    // ========================================================================
    // Constants
    // ========================================================================

    // ========================================================================
    // Fields
    // ========================================================================
    private final ViewBinderListManager mViewBinderManager = new ViewBinderListManager();
    protected LayoutInflater mLayoutInflater;
    private List<T> mDataSet = new ArrayList<>();
    private boolean notifyObservers;

    // ========================================================================
    // Constructors
    // ========================================================================
    public AbstractRecyclerViewAdapter(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
        notifyObservers = true;
    }

    // ========================================================================
    // Getter & Setter
    // ========================================================================
    @SuppressWarnings("unchecked")
    public List<T> getDataSet() {
        return mDataSet;
    }

    @SuppressWarnings("unchecked")
    public void setDataSet(List<T> dataSet) {
        mDataSet = dataSet;
    }

    public void setNotifyObservers(boolean notifyObservers) {
        this.notifyObservers = notifyObservers;
    }

    // ========================================================================
    // Methods for/from SuperClass/Interfaces
    // ========================================================================
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mViewBinderManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mViewBinderManager.onBindViewHolder(mDataSet.get(position), holder);
    }

    @Override
    public int getItemViewType(int position) {
        return mViewBinderManager.getItemViewType(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    // ========================================================================
    // Methods
    // ========================================================================
    protected void clearViewBinders() {
        mViewBinderManager.clear();
    }

    @SuppressWarnings("unchecked")
    protected boolean addViewBinder(@NonNull AbstractViewBinder cvb) {
        return mViewBinderManager.addViewBinder(cvb);
    }

    protected boolean removeViewBinder(int viewType) {
        return mViewBinderManager.removeViewBinder(viewType);
    }

    @SuppressWarnings("unchecked")
    public boolean existViewBinder(ViewBinder viewBinder) {
        return mViewBinderManager.existViewBinder(viewBinder);
    }

    public int getViewBindersCount() {
        return mViewBinderManager.size();
    }

    public void addFirstCollection(List<T> collection) {
        LibUtils.checkNotNull(collection, "Collection to add on the DataSet is null.");

        final int targetSize = collection.size();
        final int insertPos = registeredHeaderView() ? 1 : 0;
        mDataSet.addAll(insertPos, collection);

        if (notifyObservers) {
            notifyItemRangeInserted(-targetSize, targetSize);
        }
    }

    public void addLastCollection(List<T> collection) {
        LibUtils.checkNotNull(collection, "Collection to add on the DataSet is null.");

        final int targetSize = collection.size();
        int insertPos = getItemCount();

        if (registeredFooterView()) {
            if (getItemCount() == 1) {
                insertPos = 0;
            } else if (getItemCount() > 1) {
                insertPos = getItemCount() - 1;
            }
        }
        mDataSet.addAll(insertPos, collection);

        if (notifyObservers) {
            notifyItemRangeInserted(insertPos + 1, targetSize);
        }
    }

    public void addLast(T element) {
        int insertionPos;
        if (registeredFooterView()) {
            insertionPos = getItemCount() == 0 ? 0 : getItemCount() - 1;
            mDataSet.add(insertionPos, element);
        } else {
            insertionPos = getItemCount();
            mDataSet.add(insertionPos, element);
        }

        if (notifyObservers) {
            notifyItemInserted(insertionPos);
        }
    }

    public void addFirst(T element) {
        int insertionPos;
        if (registeredHeaderView()) {
            insertionPos = getItemCount() == 0 ? 0 : 1;
        } else {
            insertionPos = 0;
        }
        mDataSet.add(insertionPos, element);

        if (notifyObservers) {
            notifyItemInserted(insertionPos);
        }
    }

    public void addPosition(T element, int position) {
        if (position < 0 || position > getItemCount()) {
            throw new IndexOutOfBoundsException("position is invalid. : " + position);
        }
        mDataSet.add(position, element);

        if (notifyObservers) {
            notifyItemInserted(position);
        }
    }

    public void removeLastItem() {
        if (getItemCount() == 0) {
            return;
        }

        final int removeItemPos = getItemCount() - 1;
        mDataSet.remove(removeItemPos);

        if (notifyObservers) {
            notifyItemRemoved(removeItemPos);
        }
    }

    public void removeFirstItem() {
        if (getItemCount() == 0) {
            return;
        }

        mDataSet.remove(0);

        if (notifyObservers) {
            notifyItemRemoved(0);
        }
    }

    public void updatePositionWithNotify(T element, int position) {
        replaceElement(element, position);
        notifyItemChanged(position);
    }

    public void replaceElement(T object, int position) {
        mDataSet.set(position, object);
    }

    public void clearDataSet() {
        final int size = getItemCount();
        mDataSet.clear();

        if (notifyObservers) {
            notifyItemRangeRemoved(0, size);
        }
    }

    protected void removeFooterItem() {
        if (!registeredFooterView()) {
            throw new IllegalArgumentException("Adapter is not using the footer view");
        }
        if (getItemCount() == 0) {
            throw new IndexOutOfBoundsException("There is no items in the DataSet");
        }
        if (!(mDataSet.get(getItemCount() - 1) instanceof RecyclerViewFooterItem)) {
            throw new RuntimeException("The last item of the DataSet MUST be an instance of RecyclerViewFooterItem");
        }

        removeLastItem();
    }

    protected void removeHeaderItem() {
        if (!registeredHeaderView()) {
            throw new IllegalArgumentException("Adapter is not using the header view");
        }
        if (getItemCount() == 0) {
            throw new IndexOutOfBoundsException("There is no items in the DataSet");
        }
        if (!(mDataSet.get(0) instanceof RecyclerViewHeaderItem)) {
            throw new RuntimeException("The first item of the DataSet MUST be an instance of RecyclerViewHeaderItem");
        }

        removeFirstItem();
    }

    public void replaceElement(int position, T element) {
        if (getItemCount() <= position) {
            throw new IllegalArgumentException("Position is invalid");
        }
        mDataSet.set(position, element);

        if (notifyObservers) {
            notifyItemChanged(position);
        }
    }

    protected T getItem(int position) {
        return mDataSet.get(position);
    }

    // ========================================================================
    // Inner and Anonymous Classes
    // ========================================================================
}
