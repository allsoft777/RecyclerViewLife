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

package com.seongil.recyclerviewlib.lib.model;

import com.seongil.recyclerviewlib.lib.model.common.RecyclerViewItem;
import com.seongil.recyclerviewlib.lib.model.common.ViewStatus;

/**
 * @author seong-il, kim
 * @since 17. 3. 21
 */
public class RecyclerViewHeaderFooterItem implements RecyclerViewItem {

    // ========================================================================
    // Constants
    // ========================================================================

    // ========================================================================
    // Fields
    // ========================================================================
    /**
     * Represents the state of the concrete class.
     * If {#applyOneCycle} is true, follows the {#statusCodeForOneCycle} value.
     */
    private ViewStatus statusCode = ViewStatus.IDLE;

    /**
     * Represents the state of the concrete class.
     * This status code is valid when {#applyOneCycle} got "true".
     */
    private ViewStatus statusCodeForOneCycle = ViewStatus.IDLE;
    private boolean applyOneCycle = false;

    // ========================================================================
    // Constructors
    // ========================================================================
    public RecyclerViewHeaderFooterItem(ViewStatus statusCode) {
        this.statusCode = statusCode;
    }

    // ========================================================================
    // Getter & Setter
    // ========================================================================
    public ViewStatus getStatusCode() {
        return applyOneCycle ? statusCodeForOneCycle : statusCode;
    }

    public boolean isApplyOneCycle() {
        return applyOneCycle;
    }

    public void setApplyOneCycle(boolean applyOneCycle) {
        this.applyOneCycle = applyOneCycle;
    }

    public void setStatusCode(ViewStatus statusCode) {
        this.statusCode = statusCode;
    }

    // ========================================================================
    // Methods for/from SuperClass/Interfaces
    // ========================================================================

    // ========================================================================
    // Methods
    // ========================================================================
    public void setStatusCode(ViewStatus statusCode, boolean applyOneCycle) {
        this.applyOneCycle = applyOneCycle;
        if (applyOneCycle) {
            this.statusCodeForOneCycle = statusCode;
        } else {
            this.statusCode = statusCode;
        }
    }

    public boolean autoLoadData() {
        return statusCode == ViewStatus.VISIBLE_LOADING_VIEW;
    }

    // ========================================================================
    // Inner and Anonymous Classes
    // ========================================================================
}