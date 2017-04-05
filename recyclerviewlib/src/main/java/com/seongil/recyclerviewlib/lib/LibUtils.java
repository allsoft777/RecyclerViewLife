package com.seongil.recyclerviewlib.lib;

import android.support.annotation.Nullable;

/**
 * @author seong-il, kim
 * @since 17. 4. 5
 */
public class LibUtils {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================

    // ========================================================================
    // constructors
    // ========================================================================

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================

    // ========================================================================
    // methods
    // ========================================================================
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
