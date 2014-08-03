package com.test.batch.partition.tasklet;

import org.apache.commons.logging.Log;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by Armando on 01/08/2014.
 */
public class SleepyTasklet implements Tasklet {

    private static final Log log = org.apache.commons.logging.LogFactory.getLog(DummyXTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {


        /**
         * READ:
         *
         * Pay attention to the order of the log and prints ...
         *
         * Since parallel processing is being done it might be overlapping the print outs and logging
         * completed by different all steps included in the flows
         *
         * **/


        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz                           zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz    Sleeping for a while   zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz                           zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");

        //Sleep one sec
        Thread.sleep(1000);
        //Once awake take a look at all that has happened  with the other parallel steps

        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz                           zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz    Finally Waking up !!   zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz                           zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        log.info("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");

        return RepeatStatus.FINISHED;
    }
}