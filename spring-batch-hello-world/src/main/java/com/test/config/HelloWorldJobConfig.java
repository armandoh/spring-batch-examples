package com.test.config;

import org.springframework.batch.core.Step;

import javax.sql.DataSource;

/**
 * Created by Armando on 24/07/2014.
 */
@org.springframework.context.annotation.Configuration
@org.springframework.context.annotation.Import(StandaloneInfrastructureConfiguration.class)
public class HelloWorldJobConfig {

    @org.springframework.beans.factory.annotation.Autowired
    private org.springframework.batch.core.configuration.annotation.JobBuilderFactory jobBuilders;

    @org.springframework.beans.factory.annotation.Autowired
    private org.springframework.batch.core.configuration.annotation.StepBuilderFactory stepBuilders;

    @org.springframework.beans.factory.annotation.Autowired
    private InfrastructureConfiguration infrastructureConfiguration;

    @org.springframework.beans.factory.annotation.Autowired
    private DataSource dataSource; // just for show...

    @org.springframework.context.annotation.Bean
    public org.springframework.batch.core.Job helloWorldJob() {
        return jobBuilders.get("helloWorldJob")
                .start(step())
                .build();
    }

    @org.springframework.context.annotation.Bean
    public Step step() {
        return stepBuilders.get("step")
                .tasklet(tasklet())
                .build();
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.batch.core.step.tasklet.Tasklet tasklet() {
        return new com.test.HelloWorldTasklet();
    }
}

