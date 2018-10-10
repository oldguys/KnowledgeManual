package com.hrh.kmanual.modules.dao.jpas;/**
 * Created by Administrator on 2018/9/11 0011.
 */

import com.hrh.kmanual.modules.dao.entites.KnowledgeCollection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/11 0011 10:57
 */
public interface KnowledgeCollectionRepository extends JpaRepository<KnowledgeCollection, Long> {

    /**
     *  获取文档集合
     * @param id
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM knowledge_collection WHERE id = (SELECT collection_id FROM knowledge WHERE  id = :id)")
    KnowledgeCollection findByKnowledgeId(@Param("id") Long id);

    /**
     *  模糊查询名
     * @param name
     * @param sort
     * @return
     */
    List<KnowledgeCollection> findByNameContaining(String name, Sort sort);
}
