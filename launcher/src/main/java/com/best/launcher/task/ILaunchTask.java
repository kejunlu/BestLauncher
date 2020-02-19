package com.best.launcher.task;

/**
 * 文 件 名: ILaunchTask
 * 创 建 人: LKJ
 * 创建日期: 2020-02-03 16:02
 * 邮   箱: kejun.lu@ucextech.com
 *
 * 类型：
 * 1、需要在主线程执行的任务（不建议太耗时的任务）
 * 2、任意线程需要在Activity的onCreate之前执行完成的任务
 * 3、任意线程可以延迟执行的任务
 *
 *
 */
public interface ILaunchTask extends Runnable {

    /**
     *
     * @return 线程类型
     */
    TaskType getType();

    /**
     *
     * @return 是否阻塞ui线程
     */
    boolean isBlockMainThread();

    /**
     *
     * @return 线程优先级，优先级高 1到10的范围指定。10表示最高优先级
     */
    int getThreadPriority();

}
