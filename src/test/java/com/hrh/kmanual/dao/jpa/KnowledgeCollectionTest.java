package com.hrh.kmanual.dao.jpa;/**
 * Created by Administrator on 2018/9/11 0011.
 */

import com.hrh.kmanual.modules.dao.entites.KnowledgeCollection;
import com.hrh.kmanual.modules.dao.jpas.KnowledgeCollectionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/11 0011 15:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KnowledgeCollectionTest {

    @Autowired
    private KnowledgeCollectionRepository knowledgeCollectionRepository;

    @Test
    public void testSave() {

        List<KnowledgeCollection> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            KnowledgeCollection entity = new KnowledgeCollection();
            entity.setStatus(1);
            entity.setDescription("测试" + i);
            entity.setCreateTime(new Date());
            entity.setName("测试" + i);
            list.add(entity);
        }
        knowledgeCollectionRepository.save(list);
    }
}
