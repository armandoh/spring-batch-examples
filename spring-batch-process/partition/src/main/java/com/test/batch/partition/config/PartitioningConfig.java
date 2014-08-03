package com.test.batch.partition.config;

import com.test.batch.partition.domain.User;
import com.test.batch.partition.listener.MainJobExecutionListener;
import com.test.batch.partition.listener.TraceListener;
import com.test.batch.partition.partitioner.MultiFileResourcePartitioner;
import com.test.batch.partition.processor.FirstUserProcessor;
import com.test.batch.partition.processor.SecondUserProcessor;
import com.test.batch.partition.writer.LogItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * Created by Armando on 30/07/2014.
 */
@Configuration
@Import(InfrastructureConfigurationImpl.class)
public class PartitioningConfig {

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Autowired
    private InfrastructureConfiguration infrastructureConfiguration;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Bean
    public Job mainJob() {
        return jobBuilders.get("mainJob")
                .listener(jobListener())
                .start(partitionStep())
                .build();
    }


    @Bean
    public Step partitionStep(){
        return stepBuilders.get("partitionStep")
                .partitioner(step1())
                .partitioner("step1", partitioner())
                //.taskExecutor(infrastructureConfiguration.taskExecutor())

                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilders.get("step1")
                .<User,User>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(infrastructureConfiguration.taskExecutor())
                .throttleLimit(10)
                //.listener(logProcessListener())
                .build();
    }




    @Bean
    public MainJobExecutionListener jobListener() {

        return new MainJobExecutionListener();
    }



    @Bean
    public ItemProcessor<User, User> processor() {

        return new FirstUserProcessor<User>();
    }

    @Bean
    public ItemProcessor<User, User> secondProcessor() {

        return new SecondUserProcessor<User>();
    }

    @Bean
    public ItemWriter<User> writer() {
        return new LogItemWriter<User>();
    }

    @Bean
    public FlatFileItemReader<User> reader() {
        FlatFileItemReader<User> itemReader = new FlatFileItemReader<User>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setResource(resource());
        return itemReader;
    }

    @Bean
    public LineMapper<User> lineMapper() {
        System.out.println(" ///////////////////////  about to read data  ///////////////////////");
        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<User>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"id", "name", "email", "gender"});
        //lineTokenizer.setIncludedFields(new int[]{0, 2});
        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<User>();
        fieldSetMapper.setTargetType(User.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public Resource resource() {
        //Set the file name to read, must be available in the classpath
        return new ClassPathResource("dummy-metadata.csv");
    }


    @Bean
    public Partitioner partitionerCustom() {

        return new MultiFileResourcePartitioner();
    }


    @Bean
    public TraceListener traceListener() {
        return new TraceListener();
    }



    @Bean
    public Partitioner partitioner(){
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        Resource[] resources;
        try {
            resources = resourcePatternResolver.getResources("file:src/test/resources/*.csv");
        } catch (IOException e) {
            throw new RuntimeException("I/O problems when resolving the input file pattern.",e);
        }
        partitioner.setResources(resources);
        System.out.println("Resource files: " + resources.length);
        return partitioner;
    }


}
