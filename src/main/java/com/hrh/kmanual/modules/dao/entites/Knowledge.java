package com.hrh.kmanual.modules.dao.entites;

import com.hrh.kmanual.commons.dao.entities.BaseEntity;


import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 文档节点
 *
 * @author huangrenhao
 * @date 2018/8/14
 */
@Entity
public class Knowledge extends BaseEntity implements Comparable<Knowledge> {

    private String description;

    @Column(columnDefinition = "TEXT")
    private String context;

    @Column(columnDefinition = "TEXT")
    private String jsonInfo;

    private String tags;

    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menuId")
    private Menu menu;

    /**
     * 文件附件
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "knowledge", cascade = CascadeType.PERSIST)
    private List<Attachment> attachments = Collections.emptyList();

    public Knowledge() {
    }

    public Knowledge(Long id) {
        setId(id);
    }

    public Knowledge(Long id, String name) {
        setId(id);
        setName(name);
    }

    public Knowledge(Long id, String name, Date createTime, String tags, Integer status) {
        super.setId(id);
        super.setName(name);
        super.setCreateTime(createTime);
        super.setStatus(status);
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJsonInfo() {
        return jsonInfo;
    }

    public void setJsonInfo(String jsonInfo) {
        this.jsonInfo = jsonInfo;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public int compareTo(Knowledge o) {
        return this.sortOrder - o.sortOrder;
    }
}
