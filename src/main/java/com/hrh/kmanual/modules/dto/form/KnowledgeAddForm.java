package com.hrh.kmanual.modules.dto.form;

import com.hrh.kmanual.commons.dto.Form;
import com.hrh.kmanual.commons.utils.JsonUtils;
import com.hrh.kmanual.modules.dao.entites.Knowledge;
import com.hrh.kmanual.modules.dao.entites.KnowledgeCollection;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/30
 */
public class KnowledgeAddForm implements Form<Knowledge> {

    @NotBlank(message = "标题不能为空！")
    private String name;

    @NotBlank(message = "文本内容不能为空！")
    private String context;

    @NotBlank(message = "文本内容不能为空！")
    private String jsonInfo;

    private String tags;

    private Long knowledgeCollection;

    @NotNull(message = "目录不能为空！")
    private Long menuId;

    /**
     *  关联验附件key
     */
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private List<MultipartFile> files = Collections.emptyList();

    private List<Long> attachmentIds = Collections.emptyList();

    public String getJsonInfo() {
        return jsonInfo;
    }

    public void setJsonInfo(String jsonInfo) {
        this.jsonInfo = jsonInfo;
    }

    public Long getKnowledgeCollection() {
        return knowledgeCollection;
    }

    public void setKnowledgeCollection(Long knowledgeCollection) {
        this.knowledgeCollection = knowledgeCollection;
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }


    @Override
    public Knowledge trainToEntity() {

        Knowledge entity = new Knowledge();
        entity.setContext(context);
        entity.setJsonInfo(jsonInfo);
        entity.setName(name);
        entity.setTags(tags);

        return entity;
    }
}
