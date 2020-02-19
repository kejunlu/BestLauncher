package com.best.simple;

import com.best.launcher.task.LaunchTask;

/**
 * 文 件 名: DelayTask
 * 创 建 人: LKJ
 * 创建日期: 2020-02-10 15:42
 * 邮   箱: kejun.lu@ucextech.com
 */
public class MainTask3 extends LaunchTask {

    @Override
    public void call() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
