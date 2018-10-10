package com.hrh.kmanual.modules.services;/**
 * Created by Administrator on 2018/9/24 0024.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/24 0024 11:02
 */
@Service
public class JdbcObjectService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


}
