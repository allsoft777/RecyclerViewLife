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

import android.view.LayoutInflater;

import com.seongil.recyclerviewlib.lib.model.RecyclerViewFooterItem;
import com.seongil.recyclerviewlib.lib.model.RecyclerViewHeaderItem;
import com.seongil.recyclerviewlib.lib.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlib.lib.model.common.ViewStatus;
import com.seongil.recyclerviewlib.lib.single.RecyclerListViewAdapter;
import com.seongil.recyclerviewlib.lib.single.viewbinder.AbstractFooterViewBinder;
import com.seongil.recyclerviewlib.lib.single.viewbinder.AbstractHeaderViewBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author seong-il, kim
 * @since: 17. 1. 17
 */
public class MockRecyclerViewAdapterWithHeaderFooterView extends RecyclerListViewAdapter<RecyclerViewItem> {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================
    public MockRecyclerViewAdapterWithHeaderFooterView(LayoutInflater layoutInflater) {
        super(layoutInflater);
        List<RecyclerViewItem> dataSet = new ArrayList<>();
        setDataSet(dataSet);
        setNotifyObservers(false);
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    protected AbstractHeaderViewBinder getNewInstanceOfHeaderViewBinder() {
        return new MockHeaderViewBinder(mLayoutInflater, null);
    }

    @Override
    protected AbstractFooterViewBinder getNewInstanceOfFooterViewBinder() {
        return new MockFooterViewBinder(mLayoutInflater, null);
    }

    @Override
    protected RecyclerViewFooterItem getNewInstanceOfFooterItem() {
        return new MockFooterViewItem(ViewStatus.IDLE);
    }

    @Override
    protected RecyclerViewHeaderItem getNewInstanceOfHeaderItem() {
        return new MockHeaderViewItem(ViewStatus.IDLE);
    }

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
