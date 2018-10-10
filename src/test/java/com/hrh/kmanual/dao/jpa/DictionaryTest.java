package com.hrh.kmanual.dao.jpa;


import com.hrh.kmanual.sys.config.DictionaryConfig;
import com.hrh.kmanual.sys.config.StaticValueConfig;
import com.hrh.kmanual.sys.dao.entities.Dictionary;
import com.hrh.kmanual.sys.dao.jpas.DictionaryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Date;


/**
 * @author huangrenhao
 * @date 2018/8/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DictionaryTest {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Test
    public void testSave() {
        Dictionary dictionary = new Dictionary();

        dictionary.setType(DictionaryConfig.DictionaryType.STATIC_VALUES.getValue());
        dictionary.setDictionaryKey(StaticValueConfig.BOOTSTRAP_NODE_ICON_KEY);
        dictionary.setValue("glyphicon glyphicon-folder-open");
        dictionary.setStatus(1);
        dictionary.setCreateTime(new Date());
        dictionary.setValueType("string");
        dictionary.setName("目录列表子菜单图标");

        dictionaryRepository.save(dictionary);
    }
}
