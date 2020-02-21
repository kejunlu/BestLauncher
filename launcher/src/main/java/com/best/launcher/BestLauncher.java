package com.best.launcher;

import android.app.Application;
import android.os.Looper;

import com.best.launcher.executor.AsyncExecutor;
import com.best.launcher.executor.DelayExecutor;
import com.best.launcher.executor.ExecutorFactory;
import com.best.launcher.task.ILaunchTask;
import com.best.launcher.task.LaunchTask;
import com.best.launcher.task.TaskType;
import com.best.launcher.util.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 文 件 名: BestLauncher
 * 创 建 人: LKJ
 * 创建日期: 2020-02-06 16:01
 * 邮   箱: kejun.lu@ucextech.com
 */
public class BestLauncher implements IBestLauncher {

    //调试信息
    private static boolean sDebuggable = true;
    public static CountDownLatch mCountDownLatch;

    private ILaunchTask mPreMainTask;
    private List<ILaunchTask> mMainTasks;
    private List<ILaunchTask> mAsyncTasks;
    private List<ILaunchTask> mDelayTasks;
    private long mMainThreadTime;

    @Override
    public void start() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new RuntimeException("BestLauncher#start should be invoke on MainThread!");
        }
        mMainThreadTime = System.currentTimeMillis();
        executePreMainTask();
        executeAsyncTasks();
        executeMainTasks();
        executeDelayTasks();
    }


    @Override
    public void shutdown() {
        shutdownAllExecutors();
    }

    private void executeMainTasks() {
        for (ILaunchTask task : mMainTasks) {
            task.run();
        }
        mMainThreadTime = System.currentTimeMillis() - mMainThreadTime;
        Logger.logBeshLauncher(this);
    }

    private void executePreMainTask() {
        if(mPreMainTask != null) {
            mPreMainTask.run();
        }
    }

    private void executeAsyncTasks() {
        if (mCountDownLatch == null) {
            int blockCount = 0;
            for (ILaunchTask task : mAsyncTasks) {
                if(task.isBlockMainThread()) blockCount++;
            }
            mCountDownLatch = new CountDownLatch(blockCount);
        }
        executeTasks(mAsyncTasks);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeDelayTasks() {
        executeTasks(mDelayTasks);
    }

    private void executeTasks(List<ILaunchTask> tasks) {
        for (ILaunchTask task : tasks) {
            TaskType taskType = task.getType();
            ExecutorFactory.get(taskType).execute(task);
        }
    }

    private synchronized void shutdownAllExecutors() {
        AsyncExecutor.getInstance().shutdown();
        DelayExecutor.getInstance().shutdown();
    }

    public static boolean debuggable() {
        return sDebuggable;
    }

    public long getMainThreadTime() {
        return mMainThreadTime;
    }

    public static final class Builder {

        private ILaunchTask mPreMainTask;
        private List<ILaunchTask> mMainTasks;
        private List<ILaunchTask> mAsyncTasks;
        private List<ILaunchTask> mDelayTasks;

        public Builder() {
            mMainTasks = new ArrayList<>();
            mAsyncTasks = new ArrayList<>();
            mDelayTasks = new ArrayList<>();
        }

        public Builder addPreLoadMainTask(LaunchTask task) {
            if(mPreMainTask != null) {
                throw new RuntimeException("Preload tasks have been added!");
            }
            task.setTaskType(TaskType.MAIN);
            this.mPreMainTask = task;
            return this;
        }

        public Builder addMainTask(LaunchTask task) {
            task.setTaskType(TaskType.MAIN);
            this.mMainTasks.add(task);
            return this;
        }

        public Builder addAsyncTask(LaunchTask task) {
            task.setTaskType(TaskType.ASYNC);
            mAsyncTasks.add(task);
            return this;
        }

        public Builder addDelayTask(LaunchTask task) {
            task.setTaskType(TaskType.DELAY);
            mDelayTasks.add(task);
            return this;
        }

        private BestLauncher create() {
            BestLauncher launcher = new BestLauncher();
            launcher.mPreMainTask = mPreMainTask;
            launcher.mMainTasks = mMainTasks;
            launcher.mAsyncTasks = mAsyncTasks;
            launcher.mDelayTasks = mDelayTasks;
            return launcher;
        }

        public IBestLauncher start() {
            IBestLauncher launcher = create();
            launcher.start();
            return launcher;
        }

    }
}
