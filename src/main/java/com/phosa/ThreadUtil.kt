package com.phosa

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.*
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy


/**
 * 线程工具类
 */
object ThreadUtil {

    val log: Logger = LoggerFactory.getLogger(ThreadUtil::class.java)

    private var threadPool: ExecutorService? = null

    init {
        updateThreadPool(5, 10)
    }

    /**
     * 更新线程池
     * @param corePoolSize 核心线程数
     * @param maxPollSize 最大线程数
     */
    @JvmOverloads
    fun updateThreadPool(corePoolSize: Int, maxPollSize: Int = corePoolSize) {
        updateThreadPool(
            ThreadPoolExecutor(
                corePoolSize,
                maxPollSize,
                0L,
                TimeUnit.MILLISECONDS,
                LinkedBlockingQueue<Runnable?>(maxPollSize),
                Executors.defaultThreadFactory(),
                AbortPolicy()
            )
        )
    }

    /**
     * 更新线程池
     * @param threadPoolExecutor 线程池
     */
    fun updateThreadPool(threadPoolExecutor: ThreadPoolExecutor?) {
        shutdownThreadPool()
        threadPool = threadPoolExecutor
    }

    /**
     * 执行任务
     * @param task 任务
     */
    fun executeTask(task: Runnable) {
        checkNotNull(threadPool) { "ThreadUtil: Thread pool has not been initialized." }
        threadPool!!.execute(task)
    }

    /**
     * 关闭线程池
     */
    fun shutdownThreadPool() {
        if (threadPool != null) {
            threadPool!!.shutdown()
            try {
                if (!threadPool!!.awaitTermination(30, TimeUnit.SECONDS)) {
                    threadPool!!.shutdownNow()
                }
            } catch (e: InterruptedException) {
                log.error("关闭线程池异常：{}", e.message, e)
                threadPool!!.shutdownNow()
                Thread.currentThread().interrupt()
            }
        }
    }
}

