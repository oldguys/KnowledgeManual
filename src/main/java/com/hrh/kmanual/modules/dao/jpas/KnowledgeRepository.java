package com.hrh.kmanual.modules.dao.jpas;

import com.hrh.kmanual.modules.dao.entites.Knowledge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/14
 */
public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

    /**
     *  分组查询列表
     */
    String knowledgeSequence = "SELECT NO, id, tags, REPLACE ( SUBSTRING( SUBSTRING_INDEX(tags, ';', a. NO), CHAR_LENGTH( SUBSTRING_INDEX(tags, ';', a. NO - 1) ) + 1 ), ';', '' ) AS tag FROM sys_sequence a CROSS JOIN ( SELECT id, tags, ( LENGTH(tags) - LENGTH(REPLACE(tags, ';', '')) ) / 1 size FROM knowledge) b ON a. NO <= b.size";

    /**
     *  修改排序
     * @param id
     * @param sort
     * @return
     */
    @Transactional(rollbackOn = RuntimeException.class)
    @Modifying
    @Query(nativeQuery = true , value = "UPDATE knowledge SET sort_order = :sort WHERE id = :id")
    int updateSort(@Param("id") Long id , @Param("sort") Integer sort);

    /**
     *  通过状态获取
     * @param status
     * @return
     */
    @Query(value = "SELECT new Knowledge(id,name) FROM Knowledge WHERE status=:status")
    List<Knowledge> findByStatus(@Param("status") Integer status);

    /**
     *  获取分组统计的标签
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT a.`value`,count(b.tag) count FROM dictionary a left JOIN ( " + knowledgeSequence + " ) b on a.`value` = b.tag where type = 'knowledge-tags' GROUP BY a.`value` ")
    List<Object> findCountByTags();

    /**
     * 获取指定目录下的内容体
     * @param menuIds
     * @return
     */
    @Query(" FROM Knowledge a WHERE a.menu.id IN (:menuIds)")
    List<Knowledge> findAllByMenuIds(@Param("menuIds") Collection<Long> menuIds);

    /**
     *  通过标识和文档名模糊查询
     * @param stringText
     * @param pageable
     * @return
     */
    @Query("select" +
            " new Knowledge(a.id,a.name,a.createTime,a.tags,a.status)" +
            " from Knowledge a " +
            "where a.name Like CONCAT('%',:queryText,'%') " +
            "OR a.tags Like CONCAT('%',:queryText,'%') ")
    Page<Knowledge> findByStringTextPage(@Param("queryText") String stringText, Pageable pageable);

    /**
     * 通过标识模糊查询
     * @param tag
     * @return
     */
    List<Knowledge> findByTagsContaining(String tag);

}
