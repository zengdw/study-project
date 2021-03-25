package com.zengdw.batch.processing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 批处理完成时的监听
 * @author zengd
 */
@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.debug("!!! JOB FINISHED! Time to verify the results");

            jdbcTemplate.query("SELECT first_name, last_name FROM people",
                    (rs, row) -> new Person(
                            0,
                            rs.getString(1),
                            rs.getString(2))
            ).forEach(person -> log.debug("Found <" + person + "> in the database."));
        }
    }

}
