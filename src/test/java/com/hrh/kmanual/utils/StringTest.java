package com.hrh.kmanual.utils;/**
 * Created by Administrator on 2018/9/20 0020.
 */

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/20 0020 21:02
 */
public class StringTest {

    @Test
    public void test() {
        String resources = "";

        if(StringUtils.isNotBlank(resources)){
            String[] patterns = resources.split(";");
            int i = 0;
            for (String pattern : patterns) {
                System.out.println(i + ":" + pattern);
                i++;
            }
        }

    }
}
