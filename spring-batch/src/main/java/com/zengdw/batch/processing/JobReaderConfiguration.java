package com.zengdw.batch.processing;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 自定义数据读取器
 * @author: zengd
 * @date: 2020/10/29 15:41
 */
@Configuration
public class JobReaderConfiguration {
    @Resource
    private DataSource dataSource;

    /**
     * StepScope: 告诉容器直到Step执行的阶段才初始化这个@Bean
     * 通过游标读取数据
     */
    @Bean
    @StepScope
    public JdbcCursorItemReader<Person> jdbcCursorItemReader(){
        JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT first_name, last_name FROM people");
        reader.setRowMapper((resultSet, i) -> null);
        //设置ResultSet从数据库中一次读取记录的上限
        reader.setMaxRows(10);
        return reader;
    }

    /**
     * 定义批处理静态文件数据读取器
     */
    @Bean(name = "staticReader")
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names("firstName", "lastName")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    /**
     * 定义数据库读取器
     */
    @Bean(name = "jdbcPagingReader")
    public JdbcPagingItemReader<Person> flatFileDemoJdbcPagingReader() {
        JdbcPagingItemReader<Person> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource);
        //设置每次分页读取的条数
        reader.setFetchSize(10);
        reader.setRowMapper((rs, rowNum) -> new Person(rs.getInt("id"), rs.getString("firstName"),rs.getString("lastName")));
        //定义查询的数据来源
        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("firstName, lastName");
        queryProvider.setFromClause("from people");

        //读取出来的数据进行排序
        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        reader.setQueryProvider(queryProvider);
        return reader;
    }
}
