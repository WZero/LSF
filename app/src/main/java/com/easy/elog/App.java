package com.easy.elog;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.easy.elog.logcat.EasyActivityLifecycleLogcat;
import com.easy.elog.logcat.LogcatSaveFile;

import java.util.List;

/**
 * author: Wang
 * date: 2018/8/30 16:40
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProcessName(getApplicationContext(), android.os.Process.myPid());
        if (processName == null || !processName.equals(BuildConfig.APPLICATION_ID))
            return;
        registerActivityLifecycleCallbacks(EasyActivityLifecycleLogcat.getInstance());
        LogcatSaveFile.initLSF(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(EasyActivityLifecycleLogcat.getInstance());
        super.onTerminate();
    }

    /**
     * 根据Pid获取当前进程的名字 判断是否是主进程
     *
     * @param context Context
     * @param pid     进程的id
     * @return 返回进程的名字
     */
    private String getProcessName(Context context, int pid) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert activityManager != null;
        List list = activityManager.getRunningAppProcesses();
        for (Object aList : list) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (aList);
            if (info.pid == pid) {
                return info.processName;
            }
        }
        return null;
    }

}
