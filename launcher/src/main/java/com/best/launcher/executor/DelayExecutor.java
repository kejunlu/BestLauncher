package com.best.launcher.executor;

import com.best.launcher.util.Logger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import androidx.annotation.NonNull;

/**
 * 文 件 名: DelayExecutor
 * 创 建 人: LKJ
 * 创建日期: 2020-02-03 16:02
 * 邮   箱: kejun.lu@ucextech.com
 */

public class DelayExecutor implements TaskExecutor {

    private static final String IO_THREAD_PREFIX = "best-delay-";
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private AtomicInteger mName = new AtomicInteger(0);
    private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r, IO_THREAD_PREFIX + mName.incrementAndGet());
            // 捕获多线程处理中的异常
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Logger.e("Running task appeared exception! Thread [" + thread.getName() + "], because [" + ex.getMessage() + "]");
                }
            });
            return thread;
        }
    });

    public static DelayExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DelayExecutor INSTANCE = new DelayExecutor();
    }

    private DelayExecutor() {
        mExecutor.allowCoreThreadTimeOut(true);
    }

    @Override
    public void execute(Runnable runnable) {
        mExecutor.execute(runnable);
    }

    @Override
    public void shutdown() {
        mExecutor.shutdown();
    }
}
