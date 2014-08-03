package com.test.batch.partition.config;

/**
 * Created by Armando on 30/07/2014.
 */
public interface InfrastructureConfiguration {

    @org.springframework.context.annotation.Bean
    public abstract org.springframework.core.task.TaskExecutor taskExecutor();


}
