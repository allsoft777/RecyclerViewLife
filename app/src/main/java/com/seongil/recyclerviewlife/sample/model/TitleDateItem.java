package com.seongil.recyclerviewlife.sample.model;

import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;

/**
 * @author seong-il, kim
 * @since 17. 4. 7
 */
public class TitleDateItem implements RecyclerViewItem {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    private String title;
    private String dateTime;

    // ========================================================================
    // constructors
    // ========================================================================
    public TitleDateItem() {
    }

    public TitleDateItem(String title, String dateTime) {
        this.title = title;
        this.dateTime = dateTime;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
