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

import android.view.LayoutInflater;

import com.seongil.recyclerviewlife.mock.MockFooterViewItem;
import com.seongil.recyclerviewlife.mock.MockHeaderViewItem;
import com.seongil.recyclerviewlife.mock.MockRecyclerListViewAdapter;
import com.seongil.recyclerviewlife.mock.TestRecyclerViewItem1;
import com.seongil.recyclerviewlife.mock.TestRecyclerViewItem2;
import com.seongil.recyclerviewlife.mock.TestRecyclerViewItem3;
import com.seongil.recyclerviewlife.model.common.RecyclerViewItem;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author seong-il, kim
 * @since 17. 4. 12
 */
public class RecyclerListViewAdapterTest {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    @Mock LayoutInflater mLayoutInflater;

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
    @SuppressWarnings("unchecked")
    public void testReplaceCollectionWithFooterView() throws Exception {
        MockRecyclerListViewAdapter adapter = new MockRecyclerListViewAdapter(mLayoutInflater);
        adapter.useFooterView();

        List<RecyclerViewItem> list = new ArrayList<>();
        list.add(new TestRecyclerViewItem1());
        list.add(new TestRecyclerViewItem2());
        adapter.addLastCollection(list);

        Assert.assertEquals(adapter.getDataSet().size(), list.size() + 1);
        Assert.assertEquals(adapter.getFooterItem(), adapter.getDataSet().get(2));

        list.clear();
        list.add(new TestRecyclerViewItem3());
        adapter.setData(list);
        Assert.assertEquals(adapter.getDataSet().size(), 2);
        Assert.assertTrue(adapter.getItem(1) instanceof MockFooterViewItem);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testReplaceCollectionWithHeaderView() throws Exception {
        MockRecyclerListViewAdapter adapter = new MockRecyclerListViewAdapter(mLayoutInflater);
        adapter.useHeaderView();

        List<RecyclerViewItem> list = new ArrayList<>();
        list.add(new TestRecyclerViewItem1());
        list.add(new TestRecyclerViewItem2());
        adapter.addLastCollection(list);

        Assert.assertEquals(adapter.getDataSet().size(), list.size() + 1);
        Assert.assertEquals(adapter.getHeaderItem(), adapter.getDataSet().get(0));

        list.clear();
        list.add(new TestRecyclerViewItem3());
        adapter.setData(list);
        Assert.assertEquals(adapter.getDataSet().size(), 2);
        Assert.assertTrue(adapter.getItem(0) instanceof MockHeaderViewItem);
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
