package com.seongil.recyclerviewlib.sample.model;

import com.seongil.recyclerviewlib.model.common.RecyclerViewItem;

/**
 * @author seong-il, kim
 * @since 17. 4. 7
 */
public class AdvertisementItem implements RecyclerViewItem {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    private String title;
    private int iconRes;

    // ========================================================================
    // constructors
    // ========================================================================
    public AdvertisementItem() {
    }

    public AdvertisementItem(String title, int iconRes) {
        this.title = title;
        this.iconRes = iconRes;
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

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
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
