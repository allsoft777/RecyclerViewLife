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

package com.seongil.recyclerviewlife.single;

import android.util.Log;
import android.view.LayoutInflater;

import com.seongil.recyclerviewlife.mock.MockRecyclerViewAdapterSimple;
import com.seongil.recyclerviewlife.mock.TestRecyclerViewItem1;
import com.seongil.recyclerviewlife.mock.TestRecyclerViewItem2;
import com.seongil.recyclerviewlife.mock.TestRecyclerViewItem3;
import com.seongil.recyclerviewlife.mock.TestViewBinder1;
import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author seong-il, kim
 * @since 17. 1. 17
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class AbstractRecyclerViewAdapterTest {

    // ========================================================================
    // constants
    // ========================================================================
    private static final int VIEW_TYPE_1 = 1;

    // ========================================================================
    // fields
    // ========================================================================
    @Mock
    LayoutInflater layoutInflater;
    private TestViewBinder1 vb1;

    // ========================================================================
    // constructors
    // ========================================================================
    @Before
    public void setUp() throws Exception {
        vb1 = new TestViewBinder1(VIEW_TYPE_1, layoutInflater, null, null);
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
    @Test(expected = IllegalArgumentException.class)
    public void testAddViewBinder() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);
        adapter.addViewBinder(vb1);
    }

    @Test
    public void testRemoveViewBinder() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);
        Assert.assertTrue(adapter.removeViewBinder(VIEW_TYPE_1));
        Assert.assertFalse(adapter.removeViewBinder(VIEW_TYPE_1));
    }

    @Test
    public void testClearViewBinders() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);
        Assert.assertEquals(adapter.getViewBindersCount(), 1);
        adapter.clearViewBinders();
        Assert.assertEquals(adapter.getViewBindersCount(), 0);
    }

    @Test
    public void testExistViewBinder() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);
        Assert.assertTrue(adapter.existViewBinder(vb1));
        adapter.removeViewBinder(VIEW_TYPE_1);
        Assert.assertFalse(adapter.existViewBinder(vb1));
    }

    @Test
    public void testAddRemoveDataItem() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);

        TestRecyclerViewItem1 item1 = new TestRecyclerViewItem1();
        TestRecyclerViewItem2 item2 = new TestRecyclerViewItem2();
        TestRecyclerViewItem3 item3 = new TestRecyclerViewItem3();

        adapter.addLast(item1);
        Assert.assertEquals(adapter.getItemCount(), 1);

        adapter.addLast(item2);
        Assert.assertEquals(adapter.getItemCount(), 2);
        assertThat(adapter.getItem(1), instanceOf(TestRecyclerViewItem2.class));

        adapter.addFirst(item3);
        assertThat(adapter.getItem(0), instanceOf(TestRecyclerViewItem3.class));

        adapter.addPosition(item3, 1);
        assertThat(adapter.getItem(1), instanceOf(TestRecyclerViewItem3.class));
    }

    @Test
    public void testReplaceElement() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);

        TestRecyclerViewItem1 item1 = new TestRecyclerViewItem1();
        TestRecyclerViewItem2 item2 = new TestRecyclerViewItem2();
        adapter.addFirst(item1);
        assertThat(adapter.getItem(0), instanceOf(TestRecyclerViewItem1.class));

        adapter.replaceElement(item2, 0);
        assertThat(adapter.getItem(0), instanceOf(TestRecyclerViewItem2.class));

        adapter.replaceElement(item1, 0);
        assertThat(adapter.getItem(0), instanceOf(TestRecyclerViewItem1.class));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testReplaceElementException() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);

        TestRecyclerViewItem1 item1 = new TestRecyclerViewItem1();
        adapter.replaceElement(item1, 1);
    }

    @Test
    public void testAddFirstCollection() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);

        List<RecyclerViewItem> partial = new ArrayList<>();
        partial.add(new TestRecyclerViewItem1());
        partial.add(new TestRecyclerViewItem2());
        partial.add(new TestRecyclerViewItem3());

        adapter.addFirstCollection(partial);
        Assert.assertEquals(partial.size(), adapter.getItemCount());

        List<RecyclerViewItem> partialEmpty = new ArrayList<>();
        adapter.addFirstCollection(partialEmpty);
        Assert.assertEquals(partial.size(), adapter.getItemCount());

        assertThat(adapter.getItem(0), instanceOf(TestRecyclerViewItem1.class));
        assertThat(adapter.getItem(1), instanceOf(TestRecyclerViewItem2.class));
        assertThat(adapter.getItem(2), instanceOf(TestRecyclerViewItem3.class));
    }

    @Test
    public void testAddLastCollection() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);

        List<RecyclerViewItem> partial = new ArrayList<>();
        partial.add(new TestRecyclerViewItem1());
        partial.add(new TestRecyclerViewItem2());
        partial.add(new TestRecyclerViewItem3());

        adapter.addLastCollection(partial);
        Assert.assertEquals(partial.size(), adapter.getItemCount());

        List<RecyclerViewItem> partialEmpty = new ArrayList<>();
        adapter.addLastCollection(partialEmpty);
        Assert.assertEquals(partial.size(), adapter.getItemCount());

        assertThat(adapter.getItem(0), instanceOf(TestRecyclerViewItem1.class));
        assertThat(adapter.getItem(1), instanceOf(TestRecyclerViewItem2.class));
        assertThat(adapter.getItem(2), instanceOf(TestRecyclerViewItem3.class));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddPosition() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);
        TestRecyclerViewItem1 item1 = new TestRecyclerViewItem1();

        adapter.addPosition(item1, 0);
        adapter.addPosition(item1, adapter.getItemCount());

        adapter.addPosition(item1, adapter.getItemCount() + 1);
    }

    @Test
    public void testRemoveLastItem() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);
        TestRecyclerViewItem1 item1 = new TestRecyclerViewItem1();
        TestRecyclerViewItem2 item2 = new TestRecyclerViewItem2();
        TestRecyclerViewItem3 item3 = new TestRecyclerViewItem3();

        adapter.addLast(item1);
        adapter.addLast(item2);
        adapter.addLast(item3);

        Assert.assertEquals(adapter.getItemCount(), 3);
        adapter.removeLastItem();
        Assert.assertEquals(adapter.getItemCount(), 2);

        assertThat(adapter.getItem(0), instanceOf(TestRecyclerViewItem1.class));
        assertThat(adapter.getItem(1), instanceOf(TestRecyclerViewItem2.class));

        adapter.removeLastItem();
        adapter.removeLastItem();
        Assert.assertEquals(adapter.getItemCount(), 0);
    }

    @Test
    public void testRemoveFirstItem() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);
        TestRecyclerViewItem1 item1 = new TestRecyclerViewItem1();
        TestRecyclerViewItem2 item2 = new TestRecyclerViewItem2();
        TestRecyclerViewItem3 item3 = new TestRecyclerViewItem3();

        adapter.addLast(item1);
        adapter.addLast(item2);
        adapter.addLast(item3);

        Assert.assertEquals(adapter.getItemCount(), 3);
        adapter.removeFirstItem();
        Assert.assertEquals(adapter.getItemCount(), 2);

        assertThat(adapter.getItem(0), instanceOf(TestRecyclerViewItem2.class));
        assertThat(adapter.getItem(1), instanceOf(TestRecyclerViewItem3.class));

        adapter.removeFirstItem();
        adapter.removeFirstItem();
        Assert.assertEquals(adapter.getItemCount(), 0);
    }

    @Test
    public void testRemoveItem() throws Exception {
        MockRecyclerViewAdapterSimple adapter = new MockRecyclerViewAdapterSimple(layoutInflater, vb1);
        TestRecyclerViewItem1 item1 = new TestRecyclerViewItem1();
        TestRecyclerViewItem2 item2 = new TestRecyclerViewItem2();
        TestRecyclerViewItem3 item3 = new TestRecyclerViewItem3();

        adapter.addLast(item1);
        adapter.addLast(item2);
        adapter.addLast(item3);

        Assert.assertEquals(adapter.getItemCount(), 3);
        adapter.removePosition(1, false);
        Assert.assertEquals(adapter.getItemCount(), 2);

        assertThat(adapter.getItem(0), instanceOf(TestRecyclerViewItem1.class));
        assertThat(adapter.getItem(1), instanceOf(TestRecyclerViewItem3.class));
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
