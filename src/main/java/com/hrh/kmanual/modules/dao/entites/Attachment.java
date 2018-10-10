package com.hrh.kmanual.modules.dao.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hrh.kmanual.commons.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author huangrenhao
 * @date 2018/8/15
 */
@Entity
public class Attachment extends BaseEntity {

    private String url;

    private String otherName;

    private String type;

    /**
     *
     */
    private String fileType;

    /**
     *  关联key
     */
    private String asoKey;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "knowledgeId")
    private Knowledge knowledge;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getAsoKey() {
        return asoKey;
    }

    public void setAsoKey(String asoKey) {
        this.asoKey = asoKey;
    }

    public enum AttachmentType{
        KnowledgeImage("KnowledgeImage","文档附件"),
        KnowledgeFile("KnowledgeFile","文档附件"),
        OtherType("OtherFile","其他附件");

        private String type ;

        private String description;

        AttachmentType(String type, String description) {
            this.type = type;
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
