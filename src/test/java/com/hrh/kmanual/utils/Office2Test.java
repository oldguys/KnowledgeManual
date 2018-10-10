package com.hrh.kmanual.utils;/**
 * Created by Administrator on 2018/9/28 0028.
 */

import com.hrh.kmanual.commons.utils.Office2Utils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/28 0028 20:32
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class Office2Test {


    @Autowired
    private DocumentConverter documentConverter;

    @Test
    public void test() throws OfficeException, IOException {

        File source = new File("E:\\test\\文件\\HR-0145 异动流程 V1.2.xlsx");
        File target = new File("E:\\test\\文件\\侧是是是.pdf");
//        if(!target.exists()){
//            target.createNewFile();
//        }
        documentConverter.convert(source).to(target).execute();


    }
}
