package com.test.config;

import javax.sql.DataSource;

/**
 * Created by Armando on 24/07/2014.
 */
public interface InfrastructureConfiguration {

    @org.springframework.context.annotation.Bean
    public abstract DataSource dataSource();
}
