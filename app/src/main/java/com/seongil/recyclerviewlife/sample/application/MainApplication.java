package com.seongil.recyclerviewlife.sample.application;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author seong-il, kim
 * @since 17. 4. 8
 */
public class MainApplication extends Application {

    // ========================================================================
    // constants
    // ========================================================================

    // ========================================================================
    // fields
    // ========================================================================
    private RefWatcher mRefWatcher;
    private static MainApplication sInstance;

    // ========================================================================
    // constructors
    // ========================================================================

    // ========================================================================
    // getter & setter
    // ========================================================================
    public static RefWatcher getRefWatcher(Context context) {
        return ((MainApplication) context.getApplicationContext()).mRefWatcher;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    // ========================================================================
    // methods for/from superclass/interfaces
    // ========================================================================
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        mRefWatcher = LeakCanary.install(this);
    }

    // ========================================================================
    // methods
    // ========================================================================

    // ========================================================================
    // inner and anonymous classes
    // ========================================================================
}
