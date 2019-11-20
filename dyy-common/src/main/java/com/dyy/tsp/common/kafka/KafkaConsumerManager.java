package com.dyy.tsp.common.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaConsumerManager {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumerManager.class);
    private ExecutorService pool = Executors.newCachedThreadPool(new DefaultThreadFactory("KAFKA-CONSUMER-MANAGER"));
    private volatile boolean running = false;

    public void createConsumer(int taskNum, String topic, Map<String, Object> properties, ApplicationContext context, AbstractListener listener) throws Exception {
        running = true;
        for (int i = 0; i < taskNum; i++) {
            KafkaConsumer consumer = new KafkaConsumer(properties);
            listener.setTopic(topic);
            listener.setConsumer(consumer);
            listener.setContext(context);
            listener.setRunning(running);
            listener.init();
            pool.submit(listener);
        }
    }

    public static class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        public DefaultThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = name + "-thread-";
        }
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    public void shutdown() {
        logger.info("pre destory");
        pool.shutdown();
        running = false;
    }
}
