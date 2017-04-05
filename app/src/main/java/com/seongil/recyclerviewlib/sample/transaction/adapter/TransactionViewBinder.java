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

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seongil.recyclerviewlib.lib.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlib.lib.single.viewbinder.AbstractViewBinder;
import com.seongil.recyclerviewlib.sample.R;
import com.seongil.recyclerviewlib.sample.model.Transaction;
import com.seongil.recyclerviewlib.sample.utils.RxFormatObservables;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author seongil2.kim
 * @since: 17. 1. 16
 */
public class TransactionViewBinder extends AbstractViewBinder {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    private static final SimpleDateFormat dateTimeFormat =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    private static final String CURRENCY_FORMAT =
        Currency.getInstance(Locale.KOREA).getSymbol() + "#,###";

    // ========================================================================
    // constructors
    // ========================================================================
    public TransactionViewBinder(
        int viewType,
        @NonNull LayoutInflater inflater,
        @NonNull RecyclerViewItemClickListener itemClickListener) {
        super(viewType, inflater, itemClickListener);
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    public boolean isForViewType(@NonNull RecyclerViewItem item) {
        return item instanceof Transaction;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new TransactionItemViewHolder(
            mLayoutInflater.inflate(R.layout.list_item_transactions_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItem item,
                                 @NonNull final RecyclerView.ViewHolder holder) {
        final Transaction data = (Transaction) item;
        final TransactionItemViewHolder viewHolder = (TransactionItemViewHolder) holder;
        final Resources res = viewHolder.itemView.getResources();

        RxFormatObservables
            .build(dateTimeFormat, data.getTransactionTimeInMillis())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((date) -> viewHolder.dateTime.setText(date));

        RxFormatObservables
            .decimalFormat(CURRENCY_FORMAT, data.getAmount())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((amount) -> viewHolder.amount.setText(amount));

        viewHolder.transactionType.setText(data.getTransactionType());

        holder.itemView.setOnClickListener((v) ->
            mItemViewClickListener.onClickedRecyclerViewItem(holder, data, holder.getLayoutPosition()));
    }

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
    public static class TransactionItemViewHolder extends RecyclerView.ViewHolder {

        public final TextView dateTime;
        public final TextView transactionType;
        public final TextView amount;

        public TransactionItemViewHolder(View view) {
            super(view);
            dateTime = (TextView) view.findViewById(R.id.date_time);
            transactionType = (TextView) view.findViewById(R.id.transaction_type);
            amount = (TextView) view.findViewById(R.id.amount);
        }
    }
}
