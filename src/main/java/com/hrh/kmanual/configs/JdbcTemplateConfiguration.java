package com.hrh.kmanual.configs;/**
 * Created by Administrator on 2018/9/24 0024.
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/24 0024 10:58
 */
@Configuration
public class JdbcTemplateConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DruidDataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
