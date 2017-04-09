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
