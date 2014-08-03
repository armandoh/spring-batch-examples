package com.test.batch.parallel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

/**
 * Created by Armando on 30/07/2014.
 */
public interface InfrastructureConfiguration {

    @Bean
    public abstract TaskExecutor taskExecutor();


}
