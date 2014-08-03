package com.test.batch.multitask.config;

import com.test.batch.multitask.domain.User;
import com.test.batch.multitask.processor.FirstUserProcessor;
import com.test.batch.multitask.processor.SecondUserProcessor;
import com.test.batch.multitask.writer.LogItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by Armando on 30/07/2014.
 */
@Configuration
@EnableBatchProcessing
public class MultiTaskConfig {

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;


    @Bean
    public Job mainJob() {
        return jobBuilders.get("mainJob")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilders.get("step1")
                .<User, User>chunk(1)
                .reader(reader())
                .processor(firstProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public Step step2(){
        return stepBuilders.get("step2")
                .<User, User>chunk(1)
                .reader(reader())
                .processor(secondProcessor())
                .writer(writer())
                .build();
        //This step is reusing the reader and writer
        //The processor is different regarding step1
    }

    @Bean
    public ItemProcessor<User, User> firstProcessor() {

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
    public FlatFileItemReader<User> reader(){
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


}
