package com.hrh.kmanual.modules.dao.jpas;

import com.hrh.kmanual.modules.dao.entites.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/7
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    String COMMON_COLUMNS = " id,create_time,name,sort_order,type,parent_id,status ";

    /**
     *  更新父级ID
     * @param parentId
     * @param id
     * @return
     */
    @Modifying
    @Query(nativeQuery = true,value = "update menu SET parent_id = :parentId WHERE id = :id ")
    int updateParentIdById( @Param("parentId") Long parentId , @Param("id") Long id);

    /**
     *
     * @param parentId
     * @return
     */
    @Query(nativeQuery = true
            , value = "select " + COMMON_COLUMNS + " from menu where parent_id = :parentId and status = 1")
    List<Menu> findByParentId(@Param("parentId") Long parentId);

    /**
     *
     * @return
     */
    @Query(nativeQuery = true, value = "select " + COMMON_COLUMNS + " from menu where parent_id is null and status = 1")
    List<Menu> findMenus();

    /**
     * 通过nativeQuery获取，{@link Menu}
     * @param status
     * @return
     */
    List<Menu> findAllByStatus(Integer status);
}
