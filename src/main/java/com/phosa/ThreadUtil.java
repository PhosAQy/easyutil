package com.phosa;


import java.util.concurrent.*;

/**
 * 线程工具类
 */
public class ThreadUtil {

    private static ExecutorService threadPool;

    static {
        updateThreadPool(5, 10);
    }

    /**
     * 更新线程池
     * @param corePoolSize 核心线程数
     */
    public static void updateThreadPool(int corePoolSize) {
        updateThreadPool(corePoolSize, corePoolSize);
    }

    /**
     * 更新线程池
     * @param corePoolSize 核心线程数
     * @param maxPollSize 最大线程数
     */
    public static void updateThreadPool(int corePoolSize, int maxPollSize) {
        updateThreadPool(new ThreadPoolExecutor(
                corePoolSize,
                maxPollSize,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(maxPollSize),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        ));
    }

    /**
     * 更新线程池
     * @param threadPoolExecutor 线程池
     */
    public static void updateThreadPool(ThreadPoolExecutor threadPoolExecutor) {
        shutdownThreadPool();
        threadPool = threadPoolExecutor;
    }

    /**
     * 执行任务
     * @param task 任务
     */
    public static void executeTask(Runnable task) {
        if (threadPool == null) {
            throw new IllegalStateException("ThreadUtil: Thread pool has not been initialized.");
        }
        threadPool.execute(task);
    }

    /**
     * 关闭线程池
     */
    public static void shutdownThreadPool() {
        if (threadPool != null) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(30, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

}

