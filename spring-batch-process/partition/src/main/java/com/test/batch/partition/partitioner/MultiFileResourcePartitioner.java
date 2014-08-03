package com.test.batch.partition.partitioner;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Armando on 30/07/2014.
 */
public class MultiFileResourcePartitioner implements Partitioner {

    //Just for test
    private String inboundDir = "/home/projects/spring-batch-process/partition/src/test/resources";

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitionMap = new HashMap<String, org.springframework.batch.item.ExecutionContext>();
        File dir = new File(inboundDir);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                ExecutionContext context = new ExecutionContext();
                context.put("fileResource", file.toURI().toString());
                partitionMap.put(file.getName(), context);
            }
        }
        return partitionMap;
    }


    public String getInboundDir() {

        return inboundDir;
    }

    public void setInboundDir(String inboundDir) {

        this.inboundDir = inboundDir;
    }

}
