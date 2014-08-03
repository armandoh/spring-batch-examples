package com.test.batch.multithread.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Armando on 30/07/2014.
 */
@Configuration
@EnableBatchProcessing
public class InfrastructureConfigurationImpl implements InfrastructureConfiguration {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //Setting the core pool size allows to have as much Threads working
        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(30);
        //taskExecutor.setMaxPoolSize(Integer.MAX_VALUE);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
