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

package com.seongil.recyclerviewlib.sample.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import io.reactivex.Observable;

/**
 * @author seongil2.kim
 * @since: 17. 2. 8
 */
public class RxFormatObservables {

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
    public static Observable<String> build(SimpleDateFormat sdf, long millis) {
        return Observable.fromCallable(() -> {
            Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Asia/Seoul"));
            calendar.setTimeInMillis(millis);
            sdf.setCalendar(calendar);
            return sdf.format(calendar.getTimeInMillis());
        });
    }

    public static Observable<String> decimalFormat(String format, long number1) {
        return Observable.fromCallable(() -> new DecimalFormat(format).format(number1));
    }

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
