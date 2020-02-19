package com.best.simple;


import com.best.launcher.task.LaunchTask;

/**
 * 文 件 名: AsyncTask1
 * 创 建 人: LKJ
 * 创建日期: 2020-02-06 16:12
 * 邮   箱: kejun.lu@ucextech.com
 */
public class AsyncTask1 extends LaunchTask {
    @Override
    public void call() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isBlockMainThread() {
        return true;
    }
}
