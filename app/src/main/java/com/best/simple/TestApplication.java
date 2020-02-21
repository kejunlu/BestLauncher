package com.best.simple;

import android.app.Application;

import com.best.launcher.BestLauncher;
import com.best.launcher.util.ProcessUtils;

/**
 * 文 件 名: TestApplication
 * 创 建 人: LKJ
 * 创建日期: 2020-02-06 16:11
 * 邮   箱: kejun.lu@ucextech.com
 */
public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessUtils.isMianProcess(this)) {
            new BestLauncher.Builder()
                    .addPreLoadMainTask(new MainTask())
                    .addMainTask(new MainTask2())
                    .addMainTask(new MainTask3())
                    .addAsyncTask(new AsyncTask1())
                    .addAsyncTask(new AsyncTask2())
                    .addAsyncTask(new AsyncTask3())
                    .addAsyncTask(new AsyncTask4())
                    .addDelayTask(new DelayTask())
//                .addDelayTask(new DelayTask2())
                    .addDelayTask(new DelayTask3())
                    .start();
        }
    }
}
