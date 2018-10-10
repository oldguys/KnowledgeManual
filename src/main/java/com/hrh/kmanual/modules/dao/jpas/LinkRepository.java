package com.hrh.kmanual.modules.dao.jpas;

import com.hrh.kmanual.modules.dao.entites.Link;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/9/7
 */
public interface LinkRepository extends JpaRepository<Link, Long> {

    /**
     *  分组查询列表
     */
    String linkSequence = "SELECT NO, id, tags, REPLACE ( SUBSTRING( SUBSTRING_INDEX(tags, ';', a. NO), CHAR_LENGTH( SUBSTRING_INDEX(tags, ';', a. NO - 1) ) + 1 ), ';', '' ) AS tag FROM sys_sequence a CROSS JOIN ( SELECT id, tags, ( LENGTH(tags) - LENGTH(REPLACE(tags, ';', '')) ) / 1 size FROM link) b ON a. NO <= b.size";

    /**
     *  获取分组统计的标签
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT a.`value`,count(b.tag) count FROM dictionary a left JOIN ( " + linkSequence + " ) b on a.`value` = b.tag where type = 'knowledge-tags' GROUP BY a.`value` ")
    List<Object> findCountByTags();
    /**
     *  获取标签
     * @param tag
     * @param sort
     * @return
     */
    List<Link> findByTagsContaining(String tag, Sort sort);
}
