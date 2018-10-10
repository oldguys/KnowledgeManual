package com.hrh.kmanual.sys.services;

import com.hrh.kmanual.commons.utils.Log4jUtils;
import com.hrh.kmanual.sys.config.DictionaryConfig;
import com.hrh.kmanual.sys.dao.entities.Dictionary;
import com.hrh.kmanual.sys.dao.jpas.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/15
 */
@Service
@DependsOn("staticValueMap")
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @PostConstruct
    public void initMap() {

        Log4jUtils.getInstance(getClass()).info("初始化字典数据...");
        List<Dictionary> dictionaries = dictionaryRepository.findByType(DictionaryConfig.DictionaryType.STATIC_VALUES.getValue());
        for (Dictionary dictionary : dictionaries) {
            DictionaryConfig.getStaticValueMap().put(dictionary.getDictionaryKey(), dictionary.getValue());
        }
    }
}
