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

package com.seongil.recyclerviewlife.sample.ui.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.seongil.recyclerviewlife.LibUtils;
import com.seongil.recyclerviewlife.sample.R;
import com.seongil.recyclerviewlife.sample.application.MainApplication;
import com.seongil.recyclerviewlife.sample.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author seong-il, kim
 * @since 17. 4. 7
 */
public class OptionDialogFragment extends DialogFragment {

    // ========================================================================
    // constants
    // ========================================================================
    public static final String KEY_LOADING_ITEM_COUNT = "item_count_per_list";
    public static final String KEY_LOADING_ITEM_COUNT_PER_CYCLE = "loading_item_count_per_cycle";
    public static final String KEY_LOADING_DIRECTION = "loading_direction";
    public static final String KEY_LOADING_MODE = "loading_mode";
    public static final String KEY_LOADING_FINISHED_ACTION = "loaded_whole_items";

    public static final int LOADING_ITEM_MIN_CNT = 50;
    public static final int LOADING_ITEM_MIN_CNT_PER_CYCLE = 5;
    public static final int LOADING_ITEM_MAX_CNT_PER_CYCLE = 20;

    public static final int LOADING_HEADER = 1;
    public static final int LOADING_FOOTER = 2;
    public static final int LOADING_BOTH = 3;

    public static final int LOADING_INFINITE = 1;
    public static final int LOADING_TOUCH = 2;

    public static final int HIDE_HEADER_FOOTER = 1;
    public static final int SHOW_TOUCH_LABEL = 2;

    // ========================================================================
    // fields
    // ========================================================================
    @BindView(R.id.radio_group_loading_direction)
    RadioGroup mLoadingDirectionRadioGroup;

    @BindView(R.id.rb_dir_header_view)
    RadioButton mDirHeaderView;

    @BindView(R.id.rb_dir_footer_view)
    RadioButton mDirFooterView;

    @BindView(R.id.rb_dir_both)
    RadioButton mDirBoth;

    @BindView(R.id.radio_group_loading_mode)
    RadioGroup mLoadingModeRadioGroup;

    @BindView(R.id.rb_infinite_loading)
    RadioButton mLoadingInfinite;

    @BindView(R.id.rb_touch_loading)
    RadioButton mLoadingTouch;

    @BindView(R.id.radio_group_after_loading_whole_items)
    RadioGroup mAfterLoadingWholeItems;

    @BindView(R.id.rb_hide_header_footer_item)
    RadioButton mHideHeaderFooter;

    @BindView(R.id.rb_show_touch_label)
    RadioButton mShowTouchLabel;

    @BindView(R.id.input_item_count)
    EditText mEntireLoadingItemCnt;

    @BindView(R.id.input_loading_item_count)
    EditText mCycleLoadingItemCnt;

    private int mItemCount = LOADING_ITEM_MIN_CNT;
    private int mItemCountPerCycle = LOADING_ITEM_MIN_CNT_PER_CYCLE;
    private int mLoadingDirection = LOADING_FOOTER;
    private int mLoadingMode = LOADING_INFINITE;
    private int mLoadingFinishedAction = HIDE_HEADER_FOOTER;
    private OptionDialogListener mCallback;

