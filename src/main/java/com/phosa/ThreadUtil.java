package com.phosa;


import java.util.concurrent.*;

public class ThreadUtil {

    private static ExecutorService threadPool;

    static {
        updateThreadPool(5, 10);
    }

    public static void updateThreadPool(int corePoolSize) {
        updateThreadPool(corePoolSize, corePoolSize);
    }

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
    public static void updateThreadPool(ThreadPoolExecutor threadPoolExecutor) {
        shutdownThreadPool();
        threadPool = threadPoolExecutor;
    }


    public static void executeTask(Runnable task) {
        if (threadPool == null) {
            throw new IllegalStateException("ThreadUtil: Thread pool has not been initialized.");
        }
        threadPool.execute(task);
    }


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

