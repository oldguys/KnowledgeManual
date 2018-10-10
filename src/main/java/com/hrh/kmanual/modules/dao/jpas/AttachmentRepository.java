package com.hrh.kmanual.modules.dao.jpas;

import com.hrh.kmanual.modules.dao.entites.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/31
 */
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    /**
     *  通过Id集合获取列表
     * @param ids
     * @return
     */
    @Query("from Attachment where id IN (:ids )")
    List<Attachment> findByIds(@Param("ids") Collection<Long> ids);

    List<Attachment> findByAsoKey(String asoKey);
}
