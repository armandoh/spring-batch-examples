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
public class WelcomeTasklet implements Tasklet {

    private static final Log log = LogFactory.getLog(WelcomeTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {


        StringBuilder sb = new StringBuilder();
        sb.append("\n **************************     WELCOME MESSAGE     **************************").append("\n");
        sb.append("").append("\n");
        sb.append(" XXX         XXX             XX                                                   XXXX ").append("\n");
        sb.append("  XX         XX               X                                                   XXXX ").append("\n");
        sb.append("  XX         XX               X                                                    XX  ").append("\n");
        sb.append("  XX         XX     XXXXX     X     XXXXX    XXXXX  XXXXXX    XXXXX   XXXXX        XX  ").append("\n");
        sb.append("  XX    X    XX    X     X    X    X     x  X     X  XX   XXXX   XX  X     X       XX  ").append("\n");
        sb.append("  XX   XXX   XX    XXXXXXX    X    X        X     X  XX    XX    XX  XXXXXXX       XX  ").append("\n");
        sb.append("   XX XX XX XX     X          X    X        X     X  XX    XX    XX  X             XX  ").append("\n");
        sb.append("    XXX   XXX      X     X    X    X     x  X     X  XX          XX  X     X           ").append("\n");
        sb.append("    XXX   XXX       XXXXX   XXXXX   XXXXX    XXXXX  XXX          XXX  XXXXX        X   ").append("\n");
        sb.append("").append("\n");

        sb.append(" **************************      WELCOME MESSAGE      **************************");
        sb.append("\n");
        //System.out.println(sb.toString());
        log.info(sb.toString());


        return RepeatStatus.FINISHED;
    }
}