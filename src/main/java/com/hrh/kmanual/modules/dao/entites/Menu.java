package com.hrh.kmanual.modules.dao.entites;

import com.hrh.kmanual.commons.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;



@NamedNativeQueries(
        value = {
                @NamedNativeQuery(name = "Menu.findAllByStatus"
                        , query = "SELECT a.id,a.create_time,a.`name`,a.sort_order,a.type,a.`status`,IFNULL(b.count,0) count,c.id parent_id ,c.`name` parent_node_name FROM menu a " +
                        "LEFT JOIN (  SELECT COUNT(*) count,menu_id FROM knowledge a WHERE  a.status = 1 GROUP BY menu_id ) b on a.id = b.menu_id " +
                        "LEFT JOIN menu c on a.parent_id = c.id " +
                        "WHERE a.status = ?1"
                        , resultSetMapping = "MenuItems"),
                @NamedNativeQuery(name = "Menu.findMenus"
                        , query = "SELECT a.id,a.create_time,a.`name`,a.sort_order,a.type,a.`status`,IFNULL(b.count,0) count,c.id parent_id ,c.`name` parent_node_name FROM menu a " +
                        "LEFT JOIN (  SELECT COUNT(*) count,menu_id FROM knowledge a WHERE  a.status = 1 GROUP BY menu_id ) b on a.id = b.menu_id " +
                        "LEFT JOIN menu c on a.parent_id = c.id "
                        , resultSetMapping = "MenuItems"),
        }
)

@SqlResultSetMapping(name = "MenuItems"
        /**
        *  1.映射注入实体对象：
         *  如果 Field 被 @Transient ，则不会注入成功，
         *   结果值 为 实体对象。
        */
//        ,entities= {
//                @EntityResult(entityClass = Menu.class
////                        ,fields = {
////                        @FieldResult(name = "id",column = "id"),
////                        @FieldResult(name = "name",column = "name"),
////                        @FieldResult(name = "createTime",column = "create_time"),
////                        @FieldResult(name = "sortOrder",column = "sort_order"),
////                        @FieldResult(name = "type",column = "type"),
////                        @FieldResult(name = "status",column = "status"),
////                        @FieldResult(name = "count",column = "count1"), //  @Transient ，注入失效。
////                        }
//                )
//        }
        /***
        *  2.将结果集按照顺序注入写到Array，顺序为注解的排列顺序。返回值不再是 Entity ，
         *  并且由于反射，实体集和 List<Menu> 的 单个Entity 引用数组，但是不会报转换异常。
        */
//        ,columns = {
//                @ColumnResult(name = "id",type = Long.class),
//                @ColumnResult(name = "create_time",type = Date.class),
//                @ColumnResult(name = "name"),
//                @ColumnResult(name = "sort_order"),
//                @ColumnResult(name = "type"),
//                @ColumnResult(name = "status"),
//                @ColumnResult(name = "count1",type = Integer.class),
//        }
        /***
        *  3.构造器模式：
         *  自定义构造方式，使用值注入，注入对象可以认为是DTO。
         *  由于是DTO,所以不会触发级联操作。
        */
        , classes = {
        @ConstructorResult(targetClass = Menu.class,
                columns = {
                        @ColumnResult(name = "id",type = Long.class),
                        @ColumnResult(name = "name"),
                        @ColumnResult(name = "create_time"),
                        @ColumnResult(name = "status"),
                        @ColumnResult(name = "type"),
                        @ColumnResult(name = "sort_order"),
                        @ColumnResult(name = "count",type = Integer.class),
                        @ColumnResult(name = "parent_id",type = Long.class),
                        @ColumnResult(name = "parent_node_name"),
                }
             )
        }
)
@Entity
@Getter
@Setter
/**
 * @author huangrenhao
 * @date 2018/8/7
 */
public class Menu extends BaseEntity implements Comparable<Menu>{

    /**
     * 目录类别
     */
    private String type;

    private Integer sortOrder;

    /**
     * 文档统计数，不计入总数
     */
    @Transient
    private Integer count;
    /**
     * 父级目录
     */
    @Transient
    private Long parentId;
    /**
     * 父级目录名称
     */
    @Transient
    private String parentNodeName;

    /**
     *  是否叶子节点
     */
    @Transient
    private Boolean isLeaf;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parentId")
    private List<Menu> subMenu = Collections.emptyList();

    public Menu() {
    }

    public Menu(Long id) {
        super.setId(id);
    }

    public Menu(Long id, String name, Date createTime, Integer status, String type, Integer sortOrder, Integer count,Long parentId,String parentNodeName) {
        super(id, name, createTime, status);
        this.type = type;
        this.sortOrder = sortOrder;
        this.count = count;
        this.parentId = parentId;
        this.parentNodeName = parentNodeName;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentNodeName() {
        return parentNodeName;
    }

    public void setParentNodeName(String parentNodeName) {
        this.parentNodeName = parentNodeName;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public List<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Menu> subMenu) {
        this.subMenu = subMenu;
    }

    @Override
    public int compareTo(Menu o) {
        return this.getSortOrder() - o.getSortOrder();
    }
}
