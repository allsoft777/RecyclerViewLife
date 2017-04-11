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

package com.seongil.recyclerviewlife.mock;

import android.view.LayoutInflater;

import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlife.single.AbstractRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author seong-il, kim
 * @since 17. 1. 17
 */
public class MockRecyclerViewAdapterSimple extends AbstractRecyclerViewAdapter<RecyclerViewItem> {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================
    public MockRecyclerViewAdapterSimple(LayoutInflater layoutInflater, TestViewBinder1 binder1) {
        super(layoutInflater);
        setNotifyObservers(false);

        List<RecyclerViewItem> items = new ArrayList<>();
        addLastCollection(items);
        addViewBinder(binder1);
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    public boolean registeredFooterView() {
        return false;
    }

    @Override
    public boolean registeredHeaderView() {
        return false;
    }

    @Override
    public boolean loadFooterItemsMore() {
        return false;
    }

    @Override
    public boolean loadHeaderItemsMore() {
        return false;
    }


    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
