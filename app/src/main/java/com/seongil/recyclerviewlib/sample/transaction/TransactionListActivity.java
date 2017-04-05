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

package com.seongil.recyclerviewlib.sample.transaction;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.seongil.recyclerviewlib.sample.R;
import com.seongil.recyclerviewlib.sample.transaction.TransactionListFragment;

/**
 * @author seongil2.kim
 * @since: 17. 1. 10
 */
public class TransactionListActivity extends AppCompatActivity {

    // ========================================================================
    // constants
    // ========================================================================
    private static final String TAG = "TransactionListActivity";

    // ========================================================================
    // fields
    // ========================================================================
    private Toolbar mToolBar;

    // ========================================================================
    // constructors
    // ========================================================================

    // ========================================================================
    // getter & setter
    // ========================================================================

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitle(R.string.transaction_list);
        setSupportActionBar(mToolBar);

        if (savedInstanceState == null) {
            setFragment();
        }
    }

    // ========================================================================
    // methods
    // ========================================================================
    private void setFragment() {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        final Fragment fragment = TransactionListFragment.newInstance();
        transaction.replace(R.id.container, fragment, TAG).commit();
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
