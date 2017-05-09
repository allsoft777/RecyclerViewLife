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
import android.view.ViewGroup;

import com.seongil.recyclerviewlife.LibUtils;
import com.seongil.recyclerviewlife.RecyclerListViewListener;
import com.seongil.recyclerviewlife.model.RecyclerViewFooterItem;
import com.seongil.recyclerviewlife.model.RecyclerViewHeaderFooterItem;
import com.seongil.recyclerviewlife.model.RecyclerViewHeaderItem;
import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractViewBinder;
import com.seongil.recyclerviewlife.single.viewbinder.ViewBinder;
import com.seongil.recyclerviewlife.single.viewbinder.ViewBinderListManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author seong-il, kim
 * @since 17. 3. 21
 */
public abstract class AbstractRecyclerViewAdapter<T extends RecyclerViewItem>
      extends RecyclerView.Adapter implements RecyclerListViewListener {

    // ========================================================================
    // Constants
    // ========================================================================

    // ========================================================================
    // Fields
    // ========================================================================
    private final ViewBinderListManager mViewBinderManager = new ViewBinderListManager();
    protected LayoutInflater mLayoutInflater;
    private List<T> mDataSet = new ArrayList<>();
    private boolean mNotifyObservers;

    // ========================================================================
    // Constructors
    // ========================================================================
    public AbstractRecyclerViewAdapter(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
        mNotifyObservers = true;
    }

    // ========================================================================
    // Getter & Setter
    // ========================================================================
    public void setNotifyObservers(boolean notifyObservers) {
        mNotifyObservers = notifyObservers;
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

    public void setData(@NonNull List<T> collection) {
        setData(collection, mNotifyObservers);
    }

    @SuppressWarnings("unchecked")
    public void setData(@NonNull List<T> collection, boolean notifyObservers) {
        if (registeredHeaderView()) {
            collection.add(0, (T) getHeaderItem());
        }
        if (registeredFooterView()) {
            collection.add(collection.size(), (T) getFooterItem());
        }
        mDataSet = collection;

        if (notifyObservers) {
            notifyItemRangeChanged(0, getItemCount());
        }
    }

    public void addCollectionToFirstPosition(@NonNull List<T> collection) {
        addCollectionToFirstPosition(collection, mNotifyObservers);
    }

    public void addCollectionToFirstPosition(@NonNull List<T> collection, boolean notifyObservers) {
        LibUtils.checkNotNull(collection, "Collection to add on the DataSet is null.");

        final int targetSize = collection.size();
        final int insertPos = registeredHeaderView() ? 1 : 0;
        mDataSet.addAll(insertPos, collection);

        if (notifyObservers) {
            notifyItemRangeInserted(-targetSize, targetSize);
        }
    }

    public void addCollectionToLastPosition(@NonNull List<T> collection) {
        addCollectionToLastPosition(collection, mNotifyObservers);
    }

    public void addCollectionToLastPosition(@NonNull List<T> collection, boolean notifyObservers) {
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

    public void addItemToLastPosition(T element) {
        addItemToLastPosition(element, mNotifyObservers);
    }

    public void addItemToLastPosition(T element, boolean notifyObservers) {
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

    public void addItemToFirstPosition(T element) {
        addItemToFirstPosition(element, mNotifyObservers);
    }

    public void addItemToFirstPosition(T element, boolean notifyObservers) {
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

    public void addItem(T element, int position) {
        addItem(element, position, mNotifyObservers);
    }

    public void addItem(T element, int position, boolean notifyObservers) {
        if (position < 0 || position > getItemCount()) {
            throw new IndexOutOfBoundsException("Position is invalid. : " + position);
        }
        mDataSet.add(position, element);

        if (notifyObservers) {
            notifyItemInserted(position);
        }
    }

    public void replaceItem(T element, int position) {
        replaceItem(element, position, mNotifyObservers);
    }

    public void replaceItem(T element, int position, boolean notifyObservers) {
        if (position < 0 || position >= getItemCount()) {
            throw new IndexOutOfBoundsException("Position is invalid. : " + position);
        }
        mDataSet.set(position, element);

        if (notifyObservers) {
            notifyItemChanged(position);
        }
    }

    public void removeLastItem() {
        removeLastItem(mNotifyObservers);
    }

    public void removeLastItem(boolean notifyObservers) {
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
        removeFirstItem(mNotifyObservers);
    }

    public void removeFirstItem(boolean notifyObservers) {
        if (getItemCount() == 0) {
            return;
        }

        mDataSet.remove(0);

        if (notifyObservers) {
            notifyItemRemoved(0);
        }
    }

    public void removePosition(int position) {
        removePosition(position, mNotifyObservers);
    }

    public void removePosition(int position, boolean notifyToObservers) {
        if (getItemCount() == 0) {
            return;
        }

        if (position < 0 || position >= getItemCount()) {
            throw new IndexOutOfBoundsException("Given position is invalid.");
        }

        mDataSet.remove(position);
        if (notifyToObservers) {
            notifyItemRemoved(position);
        }
    }

    public void clearDataSet() {
        clearDataSet(false, true);
    }

    public void clearDataSet(final boolean withHeaderFooterItem, final boolean notifyToObservers) {
        final int size = getItemCount();
        int startPos = 0, notifyItemCount = size;

        if (withHeaderFooterItem) {
            mDataSet.clear();
        } else {
            List<T> list = new ArrayList<>();
            if (registeredHeaderView()) {
                startPos = 1;
                list.add(mDataSet.get(0));
            }
            if (registeredFooterView()) {
                notifyItemCount = size - 1;
                if(registeredHeaderView()) {
                    notifyItemCount--;
                }
                if (notifyItemCount < 0) {
                    notifyItemCount = 0;
                }
                list.add(mDataSet.get(size - 1));
            }
            mDataSet = list;
        }
        if (notifyItemCount < 0) {
            notifyItemCount = 0;
        }

        if (notifyToObservers) {
            notifyItemRangeRemoved(startPos, notifyItemCount);
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

    public RecyclerViewHeaderItem getHeaderItem() {
        if (getItemCount() == 0) {
            throw new AssertionError("Data size is 0.");
        }

        RecyclerViewItem item = mDataSet.get(0);
        if (!(item instanceof RecyclerViewHeaderItem)) {
            throw new RuntimeException("There is no header item in the list.");
        }
        return (RecyclerViewHeaderItem) item;
    }

    public RecyclerViewFooterItem getFooterItem() {
        if (getItemCount() == 0) {
            throw new AssertionError("Data size is 0.");
        }

        RecyclerViewItem item = mDataSet.get(getItemCount() - 1);
        if (!(item instanceof RecyclerViewFooterItem)) {
            throw new RuntimeException("There is no footer item in the list.");
        }
        return (RecyclerViewFooterItem) item;
    }

    public void replaceHeaderItem(T element) {
        replaceHeaderItem(element, mNotifyObservers);
    }

    public void replaceHeaderItem(T element, boolean notifyObservers) {
        if (getItemCount() == 0 || !registeredHeaderView()) {
            throw new AssertionError("There is no header item to replace with the given item.");
        }
        mDataSet.set(0, element);
        if (notifyObservers) {
            notifyItemChanged(0);
        }
    }

    public void replaceFooterItem(T element) {
        replaceFooterItem(element, mNotifyObservers);
    }

    public void replaceFooterItem(T element, boolean notifyObservers) {
        if (getItemCount() == 0 || !registeredFooterView()) {
            throw new AssertionError("There is no footer item to replace with the given item.");
        }
        mDataSet.set(getItemCount() - 1, element);
        if (notifyObservers) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public T getItem(int position) {
        return mDataSet.get(position);
    }

    @SuppressWarnings("unchecked")
    public List<T> getDataSet() {
        return getDataSet(true);
    }

    public List<T> getDataSet(final boolean withHeaderFooterItem) {
        if (withHeaderFooterItem) {
            return mDataSet;
        }
        final List<T> result = new ArrayList<>();
        for (T item : mDataSet) {
            if (item instanceof RecyclerViewHeaderFooterItem) {
                continue;
            }
            result.add(item);
        }
        return result;
    }

    // ========================================================================
    // Inner and Anonymous Classes
    // ========================================================================
}
