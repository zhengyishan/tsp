package com.dyy.tsp.common.asyn;

import com.dyy.tsp.common.kafka.KafkaConsumerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * created by dyy
 * kafka异步消费任务池单例设置
 */
@SuppressWarnings("all")
public class TaskPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskPool.class);

    private static TaskPool tp = null;

    /**
     * jdk自带线程池
     */
    private final ExecutorService pool;

    /**
     * 私有构造
     */
    private TaskPool() {
        this.pool = new ThreadPoolExecutor(8, 12,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1000),
                new KafkaConsumerManager.DefaultThreadFactory("DYY"), (r, executor) -> {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                LOGGER.warn("retry put task error,message:{}", e);
            }
        });
    }

    /**
     * 提供get方法获取TaskPool实例
     * @return
     */
    public static TaskPool getInstance() {
        if (tp == null) {
            synchronized (TaskPool.class) {
                if (tp == null) {
                    tp = new TaskPool();
                }
            }
        }
        return tp;
    }

    /**
     * 提交执行任务
     * @param task
     */
    public void submitTask(Runnable task) {
        pool.execute(task);
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        pool.shutdown();
    }

}
