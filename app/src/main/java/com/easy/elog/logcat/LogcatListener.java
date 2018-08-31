package com.easy.elog.logcat;

/**
 * author: Wang
 * date: 2018/8/30 14:50
 */
interface LogcatListener {
    /**
     * 打印信息回调
     *
     * @param string String
     */
    void onCallback(String string);
}
