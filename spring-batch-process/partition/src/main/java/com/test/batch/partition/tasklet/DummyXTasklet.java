package com.test.batch.partition.tasklet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by Armando on 01/08/2014.
 */
public class DummyXTasklet implements Tasklet {

    private static final Log log = LogFactory.getLog(DummyXTasklet.class);

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

        System.out.println("                                    XXX        XXX ");
        System.out.println("                                     XXX      XXX  ");
        System.out.println("                                      XXX    XXX   ");
        System.out.println("                                       XXX  XXX    ");
        System.out.println("                                       XX XX XX    ");
        System.out.println("                                       XX XX XX    ");
        System.out.println("                                      XXX    XXX   ");
        System.out.println("                                     XXX      XXX  ");
        System.out.println("                                    XXX        XXX ");

        StringBuilder sb = new StringBuilder();
        sb.append("\n **************************     PRINTING X MESSAGE     **************************").append("\n");
        sb.append("").append("\n");
        /*
        sb.append("                                    XXX        XXX ").append("\n");
        sb.append("                                     XXX      XXX  ").append("\n");
        sb.append("                                      XXX    XXX   ").append("\n");
        sb.append("                                       XXX  XXX    ").append("\n");
        sb.append("                                       XX XX XX    ").append("\n");
        sb.append("                                       XX XX XX    ").append("\n");
        sb.append("                                      XXX    XXX   ").append("\n");
        sb.append("                                     XXX      XXX  ").append("\n");
        sb.append("                                    XXX        XXX ").append("\n");
        */

        sb.append("").append("\n");

        sb.append(" **************************      END OF X MESSAGE      **************************");
        sb.append("\n");
        //System.out.println(sb.toString());
        log.info(sb.toString());
        return RepeatStatus.FINISHED;
    }
}