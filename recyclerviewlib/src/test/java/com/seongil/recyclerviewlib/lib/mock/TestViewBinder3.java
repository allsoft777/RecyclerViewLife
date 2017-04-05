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

package com.seongil.recyclerviewlib.lib.mock;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.seongil.recyclerviewlib.lib.model.common.RecyclerViewItem;

/**
 * @author seong-il, kim
 * @since: 17. 1. 17
 */
public class TestViewBinder3 extends TestBaseViewBinder {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================
    public TestViewBinder3(
            int viewType,
            @NonNull LayoutInflater inflater,
            @Nullable RecyclerViewItemClickListener itemClickListener,
            @Nullable RecyclerViewItemLongClickListener itemLongClickListener) {
        super(viewType, inflater, itemClickListener, itemLongClickListener);
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    public boolean isForViewType(@NonNull RecyclerViewItem item) {
        return item instanceof TestRecyclerViewItem3;
    }

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
