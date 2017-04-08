package com.seongil.recyclerviewlife.scroll;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author seongil-il, kim
 * @since 17. 4. 6
 */
public class ScrollDirection {

    // ========================================================================
    // constants
    // ========================================================================
    @IntDef({ STOP, RIGHT, LEFT, UP, DOWN })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollDir {

    }

    protected static final int STOP = 0;
    protected static final int RIGHT = 1;
    protected static final int LEFT = 2;
    protected static final int UP = 3;
    protected static final int DOWN = 4;

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================
    private ScrollDirection() {
        throw new AssertionError("Could not create instance.");
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
