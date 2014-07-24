package com.test.config;

import javax.sql.DataSource;
/**
 * Created by Armando on 24/07/2014.
 */
@org.springframework.context.annotation.Configuration
@org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
public class StandaloneInfrastructureConfiguration implements InfrastructureConfiguration {

    @org.springframework.context.annotation.Bean
    public DataSource dataSource() {
        org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder();
        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-hsqldb.sql")
                .addScript("classpath:org/springframework/batch/core/schema-hsqldb.sql")
                .setType(org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL)
                .build();
    }

}
