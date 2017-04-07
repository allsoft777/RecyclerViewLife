package com.seongil.recyclerviewlib.sample.model;

import com.seongil.recyclerviewlib.model.common.RecyclerViewItem;

/**
 * @author seong-il, kim
 * @since 17. 4. 7
 */
public class ThumbnailTitleItem implements RecyclerViewItem {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    private String title;
    private int thumbnailResId;

    // ========================================================================
    // constructors
    // ========================================================================
    public ThumbnailTitleItem() {
    }

    public ThumbnailTitleItem(String title, int resId) {
        this.title = title;
        this.thumbnailResId = resId;
    }

    // ========================================================================
    // getter & setter
    // ========================================================================
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThumbnailResId() {
        return thumbnailResId;
    }

    public void setThumbnailResId(int thumbnailResId) {
        this.thumbnailResId = thumbnailResId;
    }

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