    // ========================================================================
    // constructors
    // ========================================================================
    public static synchronized OptionDialogFragment newInstance(
          int loadingItemCnt, int loadingItemCntPerCycler,
          int loadingDirection, int loadingMode, int actionAfterFinishedLoading) {

        OptionDialogFragment fragment = new OptionDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LOADING_ITEM_COUNT, loadingItemCnt);
        bundle.putInt(KEY_LOADING_ITEM_COUNT_PER_CYCLE, loadingItemCntPerCycler);
        bundle.putInt(KEY_LOADING_DIRECTION, loadingDirection);
        bundle.putInt(KEY_LOADING_MODE, loadingMode);
        bundle.putInt(KEY_LOADING_FINISHED_ACTION, actionAfterFinishedLoading);
        fragment.setArguments(bundle);
        return fragment;
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_options, container);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OptionDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Option Dialog");
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mItemCount = bundle.getInt(KEY_LOADING_ITEM_COUNT, LOADING_ITEM_MIN_CNT);
            mItemCountPerCycle =
                  bundle.getInt(KEY_LOADING_ITEM_COUNT_PER_CYCLE, LOADING_ITEM_MIN_CNT_PER_CYCLE);
            mLoadingDirection = bundle.getInt(KEY_LOADING_DIRECTION, LOADING_FOOTER);
            mLoadingMode = bundle.getInt(KEY_LOADING_MODE, LOADING_INFINITE);
            mLoadingFinishedAction = bundle.getInt(KEY_LOADING_FINISHED_ACTION, HIDE_HEADER_FOOTER);
        }

        if (mItemCount > 0) {
            mEntireLoadingItemCnt.setText(String.valueOf(mItemCount));
        }

        if (mItemCountPerCycle > 0) {
            mCycleLoadingItemCnt.setText(String.valueOf(mItemCountPerCycle));
        }

        if (LOADING_HEADER == mLoadingDirection) {
            mDirHeaderView.setChecked(true);
        } else if (LOADING_FOOTER == mLoadingDirection) {
            mDirFooterView.setChecked(true);
        } else {
            mDirBoth.setChecked(true);
        }

        if (mLoadingMode == LOADING_INFINITE) {
            mLoadingInfinite.setChecked(true);
        } else {
            mLoadingTouch.setChecked(true);
        }

        if (mLoadingFinishedAction == HIDE_HEADER_FOOTER) {
            mHideHeaderFooter.setChecked(true);
        } else {
            mShowTouchLabel.setChecked(true);
        }
    }

    @OnClick(R.id.btn_cancel)
    public void onClickedCancelBtn() {
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_confirm)
    public void onClickedConfirmBtn() {
        LibUtils.checkNotNull(mCallback, "Callback listener is null. Check it again.");
        String inputText = mEntireLoadingItemCnt.getText().toString();
        if (TextUtils.isEmpty(inputText)) {
            ToastUtils.showMsg(MainApplication.getAppContext(), "You must input the valid item count.");
            return;
        }
        int inputCount = Integer.parseInt(inputText);
        if (inputCount < LOADING_ITEM_MIN_CNT) {
            ToastUtils.showMsg(MainApplication.getAppContext(),
                  "The minimum count is " + LOADING_ITEM_MIN_CNT + ". Please, input the count again.");
            return;
        }
        mItemCount = inputCount;

        inputText = mCycleLoadingItemCnt.getText().toString();
        if (TextUtils.isEmpty(inputText)) {
            ToastUtils.showMsg(MainApplication.getAppContext(), "You must input the valid item count.");
            return;
        }
        inputCount = Integer.parseInt(inputText);
        if (inputCount < LOADING_ITEM_MIN_CNT_PER_CYCLE || inputCount > LOADING_ITEM_MAX_CNT_PER_CYCLE) {
            ToastUtils.showMsg(MainApplication.getAppContext(), "You must input a valid count between "
                  + LOADING_ITEM_MIN_CNT_PER_CYCLE + " and " + LOADING_ITEM_MAX_CNT_PER_CYCLE);
            return;
        }
        mItemCountPerCycle = inputCount;

        switch (mLoadingDirectionRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_dir_header_view:
                mLoadingDirection = LOADING_HEADER;
                break;
            case R.id.rb_dir_footer_view:
                mLoadingDirection = LOADING_FOOTER;
                break;
            case R.id.rb_dir_both:
                mLoadingDirection = LOADING_BOTH;
                break;
            default:
                mLoadingDirection = LOADING_BOTH;
                break;
        }

        switch (mLoadingModeRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_infinite_loading:
                mLoadingMode = LOADING_INFINITE;
                break;
            case R.id.rb_touch_loading:
                mLoadingMode = LOADING_TOUCH;
                break;
            default:
                mLoadingMode = LOADING_INFINITE;
                break;
        }

        switch (mAfterLoadingWholeItems.getCheckedRadioButtonId()) {
            case R.id.rb_hide_header_footer_item:
                mLoadingFinishedAction = HIDE_HEADER_FOOTER;
                break;
            case R.id.rb_show_touch_label:
                mLoadingFinishedAction = SHOW_TOUCH_LABEL;
                break;
            default:
                mLoadingFinishedAction = SHOW_TOUCH_LABEL;
                break;
        }

        mCallback.onFinishedOptionSelection(
              mItemCount, mItemCountPerCycle, mLoadingDirection, mLoadingMode, mLoadingFinishedAction);
        dismiss();
    }
    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
    public interface OptionDialogListener {

        // ===========================================================
        // Constants
        // ===========================================================

        // ===========================================================
        // Methods
        // ===========================================================
        void onFinishedOptionSelection(
              int loadingItemCnt, int loadingItemCntPerCycler,
              int loadingDirection, int loadingMode, int actionAfterFinishedLoading);
    }
}
