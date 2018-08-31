package com.easy.elog.logcat;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author: Wang
 * date: 2018/8/30 14:49
 */
class LogcatThread extends Thread {

    private LogcatThread() {

    }

    private static LogcatThread logcatThread = new LogcatThread();

    public synchronized static LogcatThread init(Context context) {
        if (!logcatThread.isAlive()) {
            logcatThread.start();
        }
        return logcatThread;
    }


    private LogcatListener logcatListener;

    /**
     * 添加logcat打印回调
     *
     * @param logcatListener LogcatListener
     */
    public void setLogcatListener(LogcatListener logcatListener) {
        this.logcatListener = logcatListener;
    }

    @Override
    public void run() {
        super.run();
        int pid = getAppPid();
        logcatCallback("PID: " + pid);
        try {
            Process process = Runtime.getRuntime().exec(String.format("logcat | grep %s", pid));
            getInputStream(process);
        } catch (Exception ignored) {

        }
    }

    /**
     * @param process Process
     * @throws IOException IOException
     */
    private void getInputStream(Process process) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String string;
        while ((string = bufferedReader.readLine()) != null) {
            logcatCallback(string);
        }
    }


    /**
     * 获取当前程序的pid
     */
    private int getAppPid() {
        return android.os.Process.myPid();
    }

    private void logcatCallback(String string) {
        if (logcatListener != null) {
            logcatListener.onCallback(string);
        }
    }

    @Override
    public synchronized void start() {
        if (!isAlive()) {
            super.start();
        }
    }
}
