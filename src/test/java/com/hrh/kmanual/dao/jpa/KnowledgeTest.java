package com.hrh.kmanual.dao.jpa;

import com.hrh.kmanual.commons.utils.JsonUtils;
import com.hrh.kmanual.modules.dao.entites.Knowledge;
import com.hrh.kmanual.modules.dao.jpas.KnowledgeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.util.*;

/**
 * @author huangrenhao
 * @date 2018/8/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KnowledgeTest {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Test
    public void testFindByTagsContaining(){
        System.out.println(knowledgeRepository.findByTagsContaining("sql"));
    }

    @Test
    public void testFindCountByTags(){
       List<Object> resultSet =  knowledgeRepository.findCountByTags();

        resultSet.forEach(e->{
            System.out.println(e);
        });
    }

    @Test
    public void testSave(){
        List<Knowledge> knowledgeList = new ArrayList<>();

        for (int i = 1 ; i < 101 ; i++){
            Knowledge knowledge = new Knowledge();
            knowledge.setName("know"+i);
            knowledge.setStatus(1);
            knowledge.setTags("测试"+i);
            knowledge.setCreateTime(new Date());
            knowledge.setContext("测试:"+i+" 。。。。。。。");
            knowledgeList.add(knowledge);
        }

        knowledgeRepository.save(knowledgeList);
    }

}
