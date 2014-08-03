package com.test.batch.parallel.tasklet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by Armando on 24/07/2014.
 */
public class HelloWorldTasklet implements Tasklet {

    private static final Log log = LogFactory.getLog(HelloWorldTasklet.class);
    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append("\n **************************     HELLO WORLD MESSAGE     **************************").append("\n");
        sb.append("").append("\n");
        sb.append(" XXX XXX           XX      XX             ").append("\n");
        sb.append("  X   X             X       X             ").append("\n");
        sb.append("  X   X             X       X             ").append("\n");
        sb.append("  X   X   XXXXX     X       X     XXXXX   ").append("\n");
        sb.append("  XXXXX  X     X    X       X    X     X  ").append("\n");
        sb.append("  X   X  XXXXXXX    X       X    X     X  ").append("\n");
        sb.append("  X   X  X          X       X    X     X  ").append("\n");
        sb.append("  X   X  X     X    X       X    X     X  ").append("\n");
        sb.append(" XXX XXX  XXXXX   XXXXX   XXXXX   XXXXX   ").append("\n");
        sb.append("                                          ").append("\n");
        sb.append("                                          ").append("\n");
        sb.append("                                          ").append("\n");
        sb.append("                                          ").append("\n");
        sb.append(" XXX XXX                   XX        XX   ").append("\n");
        sb.append("  X   X                     X         X   ").append("\n");
        sb.append("  X   X                     X         X   ").append("\n");
        sb.append("  X   X   XXXXX  XXX XX     X     XXXXX   ").append("\n");
        sb.append("  X X X  X     X   XX  X    X    X    X   ").append("\n");
        sb.append("  X X X  X     X   X        X    X    X   ").append("\n");
        sb.append("  X X X  X     X   X        X    X    X   ").append("\n");
        sb.append("   X X   X     X   X        X    X    X   ").append("\n");
        sb.append("   X X    XXXXX  XXXXX    XXXXX   XXXXXX  ").append("\n");
        sb.append("").append("\n");

        sb.append(" **************************     HELLO WORLD MESSAGE      **************************");
        sb.append("\n");
        //System.out.println(sb.toString());
        log.info(sb.toString());

        return RepeatStatus.FINISHED;
    }
}
