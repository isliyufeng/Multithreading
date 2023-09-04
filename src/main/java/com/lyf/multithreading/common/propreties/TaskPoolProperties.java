package com.lyf.multithreading.common.propreties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author sunjian
 * @Description
 * @since 2022/3/24
 */
@Data
@Component
@ConfigurationProperties("threadpool.task")
public class TaskPoolProperties {
    /**
     * 初始化线程容量
     */
    private int corePoolSize;
    /**
     * 最大的线程池大小
     */
    private int maxPoolSize;
    /**
     * 队列大小
     */
    private int queueCapacity;
    /**
     * 单位秒
     */
    private int keepAliveTime;
}
