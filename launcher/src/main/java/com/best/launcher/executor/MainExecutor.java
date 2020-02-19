package com.best.launcher.executor;

import android.os.Handler;
import android.os.Looper;

/**
 * 文 件 名: MainExecutor
 * 创 建 人: LKJ
 * 创建日期: 2020-02-03 16:02
 * 邮   箱: kejun.lu@ucextech.com
 */

public class MainExecutor implements TaskExecutor {

    private static final Handler mMainHandler = new Handler(Looper.getMainLooper());

    public static MainExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MainExecutor INSTANCE = new MainExecutor();
    }

    private MainExecutor() {
    }

    @Override
    public void execute(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            mMainHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    @Override
    public void shutdown() {

    }
}
