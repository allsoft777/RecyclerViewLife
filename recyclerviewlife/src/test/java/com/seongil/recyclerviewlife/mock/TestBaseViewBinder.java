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

package com.seongil.recyclerviewlife.mock;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractViewBinder;

import java.lang.reflect.Field;

/**
 * @author seong-il, kim
 * @since 17. 1. 17
 */
public abstract class TestBaseViewBinder extends AbstractViewBinder {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    public boolean onCreateViewHolderCalled = false;
    public boolean onBindViewHolderCalled = false;

    public RecyclerView.ViewHolder viewHolder;

    // ========================================================================
    // constructors
    // ========================================================================
    public TestBaseViewBinder(
          int viewType,
          @NonNull LayoutInflater inflater,
          @Nullable RecyclerViewItemClickListener itemClickListener,
          @Nullable RecyclerViewItemLongClickListener itemLongClickListener) {
        super(viewType, inflater, itemClickListener, itemLongClickListener);

        viewHolder = new RecyclerView.ViewHolder(new View(null)) {
        };

        try {
            Field viewTypeField = RecyclerView.ViewHolder.class.
                  getDeclaredField("mItemViewType");

            viewTypeField.setAccessible(true);
            viewTypeField.set(viewHolder, viewType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        onCreateViewHolderCalled = true;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItem item, @NonNull RecyclerView.ViewHolder holder) {
        onBindViewHolderCalled = true;
    }

    // ========================================================================
    // methods
    // ========================================================================
    public void reset() {
        onCreateViewHolderCalled = false;
        onBindViewHolderCalled = false;
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
