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

package com.seongil.recyclerviewlib.sample.transaction.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.seongil.recyclerviewlib.lib.single.RecyclerListViewAdapter;
import com.seongil.recyclerviewlib.lib.single.viewbinder.AbstractViewBinder;

import java.util.ArrayList;

/**
 * @author seongil2.kim
 * @since: 17. 1. 16
 */
public class TransactionListAdapter extends RecyclerListViewAdapter {

    // ========================================================================
    // constants
    // ========================================================================
    private static final int VIEW_TYPE_TRANSACTION = 1;
    private static final int VIEW_TYPE_ADVERTISEMENT = 2;

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================
    public TransactionListAdapter(
        @NonNull LayoutInflater layoutInflater,
        @Nullable AbstractViewBinder.RecyclerViewItemClickListener viewItemClickListener) {
        super(layoutInflater);
        setDataSet(new ArrayList());

        addViewBinder(new TransactionViewBinder(VIEW_TYPE_TRANSACTION, layoutInflater, viewItemClickListener));
        addViewBinder(new TransactionAdvertisementViewBinder(VIEW_TYPE_ADVERTISEMENT, layoutInflater, viewItemClickListener));
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
