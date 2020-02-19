package com.best.launcher.executor;

import com.best.launcher.task.TaskType;

/**
 * 文 件 名: ExecutorFactory
 * 创 建 人: LKJ
 * 创建日期: 2020-02-03 16:02
 * 邮   箱: kejun.lu@ucextech.com
 */

public class ExecutorFactory {

    public static TaskExecutor get(TaskType taskType) {
        TaskExecutor executor;
        switch (taskType) {
            case ASYNC:
                executor = AsyncExecutor.getInstance();
                break;
            case MAIN:
                executor = MainExecutor.getInstance();
                break;
            case DELAY:
            default:
                executor = DelayExecutor.getInstance();
        }
        return executor;
    }

}
