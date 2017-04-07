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

package com.seongil.recyclerviewlib.decor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author seong-il, kim
 * @since: 17. 1. 10
 */
@SuppressWarnings("unused")
public class RecyclerViewDividerItemDecor extends RecyclerView.ItemDecoration {

    // ========================================================================
    // Constants
    // ========================================================================

    // ========================================================================
    // Fields
    // ========================================================================
    private final Drawable mDividerDrawable;
    private final int mOrientation;
    private boolean mHideLastItemDivider;
    private boolean mHideFirstItemDivider;

    // ========================================================================
    // Constructors
    // ========================================================================
    public RecyclerViewDividerItemDecor(
          Context context, int orientation, @DrawableRes int resId) {
        mDividerDrawable = ContextCompat.getDrawable(context, resId);
        if (orientation != RecyclerView.VERTICAL && orientation != RecyclerView.HORIZONTAL) {
            throw new IllegalArgumentException("You have to pass a valid orientation " +
                  "using in RecyclerView. You passed a value of " + orientation);
        }
        mOrientation = orientation;
    }

    // ========================================================================
    // Getter & Setter
    // ========================================================================
    public void hideLastItemDivider(boolean hideLastItemDivider) {
        mHideLastItemDivider = hideLastItemDivider;
    }

    public void hideFirstItemDivider(boolean hideFirstItemDivider) {
        mHideFirstItemDivider = hideFirstItemDivider;
    }

    // ========================================================================
    // Methods for/from SuperClass/Interfaces
    // ========================================================================
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == RecyclerView.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == RecyclerView.VERTICAL) {
            outRect.set(0, 0, 0, mDividerDrawable.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDividerDrawable.getIntrinsicWidth(), 0);
        }
    }

    // ========================================================================
    // Methods
    // ========================================================================
    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        View child;
        RecyclerView.LayoutParams params;
        int top, bottom;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (hideDividerDrawable(childCount, i)) {
                left = 0;
                top = 0;
                right = 0;
                bottom = 0;
            } else {
                child = parent.getChildAt(i);
                params = (RecyclerView.LayoutParams) child.getLayoutParams();
                top = child.getBottom() + params.bottomMargin;
                bottom = top + mDividerDrawable.getIntrinsicHeight();
            }
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        View child;
        RecyclerView.LayoutParams params;
        int left, right;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (hideDividerDrawable(childCount, i)) {
                left = 0;
                top = 0;
                right = 0;
                bottom = 0;
            } else {
                child = parent.getChildAt(i);
                params = (RecyclerView.LayoutParams) child.getLayoutParams();
                left = child.getRight() + params.rightMargin;
                right = left + mDividerDrawable.getIntrinsicHeight();
            }
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
        }
    }

    private boolean hideDividerDrawable(int childCount, int currentPosition) {
        if (mHideLastItemDivider && currentPosition == childCount - 1) {
            return true;
        }
        return mHideFirstItemDivider && currentPosition == 0;
    }

    // ========================================================================
    // Inner and Anonymous Classes
    // ========================================================================
}
