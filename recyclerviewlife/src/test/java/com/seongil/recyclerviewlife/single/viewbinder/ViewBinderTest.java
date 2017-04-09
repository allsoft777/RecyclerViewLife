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

package com.seongil.recyclerviewlife.single.viewbinder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.seongil.recyclerviewlife.mock.TestBaseViewBinder;
import com.seongil.recyclerviewlife.mock.TestRecyclerViewItem1;
import com.seongil.recyclerviewlife.mock.TestRecyclerViewItem2;
import com.seongil.recyclerviewlife.mock.TestRecyclerViewItem3;
import com.seongil.recyclerviewlife.mock.TestViewBinder1;
import com.seongil.recyclerviewlife.mock.TestViewBinder2;
import com.seongil.recyclerviewlife.mock.TestViewBinder3;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author seong-il, kim
 * @since 17. 1. 17
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class ViewBinderTest {

    // ========================================================================
    // constants
    // ========================================================================
    private static final int VIEW_TYPE_1 = 1;
    private static final int VIEW_TYPE_2 = 2;
    private static final int VIEW_TYPE_3 = 3;

    // ========================================================================
    // fields
    // ========================================================================
    private TestViewBinder1 b1;
    private TestViewBinder2 b2;
    private TestViewBinder3 b3;

    // ========================================================================
    // constructors
    // ========================================================================
    @Before
    public void setUp() {
        LayoutInflater layoutInflater = PowerMockito.mock(LayoutInflater.class);
        b1 = new TestViewBinder1(VIEW_TYPE_1, layoutInflater, null, null);
        b2 = new TestViewBinder2(VIEW_TYPE_2, layoutInflater, null, null);
        b3 = new TestViewBinder3(VIEW_TYPE_3, layoutInflater, null, null);

        PowerMockito.mockStatic(Log.class);
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
    @Test
    public void testAddViewBinder() throws Exception {
        resetViewBinders(b1, b2, b3);

        ViewBinderListManager mgr = new ViewBinderListManager();
        mgr.addViewBinder(b1);

        try {
            mgr.addViewBinder(b1);
            Assert.fail("It's impossible to add the same view binder to the manager.");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(mgr.getViewBinderList().get(VIEW_TYPE_1) == b1);
        }
    }

    @Test
    public void testRemoveViewBinder() throws Exception {
        resetViewBinders(b1, b2, b3);

        ViewBinderListManager mgr = new ViewBinderListManager();
        mgr.addViewBinder(b1);
        Assert.assertTrue(mgr.removeViewBinder(VIEW_TYPE_1));
        Assert.assertFalse(mgr.removeViewBinder(VIEW_TYPE_1));
    }

    @Test
    public void testClearAndSize() throws Exception {
        resetViewBinders(b1, b2, b3);

        ViewBinderListManager mgr = new ViewBinderListManager();
        mgr.addViewBinder(b1);
        Assert.assertEquals(mgr.size(), 1);

        mgr.clear();
        Assert.assertEquals(0, mgr.size());
    }

    @Test
    public void testExistViewBinder() throws Exception {
        resetViewBinders(b1, b2, b3);

        ViewBinderListManager mgr = new ViewBinderListManager();
        mgr.addViewBinder(b1);
        Assert.assertTrue(mgr.existViewBinder(b1));
        Assert.assertFalse(mgr.existViewBinder(b2));
    }

    @Test
    public void testIsForViewType() throws Exception {
        resetViewBinders(b1, b2, b3);

        ViewBinderListManager mgr = new ViewBinderListManager();
        mgr.addViewBinder(b1);
        mgr.addViewBinder(b2);
        mgr.addViewBinder(b3);

        int viewType = mgr.getViewBinderList().get(VIEW_TYPE_1).getItemViewType();
        Assert.assertEquals(viewType, VIEW_TYPE_1);
        Assert.assertTrue(b1.isForViewType(new TestRecyclerViewItem1()));
        Assert.assertTrue(b2.isForViewType(new TestRecyclerViewItem2()));
        Assert.assertTrue(b3.isForViewType(new TestRecyclerViewItem3()));
    }

    @Test
    public void testOnCreateViewHolder() throws Exception {
        resetViewBinders(b1, b2, b3);

        ViewBinderListManager mgr = new ViewBinderListManager();
        mgr.addViewBinder(b1);
        mgr.addViewBinder(b2);
        mgr.addViewBinder(b3);

        RecyclerView.ViewHolder vh = mgr.onCreateViewHolder(null, VIEW_TYPE_1);
        Assert.assertSame(vh, b1.viewHolder);
        Assert.assertTrue(b1.onCreateViewHolderCalled);
        Assert.assertFalse(b2.onCreateViewHolderCalled);
        Assert.assertFalse(b3.onCreateViewHolderCalled);
        resetViewBinders(b1, b2, b3);

        vh = mgr.onCreateViewHolder(null, VIEW_TYPE_2);
        Assert.assertSame(vh, b2.viewHolder);
        Assert.assertFalse(b1.onCreateViewHolderCalled);
        Assert.assertTrue(b2.onCreateViewHolderCalled);
        Assert.assertFalse(b3.onCreateViewHolderCalled);
        resetViewBinders(b1, b2, b3);

        vh = mgr.onCreateViewHolder(null, VIEW_TYPE_3);
        Assert.assertSame(vh, b3.viewHolder);
        Assert.assertFalse(b1.onCreateViewHolderCalled);
        Assert.assertFalse(b2.onCreateViewHolderCalled);
        Assert.assertTrue(b3.onCreateViewHolderCalled);
        resetViewBinders(b1, b2, b3);
    }

    @Test
    public void testOnBinderViewHolder() throws Exception {
        resetViewBinders(b1, b2, b3);

        ViewBinderListManager mgr = new ViewBinderListManager();
        mgr.addViewBinder(b1);
        mgr.addViewBinder(b2);
        mgr.addViewBinder(b3);

        mgr.onBindViewHolder(new TestRecyclerViewItem1(), b1.viewHolder);
        Assert.assertTrue(b1.onBindViewHolderCalled);
        Assert.assertFalse(b2.onBindViewHolderCalled);
        Assert.assertFalse(b3.onBindViewHolderCalled);

        resetViewBinders(b1, b2, b3);
        mgr.onBindViewHolder(new TestRecyclerViewItem2(), b2.viewHolder);
        Assert.assertFalse(b1.onBindViewHolderCalled);
        Assert.assertTrue(b2.onBindViewHolderCalled);
        Assert.assertFalse(b3.onBindViewHolderCalled);

        resetViewBinders(b1, b2, b3);
        mgr.onBindViewHolder(new TestRecyclerViewItem3(), b3.viewHolder);
        Assert.assertFalse(b1.onBindViewHolderCalled);
        Assert.assertFalse(b2.onBindViewHolderCalled);
        Assert.assertTrue(b3.onBindViewHolderCalled);
    }

    @Test(expected = NullPointerException.class)
    public void testNoViewBinder() {
        ViewBinderListManager mgr = new ViewBinderListManager();
        mgr.onCreateViewHolder(null, 1);
    }

    private void resetViewBinders(TestBaseViewBinder... binders) {
        for (TestBaseViewBinder d : binders) {
            d.reset();
        }
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
