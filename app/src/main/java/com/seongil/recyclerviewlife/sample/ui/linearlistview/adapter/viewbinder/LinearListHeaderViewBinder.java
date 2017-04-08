package com.seongil.recyclerviewlife.sample.ui.linearlistview.adapter.viewbinder;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seongil.recyclerviewlife.model.RecyclerViewHeaderItem;
import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlife.model.common.ViewStatus;
import com.seongil.recyclerviewlife.sample.R;
import com.seongil.recyclerviewlife.sample.model.ClientHeaderItem;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractHeaderViewBinder;

/**
 * @author seong-il, kim
 * @since 17. 4. 8
 */
public class LinearListHeaderViewBinder extends AbstractHeaderViewBinder {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================
    public LinearListHeaderViewBinder(LayoutInflater inflater, RecyclerViewItemClickListener viewItemClickListener) {
        super(inflater, viewItemClickListener);
    }

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    public boolean isForViewType(@NonNull RecyclerViewItem item) {
        return item instanceof ClientHeaderItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.common_listview_header_footer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItem item, @NonNull RecyclerView.ViewHolder holder) {
        final RecyclerViewHeaderItem data = (RecyclerViewHeaderItem) item;
        final HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
        final Resources res = holder.itemView.getResources();
        final ViewStatus code = data.getStatusCode();
        if (code == ViewStatus.HIDE_VIEW) {
            viewHolder.itemView.setVisibility(View.GONE);
        } else if (code == ViewStatus.VISIBLE_LOADING_VIEW) {
            viewHolder.itemView.setVisibility(View.VISIBLE);
            viewHolder.container.setClickable(false);
            viewHolder.container.setFocusable(false);
            viewHolder.message.setText(res.getString(R.string.loading));
            viewHolder.message.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.itemView.setOnClickListener(null);
        } else if (code == ViewStatus.VISIBLE_LABEL_VIEW) {
            viewHolder.itemView.setVisibility(View.VISIBLE);
            viewHolder.container.setClickable(true);
            viewHolder.container.setFocusable(true);
            viewHolder.message.setText(res.getString(R.string.touch_to_load_previous_data));
            viewHolder.message.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(v -> {
                if (mItemViewClickListener != null) {
                    mItemViewClickListener.onClickedRecyclerViewItem(holder, data, viewHolder.getLayoutPosition());
                }
            });
        }
    }

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public final View container;
        public final ProgressBar progressBar;
        public final TextView message;

        public HeaderViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.container);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            message = (TextView) view.findViewById(R.id.message);
        }
    }
}
