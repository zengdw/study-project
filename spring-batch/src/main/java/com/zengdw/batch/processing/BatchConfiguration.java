package com.zengdw.batch.processing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import java.io.FileNotFoundException;

/**
 * batch配置
 * 基本概念 https://www.chkui.com/article/spring/spring_batch_introduction
 * step控制 https://www.chkui.com/article/spring/spring_batch_step
 *
 * @author zengd
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Resource
    public JobBuilderFactory jobBuilderFactory;
    @Resource
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                //防止重启
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                //监听任务启动和结束
                .listener(listener)
                .flow(step1)
                .end()
                .build();

        //step按条件执行
        /*jobBuilderFactory.get("importUserJob")
                //执行失败直接退出
                .start(step1()).on("FAILED").end()
                //有跳过元素执行 errorPrint1()
                .from(step1()).on("COMPLETED WITH SKIPS").to(errorPrint1())
                //默认（成功）情况下执行 Step2
                .from(step1()).on("*").to(step2())*/

        /*this.jobBuilderFactory.get("job")
                .start(step1()) //执行step1
                //step2的ExitStatus=FAILED 执行fail, fail会尝试重启执行新的JobExecution
                .next(step2()).on("FAILED").fail()
                //否则执行step3
                .from(step2()).on("*").to(step3())
                .end()
                .build();*/
    }

    @Bean
    public Step step1(@Qualifier("writer1") JdbcBatchItemWriter<Person> writer, @Qualifier("staticReader") ItemReader<Person> reader,
                      PersonItemProcessor processor, PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("step1")
                .transactionManager(transactionManager)
                //分片配置
                .<Person, Person>chunk(10)
                .reader(reader)
                //回滚后数据重读
                .readerIsTransactionalQueue()
                .processor(processor)
                .writer(writer)
                //表示启用对应的容错功能。
                .faultTolerant()
                //不必回滚的异常
                .noRollback(ValidationException.class)
                //表示当跳过的次数超过数值时则会导致整个Step失败
                .skipLimit(10)
                //处理过程中抛出FlatFileParseException异常时就跳过该条记录的处理
                .skip(FlatFileParseException.class)
                //FileNotFoundException异常不能跳过
                .noSkip(FileNotFoundException.class)
                //失败重试次数
                .retryLimit(3)
                //表示只有捕捉到该异常才会重试
                .retry(DeadlockLoserDataAccessException.class)
                //配置step重启次数
                .startLimit(1)
                //添加拦截器
                //.listener()
                .build();
    }
}
