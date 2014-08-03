package com.test.batch.parallel.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.*;

/**
 * Created by Armando on 30/07/2014.
 */
public class MainJobExecutionListener implements JobExecutionListener {

    private static final Log log = LogFactory.getLog(MainJobExecutionListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n####################################################### \n");

        sb.append("Stats for ").append(jobExecution.getJobInstance().getJobName()).append(" \n\n");
        java.sql.Timestamp tm = new java.sql.Timestamp(jobExecution.getCreateTime().getTime());
        sb.append("  Created     : ").append(jobExecution.getCreateTime()).append(" \n");
        sb.append("  Started     : ").append(jobExecution.getStartTime()).append(" \n");
        sb.append("  Finished    : ").append(jobExecution.getEndTime()).append(" \n");
        sb.append("  Exit-Code   : ").append(jobExecution.getExitStatus().getExitCode() ).append(" \n");
        sb.append("  Exit-Descr. : ").append(jobExecution.getExitStatus().getExitDescription()).append(" \n");
        sb.append("  Status      : ").append(jobExecution.getStatus()).append(" \n");

        sb.append("  Time Elapsed: ").append(jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime()).append(" milliseconds. \n");
        sb.append("\n####################################################### \n");

        sb.append("Job-Parameters: \n");
        JobParameters jp = jobExecution.getJobParameters();

        for(java.util.Map.Entry<String, JobParameter> entry: jp.getParameters().entrySet()){
            sb.append("  ").append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
        }
        sb.append("\n####################################################### \n");
        /*
        for (Iterator<Map.Entry<String, JobParameter>> iter = jp.getParameters().entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<String, JobParameter> entry = iter.next();
            sb.append("  " + entry.getKey() + "=" + entry.getValue() + "\n");
        }
        */


        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            sb.append("\n####################################################### \n");
            sb.append(" >Step        :").append(  stepExecution.getStepName() ).append(" \n");
            sb.append(" >Started     :").append(  stepExecution.getStartTime() ).append(" \n");
            sb.append(" >WriteCount  :").append(  stepExecution.getWriteCount() ).append(" \n");
            sb.append(" >Commits     :").append(  stepExecution.getCommitCount() ).append(" \n");
            sb.append(" >SkipCount   :").append(  stepExecution.getSkipCount() ).append(" \n");
            sb.append(" >Rollbacks   :").append(  stepExecution.getRollbackCount() ).append(" \n");
            sb.append(" >Filter      :").append(  stepExecution.getFilterCount() ).append(" \n");
            sb.append(" >Finished    :").append(  stepExecution.getEndTime()).append(" \n");
            sb.append(" >Status      :").append(  stepExecution.getStatus()).append(" \n");
            sb.append(" >Time Elapsed: ").append(stepExecution.getEndTime().getTime() - stepExecution.getStartTime().getTime()).append(" milliseconds. \n");

            sb.append("\n####################################################### \n");
        }
        log.info(sb.toString());
    }

    @Override
    public void beforeJob(JobExecution arg0) {

    }
}
