package com.test.batch.partition.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Armando on 30/07/2014.
 */
@Configuration
@EnableBatchProcessing
public class InfrastructureConfigurationImpl implements InfrastructureConfiguration {

    @Bean
    public org.springframework.core.task.TaskExecutor taskExecutor() {
        org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor taskExecutor = new org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(15);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
