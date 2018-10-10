package com.hrh.kmanual.sys.dao.jpas;

import com.hrh.kmanual.sys.dao.entities.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/15
 */
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    /**
     *  获取字典表
     * @param key
     * @return
     */
    List<Dictionary> findByDictionaryKey(String key);

    /**
     *  按类别获取
     * @param type
     * @return
     */
    List<Dictionary> findByType(String type);
}
