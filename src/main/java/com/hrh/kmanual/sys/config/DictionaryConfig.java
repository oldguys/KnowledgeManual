package com.hrh.kmanual.sys.config;

import com.hrh.kmanual.sys.dao.entities.*;
import com.hrh.kmanual.sys.dao.entities.Dictionary;
import com.hrh.kmanual.sys.dao.jpas.DictionaryRepository;
import com.hrh.kmanual.sys.dao.jpas.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

/**
 * 字典表 描述表
 *
 * @author huangrenhao
 * @date 2018/8/15
 */
@Configuration
public class DictionaryConfig {


    /**
     * 静态常量
     */
    private static Map<String, String> staticValueMap;

    private static Map<String, String> knowledgeTypeMap;

    static {
        DictionaryType[] types = DictionaryType.values();
        knowledgeTypeMap = new HashMap<>(types.length);

        for (DictionaryType type : types) {
            knowledgeTypeMap.put(type.getKey(), type.getValue());
        }
    }

    /**
     * 样式配置参数注入
     */
    @Value("${km.configs.static-values-properties}")
    public String staticValuesPropertiesConfig;
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private SequenceRepository sequenceRepository;


    @PostConstruct
    public void initMenuType() {
        List<Dictionary> dictionaryList = dictionaryRepository.findByType("menu-types");
        if (dictionaryList.size() == 0) {

            Dictionary menuType1 = new Dictionary();
            menuType1.setName("文档目录");
            menuType1.setDictionaryKey("knowledge-menu");
            menuType1.setType("menu-types");
            menuType1.setStatus(1);
            menuType1.setValue("文档目录");
            menuType1.setValueType("string");
            menuType1.setCreateTime(new Date());

            Dictionary menuType2 = new Dictionary();
            menuType2.setName("导航栏目录");
            menuType2.setDictionaryKey("leader-menu");
            menuType2.setType("menu-types");
            menuType2.setStatus(1);
            menuType2.setValue("导航栏目录");
            menuType2.setValueType("string");
            menuType2.setCreateTime(new Date());

            dictionaryRepository.save(menuType1);
            dictionaryRepository.save(menuType2);

        }

        if (sequenceRepository.findAll().size() == 0) {
            List<Sequence> sequences = new ArrayList<>(100);
            for (int i = 0; i < 100; i++) {
                sequences.add(new Sequence(i));
            }
            sequenceRepository.save(sequences);
        }
    }

    /**
     * 样式配置参数注入
     *
     * @return
     * @throws IOException
     */
    @Bean("staticValueMap")
    public Map<String, String> setStaticValueMap() throws IOException {

        staticValueMap = new HashMap<>(5);
        Properties properties = PropertiesLoaderUtils.loadAllProperties(staticValuesPropertiesConfig);

        for (Object key : properties.keySet()) {
            staticValueMap.put(String.valueOf(key), String.valueOf(properties.get(key)));
        }
        return staticValueMap;
    }

    public static Map<String, String> getStaticValueMap() {
        return staticValueMap;
    }

    public static Map<String, String> getKnowledgeTypeMap() {
        return knowledgeTypeMap;
    }

    public enum DictionaryType {

        STATIC_VALUES("样式静态常量", "static-values"),
        KNOWLEDGE_TAGS("文档标签类别", "knowledge-tags"),
        MENU_TYPES("目录类型", "menu-types");

        private String key;
        private String value;

        DictionaryType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
