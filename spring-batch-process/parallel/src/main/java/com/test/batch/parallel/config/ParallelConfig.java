package com.test.batch.parallel.config;

import com.test.batch.parallel.domain.User;
import com.test.batch.parallel.listener.MainJobExecutionListener;
import com.test.batch.parallel.listener.TraceListener;
import com.test.batch.parallel.processor.FirstUserProcessor;
import com.test.batch.parallel.processor.SecondUserProcessor;
import com.test.batch.parallel.tasklet.*;
import com.test.batch.parallel.writer.LogItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.Tasklet;
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
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * Created by Armando on 30/07/2014.
 */
@Configuration
@Import(InfrastructureConfigurationImpl.class)
public class ParallelConfig {

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Autowired
    private InfrastructureConfiguration infrastructureConfiguration;


    /**
     * Main Job to execute
     */
    @Bean
    public Job mainJob() {
        return jobBuilders.get("mainJob")
                .listener(jobListener())
                .start(mainFlow())
                .build()
                .listener(traceListener())
                .build();
    }

    /**
     * Main Flow to execute, it contains the flows and steps to run in partition
     * with the SimpleAsyncTaskExecutor
     * <p/>
     * It starts the execution of a step then 3 different flows running in partition
     */

    @Bean
    public SimpleFlow mainFlow() {

        return new FlowBuilder<SimpleFlow>("Main Flow")
                .start(stepInit())
                .split(new SimpleAsyncTaskExecutor())
                .add(flow_12(), flow_AB(), flow_XY())
                .end();
    }

    /**
     * Initial step to execute
     * Call a tasklet
     */

    @Bean
    public Step stepInit() {
        return stepBuilders.get("stepInit")
                .tasklet(new SleepyTasklet())
                .listener(traceListener())
                .build();
    }


    /**
     * Flow that is compound by a couple of steps
     * Step 1 and Step 2, these 2 run sequentially
     * <p/>
     * Notice that Step2 follows Step 1 execution!
     */
    @Bean
    public Flow flow_12() {
        return new FlowBuilder<SimpleFlow>("flow_12")
                .start(step1())
                .next(step2())
                .build();
    }

    /**
     * Flow that is compound by a couple of steps
     * StepA and StepB, these 2 run sequentially
     * <p/>
     * Notice that StepB follows StepA execution!
     */

    @Bean
    public Flow flow_AB() {
        return new FlowBuilder<SimpleFlow>("flow_AB")
                .start(stepA())
                .next(stepB())
                .build();
    }

    /**
     * Flow that is compound by a couple of steps
     * StepX and StepY, these 2 run sequentially
     * <p/>
     * Notice that StepY follows StepX execution!
     */

    @Bean
    public Flow flow_XY() {
        return new FlowBuilder<SimpleFlow>("flow_XY")
                .start(stepX())
                .next(stepY())
                .build();
    }

    /**
     * Tasklet section...
     * <p/>
     * Used by the steps
     */


    @Bean
    public Tasklet tasklet_Hello() {
        return new HelloWorldTasklet();
    }


    @Bean
    public Tasklet tasklet_Welcome() {
        return new WelcomeTasklet();
    }

    @Bean
    public Tasklet tasklet_X() {
        return new DummyXTasklet();
    }

    @Bean
    public Tasklet tasklet_Y() {
        return new DummyYTasklet();
    }


    /**
     * Steps section...
     * <p/>
     * Used by the flows
     */

    @Bean
    public Step step1() {
        return stepBuilders.get("step1")
                .<User, User>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(infrastructureConfiguration.taskExecutor())
                .throttleLimit(10)
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilders.get("step2")
                .<User, User>chunk(1)
                .reader(reader())
                .processor(secondProcessor())
                .writer(writer())
                .taskExecutor(infrastructureConfiguration.taskExecutor())
                .throttleLimit(10)
                .build();
    }


    @Bean
    public Step stepA() {
        return stepBuilders.get("stepA")
                .tasklet(tasklet_Welcome())
                .listener(traceListener())
                .build();
    }


    @Bean
    public Step stepB() {
        return stepBuilders.get("stepB")
                .tasklet(tasklet_Hello())
                .listener(traceListener())
                .build();
    }

    @Bean
    public Step stepX() {
        return stepBuilders.get("stepX")
                .tasklet(tasklet_X())
                .listener(traceListener())
                .build();
    }


    @Bean
    public Step stepY() {
        return stepBuilders.get("stepY")
                .tasklet(tasklet_Y())
                .listener(traceListener())
                .build();
    }

    /**
     * Processor section...
     * <p/>
     * Used by the steps to process data
     */

    @Bean
    public ItemProcessor<User, User> processor() {

        return new FirstUserProcessor<User>();
    }

    @Bean
    public ItemProcessor<User, User> secondProcessor() {

        return new SecondUserProcessor<User>();
    }


    /**
     * Writer section...
     * <p/>
     * Used by the steps to write data
     */

    @Bean
    public ItemWriter<User> writer() {
        return new LogItemWriter<User>();
    }


    /**
     * Reader section...
     * <p/>
     * Used by the steps to read data from different sources
     */

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


    /**
     * Resource section...
     * <p/>
     * Used by the readers to locate data sources
     */

    @Bean
    public Resource resource() {
        //Set the file name to read, must be available in the classpath
        return new ClassPathResource("dummy-metadata.csv");
    }




    /**
     * Listener section...
     * <p/>
     * Used by the job to listen and monitor all of the events
     */


    @Bean
    public TraceListener traceListener() {
        return new TraceListener();
    }

    @Bean
    public MainJobExecutionListener jobListener() {
        return new MainJobExecutionListener();
    }


}
