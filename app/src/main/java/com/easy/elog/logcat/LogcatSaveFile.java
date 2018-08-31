package com.easy.elog.logcat;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Logcat 信息保存到File
 * author: Wang
 * date: 2018/8/30 16:30
 */
public class LogcatSaveFile implements LogcatListener {
    private static LogcatSaveFile logcatSaveFile;
    private File logFilePath;

    /**
     * Logcat 最多缓存文件数量
     * <p>
     * 超出后按照时间顺序删除最早的一半
     */
    private int cacheCount = 50;

    /**
     * 单个缓存文件大小 单位B
     */
    private int cacheFileSize = 512000;

    /**
     * 日志文件存储路径
     * /sdcard/android/data/{package}/files/logcat
     */
    private final File filePath;

    /**
     * Logcat 最多缓存文件数量
     * <p>
     * 超出后按照时间顺序删除最早的一半
     *
     * @param cacheCount int 默认50条
     */
    public void setCacheCount(int cacheCount) {
        this.cacheCount = cacheCount;
    }

    /**
     * 单个缓存文件大小 单位B
     *
     * @param cacheFileSize 默认 512000b
     */
    public void setCacheFileSize(int cacheFileSize) {
        this.cacheFileSize = cacheFileSize;
    }

    private LogcatSaveFile(Context context, File filePath) {
        this.filePath = filePath;
        LogcatThread.init(context).setLogcatListener(this);
    }

    /**
     * @param context Context
     * @return LogcatSaveFile
     */
    public synchronized static void initLSF(Context context) {
        if (logcatSaveFile == null)
            logcatSaveFile = new LogcatSaveFile(context, context.getExternalFilesDir("logcat"));
    }

    @Override
    public synchronized void onCallback(String string) {
        try {
            if (logFilePath == null || !logFilePath.exists() || logFilePath.length() > cacheFileSize) {
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                File[] files = filePath.listFiles();
                if (files.length >= cacheCount) {
                    Arrays.sort(files, new ComparatorFileTime());
                    List<File> fileList = new ArrayList<>(Arrays.asList(files));
                    while (fileList.size() > cacheCount / 2) {
                        File dFile = fileList.get(0);
                        if (dFile.exists() && dFile.isFile()) {
                            dFile.delete();
                        }
                        fileList.remove(dFile);
                    }
                }
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                logFilePath = new File(filePath, String.format("%s-%s.txt", sdf.format(new Date()), System.currentTimeMillis()));
            }
            writerFile(logFilePath, string);
        } catch (Exception ignored) {
        }
    }

    /**
     * 写入文件
     *
     * @param file File
     * @param str  String
     * @throws IOException IOException
     */
    private void writerFile(File file, String str) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(String.format("%s\n", str));
        fileWriter.flush();
        fileWriter.close();
    }


}
