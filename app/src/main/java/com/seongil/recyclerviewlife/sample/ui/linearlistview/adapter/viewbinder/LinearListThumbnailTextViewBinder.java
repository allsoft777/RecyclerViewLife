package com.seongil.recyclerviewlife.sample.ui.linearlistview.adapter.viewbinder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlife.sample.R;
import com.seongil.recyclerviewlife.sample.model.ThumbnailTitleItem;
import com.seongil.recyclerviewlife.single.viewbinder.AbstractViewBinder;

/**
 * @author seong-il, kim
 * @since 17. 4. 8
 */
public class LinearListThumbnailTextViewBinder extends AbstractViewBinder {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================
    public LinearListThumbnailTextViewBinder(int viewType, @NonNull LayoutInflater inflater,
          @Nullable RecyclerViewItemClickListener itemClickListener) {
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
        return item instanceof ThumbnailTitleItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new LinearListThumbnailTextViewHolder(
              mLayoutInflater.inflate(R.layout.linearlistview_item_with_thumbnail_title, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItem item, @NonNull RecyclerView.ViewHolder holder) {
        final ThumbnailTitleItem data = (ThumbnailTitleItem) item;
        final LinearListThumbnailTextViewHolder viewHolder = (LinearListThumbnailTextViewHolder) holder;
        viewHolder.itemView.setOnClickListener(v -> {
            if (mItemViewClickListener != null) {
                mItemViewClickListener.onClickedRecyclerViewItem(holder, data, viewHolder.getLayoutPosition());
            }
        });
        viewHolder.title.setText(data.getTitle());

        Glide
              .with(viewHolder.itemView.getContext())
              .fromResource()
              .load(data.getThumbnailResId())
              .into(viewHolder.image);
    }

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
    public static class LinearListThumbnailTextViewHolder extends RecyclerView.ViewHolder {

        public final ImageView image;
        public final TextView title;

        public LinearListThumbnailTextViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.thumbnail);
            title = (TextView) view.findViewById(R.id.title);
        }
    }
}
