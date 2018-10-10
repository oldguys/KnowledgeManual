package com.hrh.kmanual.modules.dao.entites;/**
 * Created by Administrator on 2018/9/11 0011.
 */

import com.hrh.kmanual.commons.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/11 0011 10:55
 */
@Entity
@Getter
@Setter
public class KnowledgeCollection extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer type;

    @OneToMany
    @JoinColumn(name = "collectionId")
    private List<Knowledge> knowledgeList;

    public List<Knowledge> getKnowledgeList() {
        return knowledgeList;
    }

    public void setKnowledgeList(List<Knowledge> knowledgeList) {
        this.knowledgeList = knowledgeList;
    }

    public enum KnowledgeCollectionType{

        FrontEnd(1,"前端"),
        BackEnd(2,"后端"),
        DATABASE(3,"数据库")
        ;

        KnowledgeCollectionType(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        private Integer code;
        private String value;

        public Integer getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }


}
