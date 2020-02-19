package com.best.launcher.executor;

/**
 * 文 件 名: TaskExecutor
 * 创 建 人: LKJ
 * 创建日期: 2020-02-03 16:24
 * 邮   箱: kejun.lu@ucextech.com
 */
public interface TaskExecutor {

    void execute(Runnable runnable);

    void shutdown();
}
