package com.best.launcher.util;

import android.util.Log;

import com.best.launcher.BestLauncher;
import com.best.launcher.task.LaunchTask;

public class Logger {
    private static final String TAG = "BestLauncher";
    static final String HALF_LINE_STRING = "==============================================";
    static final String LINE_STRING_FORMAT = "| %s : %s ";
    static final String MS_UNIT = "ms";
    static final String TASK_INFO = "任务类名";
    static final String THREAD_INFO = "线程信息";
    static final String START_TIME = "开始时刻";
    static final String START_UNTIL_RUNNING = "等待运行耗时";
    static final String RUNNING_CONSUME = "运行任务耗时";
    static final String MAIN_THREAD_TIME = "主线程总耗时";
    static final String WRAPPED = "\n";
    static final String BLANK = " ";

    static void d(Object obj) {
        d(TAG, obj);
    }

    static void w(Object obj) {
        w(TAG, obj);
    }

    public static void e(Object obj) {
        e(TAG, obj);
    }

    static void e(String tag, Object obj) {
        if (BestLauncher.debuggable()) {
            Log.e(tag, obj.toString());
        }
    }

    static void w(String tag, Object obj) {
        if (BestLauncher.debuggable()) {
            Log.w(tag, obj.toString());
        }
    }

    static void d(String tag, Object obj) {
        if (BestLauncher.debuggable()) {
            Log.d(tag, obj.toString());
        }
    }

    public static void logBeshLauncher(BestLauncher launcher) {
        StringBuilder builder = new StringBuilder();
        builder.append(WRAPPED);
        builder.append(BLANK);
        builder.append(WRAPPED);
        builder.append(HALF_LINE_STRING);
        String executeTimeStr = launcher.getMainThreadTime() + MS_UNIT;
        addTaskInfoLineString(builder, MAIN_THREAD_TIME, executeTimeStr);
        builder.append(WRAPPED);
        builder.append(HALF_LINE_STRING);
        e(TAG, builder.toString());

    }

    public synchronized static void logLaunchTask(LaunchTask task) {
        StringBuilder builder = new StringBuilder();
        builder.append(WRAPPED);
        builder.append(BLANK);
        builder.append(WRAPPED);
        builder.append(HALF_LINE_STRING);
        addTaskInfoLineString(builder, TASK_INFO, task.getClass().getName());
        addTaskInfoLineString(builder, THREAD_INFO, Thread.currentThread().getName());
        String waitTimeStr = task.getWaitingTime() + MS_UNIT;
        addTaskInfoLineString(builder, START_UNTIL_RUNNING, waitTimeStr);
        String executeTimeStr = task.getExecuteTime() + MS_UNIT;
        addTaskInfoLineString(builder, RUNNING_CONSUME, executeTimeStr);
        builder.append(WRAPPED);
        builder.append(HALF_LINE_STRING);
        e(TAG, builder.toString());

    }

    private static void addTaskInfoLineString(StringBuilder stringBuilder, String key, String value) {
        if (stringBuilder == null) {
            return;
        }
        stringBuilder.append(WRAPPED);
        stringBuilder.append(String.format(LINE_STRING_FORMAT, key, value));
    }


}
