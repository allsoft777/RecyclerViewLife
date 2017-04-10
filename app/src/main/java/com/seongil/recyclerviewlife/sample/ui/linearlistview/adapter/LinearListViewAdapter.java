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

package com.seongil.recyclerviewlife.sample.ui.linearlistview.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.seongil.recyclerviewlife.model.common.ViewStatus;
import com.seongil.recyclerviewlife.sample.model.ClientFooterItem;
import com.seongil.recyclerviewlife.sample.model.ClientHeaderItem;
import com.seongil.recyclerviewlife.sample.ui.dialog.OptionDialogFragment;
import com.seongil.recyclerviewlife.sample.ui.linearlistview.adapter.viewbinder.LinearListAdvertisementViewBinder;
import com.seongil.recyclerviewlife.sample.ui.linearlistview.adapter.viewbinder.LinearListFooterViewBinder;
import com.seongil.recyclerviewlife.sample.ui.linearlistview.adapter.viewbinder.LinearListHeaderViewBinder;
import com.seongil.recyclerviewlife.sample.ui.linearlistview.adapter.viewbinder.LinearListThumbnailTextViewBinder;
import com.seongil.recyclerviewlife.sample.ui.linearlistview.adapter.viewbinder.LinearListTwoTextViewBinder;
import com.seongil.recyclerviewlife.single.RecyclerListViewAdapter;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractFooterViewBinder;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractHeaderViewBinder;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractViewBinder.RecyclerViewItemClickListener;

/**
 * @author seong-il, kim
 * @since 17. 4. 8
 */
public class LinearListViewAdapter extends RecyclerListViewAdapter {

    // ========================================================================
    // constants
    // ========================================================================
    private static final int VIEW_TYPE_TITLE_DATE = 1;
    private static final int VIEW_TYPE_THUMBNAIL_TITLE = 2;
    private static final int VIEW_TYPE_ADVERTISEMENT = 3;

    // ========================================================================
    // fields
    // ========================================================================
    private final int mLoadingMode;
    @Nullable
    private RecyclerViewItemClickListener mViewItemClickListener;

    // ========================================================================
    // constructors
    // ========================================================================
    @SuppressWarnings("unchecked")
    public LinearListViewAdapter(int loadingMode, // For testing
          @NonNull LayoutInflater layoutInflater,
          @Nullable RecyclerViewItemClickListener viewItemClickListener) {
        super(layoutInflater);
        mLoadingMode = loadingMode;
        mViewItemClickListener = viewItemClickListener;

        addViewBinder(
              new LinearListTwoTextViewBinder(
                    VIEW_TYPE_TITLE_DATE, layoutInflater, viewItemClickListener));
        addViewBinder(
              new LinearListThumbnailTextViewBinder(
                    VIEW_TYPE_THUMBNAIL_TITLE, layoutInflater, viewItemClickListener));
        addViewBinder(
              new LinearListAdvertisementViewBinder(
                    VIEW_TYPE_ADVERTISEMENT, layoutInflater, viewItemClickListener));
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    protected AbstractFooterViewBinder getNewInstanceOfFooterViewBinder() {
        return new LinearListFooterViewBinder(mLayoutInflater, mViewItemClickListener);
    }

    @Override
    protected ClientFooterItem getNewInstanceOfFooterItem() {
        ViewStatus code = ViewStatus.VISIBLE_LOADING_VIEW;
        if (OptionDialogFragment.LOADING_TOUCH == mLoadingMode) {
            code = ViewStatus.VISIBLE_LABEL_VIEW;
        }
        return new ClientFooterItem(code);
    }

    @Override
    protected AbstractHeaderViewBinder getNewInstanceOfHeaderViewBinder() {
        return new LinearListHeaderViewBinder(mLayoutInflater, mViewItemClickListener);
    }

    @Override
    protected ClientHeaderItem getNewInstanceOfHeaderItem() {
        ViewStatus code = ViewStatus.VISIBLE_LOADING_VIEW;
        if (OptionDialogFragment.LOADING_TOUCH == mLoadingMode) {
            code = ViewStatus.VISIBLE_LABEL_VIEW;
        }
        return new ClientHeaderItem(code);
    }

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
