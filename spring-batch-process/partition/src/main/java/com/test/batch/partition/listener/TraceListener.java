package com.test.batch.partition.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * Created by Armando on 01/08/2014.
 */
public class TraceListener implements StepExecutionListener, JobExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println(String.format(" ** Before step '%s'", stepExecution.getStepName()));
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println(String.format(" ** After step '%s' : %s", stepExecution.getStepName(), stepExecution.getExitStatus().getExitCode()));
        return null;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println(String.format(" ** Before job '%s'", jobExecution.getJobInstance().getJobName()));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println(String.format(" ** After job '%s'", jobExecution.getJobInstance().getJobName()));

        if (!jobExecution.getAllFailureExceptions().isEmpty()) {
            System.out.println(String.format("    Exiting with %s failures :", jobExecution.getAllFailureExceptions().size()));
            for (Throwable failure : jobExecution.getAllFailureExceptions()) {
                System.out.println(String.format("    - %s", failure.getMessage()));
            }
        }
    }


}
