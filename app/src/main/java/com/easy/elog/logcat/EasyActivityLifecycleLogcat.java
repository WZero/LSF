package com.easy.elog.logcat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.socks.library.KLog;


/**
 * 打印 Activity 生命周期log
 * author: Wang
 * date: 2018/9/5 15:15
 */
public class EasyActivityLifecycleLogcat implements Application.ActivityLifecycleCallbacks {
    private static EasyActivityLifecycleLogcat lifecycleLogcat = new EasyActivityLifecycleLogcat();

    public static EasyActivityLifecycleLogcat getInstance() {
        return lifecycleLogcat;
    }

    private EasyActivityLifecycleLogcat() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        KLog.d(activity.getClass().getName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        KLog.d(activity.getClass().getName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        KLog.d(activity.getClass().getName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        KLog.d(activity.getClass().getName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        KLog.d(activity.getClass().getName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        KLog.d(activity.getClass().getName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        KLog.d(activity.getClass().getName());
    }

}
