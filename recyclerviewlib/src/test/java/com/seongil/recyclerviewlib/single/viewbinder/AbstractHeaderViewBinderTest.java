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

package com.seongil.recyclerviewlib.single.viewbinder;

import android.view.LayoutInflater;

import com.seongil.recyclerviewlib.mock.TestHeaderViewBinder;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

/**
 * @author seong-il, kim
 * @since: 17. 1. 17
 */
public class AbstractHeaderViewBinderTest {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    @Mock
    LayoutInflater layoutInflater;

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
    @Test
    public void testFooterViewBinder() {

        ViewBinderListManager mgr = new ViewBinderListManager();
        TestHeaderViewBinder fvb = new TestHeaderViewBinder(layoutInflater, null);
        mgr.addViewBinder(fvb);

        AbstractViewBinder avb = (AbstractViewBinder) mgr.getViewBinderList()
            .get(AbstractViewBinder.RECYCLER_HEADER_VIEW_TYPE);

        Assert.assertEquals(avb, fvb);
        Assert.assertEquals(AbstractViewBinder.RECYCLER_HEADER_VIEW_TYPE, fvb.getItemViewType());
        Assert.assertNull(fvb.getClickListener());
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
