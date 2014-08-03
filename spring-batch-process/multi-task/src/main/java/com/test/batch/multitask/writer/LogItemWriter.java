package com.test.batch.multitask.writer;

import org.apache.commons.logging.Log;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

import static org.apache.commons.logging.LogFactory.getLog;

/**
 * Created by Armando on 28/07/2014.
 */
public class LogItemWriter<T> implements ItemWriter<T> {

    private static final Log log = getLog(LogItemWriter.class);

    public void write(List<? extends T> data) throws Exception {
        System.out.println(" ***********************  about to WRITE processed data  ***********************");
        //for now just log data
        log.info(data);

        //Processed data could be written to:
        // - Queue
        // - Database
        // - Flat file
    }
}
