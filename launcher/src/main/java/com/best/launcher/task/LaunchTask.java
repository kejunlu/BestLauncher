package com.best.launcher.task;

import android.app.Application;

import com.best.launcher.BestLauncher;
import com.best.launcher.util.Logger;

/**
 * 文 件 名: LaunchTask
 * 创 建 人: LKJ
 * 创建日期: 2020-02-06 16:13
 * 邮   箱: kejun.lu@ucextech.com
 */
public abstract class LaunchTask implements ILaunchTask {

    private static final int STATE_NEW = 0;
    private static final int STATE_RUNNING = 2;
    private static final int STATE_FINISHED = 3;

    private int mTaskState = STATE_NEW;
    private TaskType mTaskType = TaskType.MAIN;
    private long mNewTime;
    private long mWaitingTime;
    private long mExecuteTime;

    public LaunchTask() {
        setNewTime(System.currentTimeMillis());
        markState(STATE_NEW);
    }

    @Override
    public TaskType getType() {
        return mTaskType;
    }

    @Override
    public boolean isBlockMainThread() {
        return false;
    }

    @Override
    public int getThreadPriority() {
        return 5;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(getThreadPriority());
        markState(STATE_RUNNING);
        try {
            long currentTime = System.currentTimeMillis();
            setWaitingTime(currentTime - mNewTime);
            call();
            setExecuteTime(System.currentTimeMillis() - currentTime);
            logExecuteTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(isBlockMainThread()) {
                BestLauncher.mCountDownLatch.countDown();
            }
        }
        markState(STATE_FINISHED);
    }

    public long getWaitingTime() {
        return mWaitingTime;
    }

    public long getExecuteTime() {
        return mExecuteTime;
    }

    public void setTaskType(TaskType mTaskType) {
        this.mTaskType = mTaskType;
    }

    private void markState(int state) {
        this.mTaskState = state;
    }

    private void setNewTime(long newTime) {
        this.mNewTime = newTime;
    }

    private void setWaitingTime(long waitingTime) {
        this.mWaitingTime = waitingTime;
    }

    private void setExecuteTime(long executeTime) {
        this.mExecuteTime = executeTime;
    }

    private void logExecuteTime() {
        Logger.logLaunchTask(this);
    }

    protected abstract void call();
}
