package com.easy.elog.logcat;

import java.io.File;
import java.util.Comparator;

/**
 * 对File文件按照时间排序
 * <p>
 * author: Wang
 * date: 2018/8/30 19:04
 */
class ComparatorFileTime implements Comparator<File> {
    @Override
    public int compare(File f1, File f2) {
        long diff = f1.lastModified() - f2.lastModified();
        if (diff > 0) {
            return 1;
        } else if (diff == 0) {
            return 0;
        } else {
            return -1;
        }
    }
}
