package com.lyf.multithreading.common.config;

import com.lyf.multithreading.common.propreties.TaskPoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @describe
 * @Author joyous
 * @Date 2023/8/25
 */
@EnableConfigurationProperties({TaskPoolProperties.class})
@Configuration
public class ThreadPoolConfig {
    @Resource
    private TaskPoolProperties taskPoolProperties;

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置线程池参数信息
        taskExecutor.setCorePoolSize(taskPoolProperties.getCorePoolSize());
        taskExecutor.setMaxPoolSize(taskPoolProperties.getMaxPoolSize());
        taskExecutor.setQueueCapacity(taskPoolProperties.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(taskPoolProperties.getKeepAliveTime());
        // 这边可以设置线程名称前缀
        taskExecutor.setThreadNamePrefix("taskExecutor--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        //修改拒绝策略为使用当前线程执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化线程池
        taskExecutor.initialize();
        return taskExecutor;
    }
}